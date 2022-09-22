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

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Random;


@Service
@AllArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class FriendsServiceImpl implements FriendsService {
    private static Random generator;
    private FriendsRepository friendsRepository;
    private ModelMapper mapper;
    private Response response;

    @Override
    public Data getFriend(Long id) {
        FriendsEntity friendsEntity = friendsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return response.responseData(mapper.map(friendsEntity, FriendsDto.class));
    }

    @Override
    public ListData getByMeId(Long id, int page, int pageSize) {
        Page<FriendsEntity> friends = friendsRepository.findByMeId(id, PageRequest.of(page, pageSize));

        return response.responseListData(friends.getContent(), new Pagination(friends.getNumber(), friends.getSize(), friends.getTotalPages(),
                (int) friends.getTotalElements()));
    }

    @Override
    public Data createFriend(FriendsDto friend) {
        FriendsEntity friendsEntity = new FriendsEntity().mapperFriendsDto(friend);
        friendsEntity.setFriendCode(generator.nextLong());
        friendsEntity.setFriendStatus(FriendStatusEnum.WAITING);

        FriendsEntity friendsEntity1 = new FriendsEntity();
        friendsEntity1.setFriendId(friendsEntity.getMeId());
        friendsEntity1.setMeId(friendsEntity.getFriendId());
        friendsEntity1.setFriendCode(friendsEntity.getFriendCode());
        friendsEntity1.setFriendStatus(FriendStatusEnum.CONFIRM);
        friendsRepository.save(friendsEntity1);

        return response.responseData(mapper.map(friendsRepository.save(friendsEntity), FriendsDto.class));
    }

    @Override
    public Data updateFriend(FriendsDto friend, Long id) {
        friend.setId(id);
        FriendsEntity friendsEntity = friendsRepository.findById(id).orElseThrow(EntityNotFoundException::new).mapperFriendsDto(friend);

        return response.responseData(mapper.map(friendsRepository.save(friendsEntity), FriendsDto.class));
    }

    @Override
    public Data deleteFriend(Long id) {
        FriendsEntity friendsEntity = friendsRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        FriendsEntity friend = friendsRepository.findByFriendCode(friendsEntity.getFriendCode()).orElseThrow(EntityNotFoundException::new);
        friendsRepository.deleteById(id);
        friendsRepository.deleteById(friend.getId());

        return response.responseData(mapper.map(friendsEntity, FriendsDto.class));
    }
}
