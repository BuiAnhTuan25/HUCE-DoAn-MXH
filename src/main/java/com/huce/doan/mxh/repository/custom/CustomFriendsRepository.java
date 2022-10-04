package com.huce.doan.mxh.repository.custom;

import com.huce.doan.mxh.model.dto.FriendResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomFriendsRepository {
    Page<FriendResponse> getListFriendByMeId(Long meId, Pageable pageable);
}
