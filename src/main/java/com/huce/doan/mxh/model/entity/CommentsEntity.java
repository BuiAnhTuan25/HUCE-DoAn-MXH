package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.CommentsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentsEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content")
    private String content;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "comment_time")
    private LocalDateTime commentTime;

    @Column(name = "status")
    private StatusEnum status;

    public CommentsEntity mapperCommentsDto(CommentsDto comment){
        this.id = comment.getId();
        this.postId = comment.getPostId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.pictureUrl = comment.getPictureUrl();
        this.commentTime = comment.getCommentTime();

        return this;
    }
}
