package com.huce.doan.mxh.service.impl;

import com.huce.doan.mxh.constains.FriendStatusEnum;
import com.huce.doan.mxh.model.dto.FriendsDto;
import com.huce.doan.mxh.model.entity.FriendsEntity;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;
import com.huce.doan.mxh.model.response.Pagination;
import com.huce.doan.mxh.model.response.Response;
import com.huce.doan.mxh.repository.FriendsRepository;
import com.huce.doan.mxh.service.FriendsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class FriendsServiceImpl implements FriendsService {
    private final FriendsRepository friendsRepository;
    private final ModelMapper mapper;
    private final Response response;

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
    public Data findByPhoneNumber(String phoneNumber) {
        Optional<FriendsDto> friendsDto = friendsRepository.findByPhoneNumber(phoneNumber);
        return friendsDto.map(data -> response.responseData("Get friend successfully", data)).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public ListData getListFriendByMeId(Long id, int page, int pageSize) {
        Page<FriendsDto> friends = friendsRepository.getListFriendByMeId(id, PageRequest.of(page, pageSize));

        return response.responseListData(friends.getContent(), new Pagination(friends.getNumber(), friends.getSize(), friends.getTotalPages(),
                (int) friends.getTotalElements()));
    }

    @Override
    public Data createFriend(FriendsDto friend) {
        FriendsEntity friendsEntity = new FriendsEntity().mapperFriendsDto(friend);
        friendsEntity.setFriendStatus(FriendStatusEnum.WAITING);

        FriendsEntity friendsEntity1 = new FriendsEntity();
        friendsEntity1.setFriendId(friendsEntity.getMeId());
        friendsEntity1.setMeId(friendsEntity.getFriendId());
        friendsEntity1.setFriendStatus(FriendStatusEnum.CONFIRM);
        friendsRepository.save(friendsEntity1);

        return response.responseData("Create friend successfully", mapper.map(friendsRepository.save(friendsEntity), FriendsDto.class));
    }

    @Override
    public Data updateFriend(FriendsDto friend, Long id) {
        friend.setId(id);
        Optional<FriendsEntity> friendsEntity = friendsRepository.findById(id);
        return friendsEntity.map(data -> response.responseData("Update friend successfully", mapper.map(friendsRepository.save(data), FriendsDto.class))).orElseGet(() -> response.responseError("Entity not found"));
    }

    @Override
    public Data deleteFriend(Long id) {
        Optional<FriendsEntity> friendsEntity = friendsRepository.findById(id);

        return friendsEntity.map(data -> {
            Optional<FriendsEntity> friend = friendsRepository.findByMeIdAndFriendId(data.getFriendId(), data.getMeId());
            friend.map(dt -> {
                friendsRepository.deleteById(id);
                friendsRepository.deleteById(dt.getId());
                return response.responseData("Delete friend successfully", mapper.map(friendsEntity, FriendsDto.class));
            });
            return response.responseError("Entity not found");
        }).orElseGet(() -> response.responseError("Entity not found"));
    }
}
