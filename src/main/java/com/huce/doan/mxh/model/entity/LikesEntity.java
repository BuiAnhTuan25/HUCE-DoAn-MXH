package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.model.dto.LikesDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long userId;

    public LikesEntity mapperLikesDto(LikesDto like){
        this.id = like.getId();
        this.postId= like.getPostId();
        this.userId = like.getUserId();

        return this;
    }
}
