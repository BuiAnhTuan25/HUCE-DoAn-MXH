package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.constains.FriendStatusEnum;
import com.huce.doan.mxh.constains.MessageStatusEnum;
import com.huce.doan.mxh.model.dto.FriendsDto;
import com.huce.doan.mxh.model.dto.NotificationsDto;
import com.huce.doan.mxh.model.entity.FriendsEntity;
import com.huce.doan.mxh.model.entity.NotificationsEntity;
import com.huce.doan.mxh.model.entity.ProfilesEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.FriendsRepository;
import com.huce.doan.mxh.repository.NotificationsRepository;
import com.huce.doan.mxh.repository.ProfilesRepository;
import com.huce.doan.mxh.service.FriendsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class FriendsServiceImpl implements FriendsService {
    private final FriendsRepository friendsRepository;
    private final ModelMapper mapper;
    private final Response response;
    private final ProfilesRepository profilesRepository;
    private final NotificationsRepository notificationsRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public Data getFriend(Long id) {
        Optional<FriendsEntity> friendsEntity = friendsRepository.findById(id);
        return friendsEntity.map(data -> response.responseData("Get friend successfully", mapper.map(data, FriendsDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public Data findByMeIdAndFriendId(Long meId, Long friendId) {
        Optional<FriendsEntity> friendsEntity = friendsRepository.findByMeIdAndFriendId(meId, friendId);

        return friendsEntity.map(data -> response.responseData("Get friend successfully", mapper.map(data, FriendsDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData getListFriendByMeIdAndFriendStatus(Long id, FriendStatusEnum friendStatus, int page, int pageSize) {
        Page<FriendsDto> friends = friendsRepository.getListFriendByMeIdAndFriendStatus(id, friendStatus, PageRequest.of(page, pageSize));

        return response.responseListData(friends.getContent(), new Pagination(friends.getNumber(), friends.getSize(), friends.getTotalPages(),
                (int) friends.getTotalElements()));
    }

    @Override
    @Transactional
    public Data createFriend(FriendsDto friend) {
        FriendsEntity friendsEntity = new FriendsEntity().mapperFriendsDto(friend);
        friendsEntity.setFriendStatus(FriendStatusEnum.WAITING);

        FriendsEntity friendsEntity1 = new FriendsEntity();
        friendsEntity1.setFriendId(friendsEntity.getMeId());
        friendsEntity1.setMeId(friendsEntity.getFriendId());
        friendsEntity1.setFriendStatus(FriendStatusEnum.CONFIRM);
        friendsRepository.save(friendsEntity1);

        ProfilesEntity profile = profilesRepository.findById(friendsEntity.getMeId()).get();
        NotificationsEntity notification = new NotificationsEntity();
        notification.setContent(profile.getName() + " sent you a friend request");
        notification.setReceiverId(friend.getFriendId());
        notification.setFriendId(profile.getId());
        notification.setSendTime(LocalDateTime.now());
        notification.setStatus(MessageStatusEnum.NOT_SEEN);
        NotificationsDto notificationsDto = mapper.map(notificationsRepository.save(notification), NotificationsDto.class);
        simpMessagingTemplate.convertAndSend("/topic/notification/" + notificationsDto.getReceiverId(), notificationsDto);

        return response.responseData("Create friend successfully",mapper.map(friendsRepository.save(friendsEntity),FriendsDto .class));
}

    @Override
    @Transactional
    public Data updateFriend(FriendsDto friend, Long id) {
        friend.setId(id);
        Optional<FriendsEntity> friendsEntity = friendsRepository.findById(id);
        if (friend.getFriendStatus().equals(FriendStatusEnum.FRIENDED)) {
            FriendsEntity friendConfirm = friendsRepository.findByMeIdAndFriendId(friend.getFriendId(), friend.getMeId()).get();
            friendConfirm.setFriendStatus(FriendStatusEnum.FRIENDED);
            friendsRepository.save(friendConfirm);

            ProfilesEntity profile = profilesRepository.findById(friend.getMeId()).get();
            NotificationsEntity notification = new NotificationsEntity();
            notification.setContent(profile.getName() + " accepted the friend request");
            notification.setReceiverId(friend.getFriendId());
            notification.setFriendId(profile.getId());
            notification.setSendTime(LocalDateTime.now());
            notification.setStatus(MessageStatusEnum.NOT_SEEN);
            NotificationsDto notificationsDto = mapper.map(notificationsRepository.save(notification), NotificationsDto.class);
            simpMessagingTemplate.convertAndSend("/topic/notification/" + notificationsDto.getReceiverId(), notificationsDto);
        }
        return friendsEntity.map(data -> response.responseData("Update friend successfully", mapper.map(friendsRepository.save(data.mapperFriendsDto(friend)), FriendsDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    @Transactional
    public Data deleteFriend(Long id) {
        Optional<FriendsEntity> friendsEntity = friendsRepository.findById(id);

        return friendsEntity.map(data -> {
            FriendsEntity friend = friendsRepository.findByMeIdAndFriendId(data.getFriendId(), data.getMeId()).get();
            friendsRepository.deleteById(id);
            friendsRepository.deleteById(friend.getId());
            return response.responseData("Delete friend successfully", mapper.map(data, FriendsDto.class));
        }).orElseGet(() -> response.responseError("Entity not found"));
    }
}
