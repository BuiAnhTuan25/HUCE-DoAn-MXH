package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.model.entity.UsersEntity;
import com.huce.doan.mxh.repository.UsersRepository;
import com.huce.doan.mxh.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UsersEntity user = usersRepository.findByUsername(s).orElseThrow(()-> new UsernameNotFoundException("Could not find user"));

        return new CustomUserDetails(user);
    }
}
