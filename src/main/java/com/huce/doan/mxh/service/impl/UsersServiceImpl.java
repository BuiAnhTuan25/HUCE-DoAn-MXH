package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.constains.ProviderEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.UserRegister;
import com.huce.doan.mxh.model.dto.UsersDto;
import com.huce.doan.mxh.model.entity.UsersEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.UsersRepository;
import com.huce.doan.mxh.security.CustomUserDetails;
import com.huce.doan.mxh.service.ProfilesService;
import com.huce.doan.mxh.service.UsersService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UsersServiceImpl implements UsersService, UserDetailsService {
    private final UsersRepository usersRepository;

    private final ModelMapper mapper;

    private final Response response;

    private final ProfilesService profilesService;

    private final PasswordEncoder passwordEncoder;

    private final MailServiceImpl mailService;

    @Override
    public Data getUser(Long id) {
        UsersEntity user = usersRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(EntityNotFoundException::new);

        return response.responseData(mapper.map(user, UsersDto.class));
    }

    @Override
    public Data createUser(UserRegister user, StringBuffer siteUrl) throws MessagingException {
        Optional<UsersEntity> optional = usersRepository.findByUsername(user.getUsername());
        if (optional.isPresent()) return new Data(false, "username already exists", null);

        UsersEntity usersEntity = usersRepository.findByEmail(user.getEmail());
        if (usersEntity != null) return new Data(false, "mail already exist", null);
        UsersEntity userEntity = new UsersEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());
        userEntity.setStatus(StatusEnum.INACTIVE);
        userEntity.setIsProfile(false);
        userEntity.setProvider(ProviderEnum.DEFAULT);
        userEntity.setVerificationCode(RandomString.make(64));


        // Gui mail
        Map<String, Object> props = new HashMap<>();
        props.put("name", user.getUsername());
        props.put("url", siteUrl.append(userEntity.getVerificationCode()).toString());

        mailService.sendMail(props, userEntity.getEmail(), "sendMail", "Xác thực tài khoản");

        return response.responseData(mapper.map(usersRepository.save(userEntity), UsersDto.class));
    }

    @Override
    public Data updateUser(UsersDto user, Long id) {
        user.setId(id);
        UsersEntity userEntity = usersRepository.findByIdAndStatus(id, StatusEnum.ACTIVE).orElseThrow(EntityNotFoundException::new);

        return response.responseData(mapper.map(usersRepository.save(userEntity.mapperUsersDto(user)), UsersDto.class));
    }

    @Override
    public Data deleteUser(Long id) {
        UsersEntity userEntity = usersRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userEntity.setStatus(StatusEnum.INACTIVE);
        usersRepository.save(userEntity);
        if (userEntity.getIsProfile()) {
            profilesService.deleteProfile(id);
        }

        return response.responseData(mapper.map(userEntity, UsersDto.class));
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UsersEntity user = usersRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return new CustomUserDetails(user);
    }

    // JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(Long id) {
        UsersEntity user = usersRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }

    @Override
    public UsersEntity processOAuthPostLogin(UsersEntity userEntity, ProviderEnum provider) {
        UsersEntity existEmail = usersRepository.findByEmail(userEntity.getEmail());
        if (existEmail == null) {
            UsersEntity newUser = new UsersEntity();
            newUser.setEmail(userEntity.getEmail());
            newUser.setProvider(provider);
            newUser.setStatus(StatusEnum.ACTIVE);
            newUser.setIsProfile(false);

            String nameConverted = convertUsername(userEntity.getUsername());
            UsersEntity existUsername = usersRepository.getUserByUsername(nameConverted);
            if (existUsername == null) {
                newUser.setUsername(nameConverted);
            } else {
                String nameFix = duplicateUsernameHandle(nameConverted);
                newUser.setUsername(nameFix);
            }
            return usersRepository.save(newUser);
        }
        return existEmail;
    }

    public String convertUsername(String name) {
        int index = name.indexOf("@");
        String username;
        if (index > 0) {
            username = name.substring(0, index);
        } else username = name;
        String temp = Normalizer.normalize(username, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "");
    }

    // Them so tu 0, 1, 2, ... vao sau username neu bi trung
    public String duplicateUsernameHandle(String nameConverted) {
        String addInt = nameConverted;
        int i = 0;
        do {
            UsersEntity existUser = usersRepository.getUserByUsername(addInt);
            if (existUser == null) {
                return addInt;
            } else {
                addInt = nameConverted.concat(String.valueOf(i));
                i++;
            }
        } while (true);
    }

    @Override
    public Data verify(String verificationCode) {
        Optional<UsersEntity> optionalUser = usersRepository.findByVerificationCode(verificationCode);
        if (!optionalUser.isPresent()) return new Data(false, "verification code not found", null);

        UsersEntity user = optionalUser.get();
        user.setVerificationCode(null);
        user.setStatus(StatusEnum.ACTIVE);
        usersRepository.save(user);
        return new Data(true, "verify success", null);
    }

    @Override
    public Data updatePasswordToken(String mail, StringBuffer siteUrl) throws MessagingException {
        UsersEntity user = usersRepository.findByEmail(mail);
        if (user==null) return new Data(false, "mail not found", null);

        user.setUpdatePasswordToken(RandomString.make(64));
        usersRepository.save(user);
//
        Map<String, Object> props = new HashMap<>();
        props.put("name", user.getUsername());
        props.put("url", siteUrl.append(user.getUpdatePasswordToken()).toString());

        mailService.sendMail(props, user.getEmail(), "updatePassword", "Đổi mật khẩu");
        return new Data(true, "update password success", siteUrl);
    }

    @Override
    public Data updatePassword(Long id,String password) {
        Optional<UsersEntity> optionalUser = usersRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new Data(false, "password token not found", null);
        }

        UsersEntity user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(password));
        return new Data(true, "update password success", usersRepository.save(user));
    }

    @Override
    public Data forgotPassword(String mail) throws MessagingException {
        UsersEntity user = usersRepository.findByEmail(mail);
        if (user == null) return new Data(false, "mail not found", null);

        String pass = RandomString.make(10);

        user.setPassword(passwordEncoder.encode(pass));
        usersRepository.save(user);
//
        Map<String, Object> props = new HashMap<>();
        props.put("name", user.getUsername());
        props.put("pass", pass);

        mailService.sendMail(props, user.getEmail(), "forgotPassword", "Quên mật khẩu");
        return new Data(true, "forgot password success", pass);
    }


}
