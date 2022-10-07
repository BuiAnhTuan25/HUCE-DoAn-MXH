package com.huce.doan.mxh.service;

import com.huce.doan.mxh.model.dto.FriendsDto;
import com.huce.doan.mxh.model.response.Data;
import com.huce.doan.mxh.model.response.ListData;

public interface FriendsService {
    Data getFriend(Long id);

    ListData getListFriendByMeId(Long userId, int page, int pageSize);

    Data createFriend(FriendsDto post);

    Data updateFriend(FriendsDto post, Long id);

    Data deleteFriend(Long id);

    Data findByMeIdAndFriendId(Long meId, Long friendId);

    Data findByPhoneNumber(String phoneNumber);
}
