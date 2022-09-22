package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.FriendStatusEnum;
import com.huce.doan.mxh.model.dto.FriendsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "friends")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendsEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "friend_id", unique = true)
    private Long friendId;

    @Column(name = "me_id", unique = true)
    private Long meId;

    @Column(name = "friend_code", unique = true)
    private Long friendCode;

    @Column(name = "friend_status", unique = true)
    private FriendStatusEnum friendStatus;

    public FriendsEntity mapperFriendsDto(FriendsDto friend){
        this.id = friend.getId();
        this.friendId = friend.getFriendId();
        this.meId = friend.getMeId();
        this.friendCode = friend.getFriendCode();
        this.friendStatus = friend.getFriendStatus();

        return this;
    }
}
