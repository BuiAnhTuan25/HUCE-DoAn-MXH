package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.PostStatusEnum;
import com.huce.doan.mxh.model.dto.PostsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostsEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "content")
    private String content;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "count_likes")
    private Integer countLikes;

    @Column(name = "post_status")
    private PostStatusEnum postStatus;

    @Column(name = "posting_time")
    private LocalDateTime postingTime;

    public PostsEntity mapperPostsDto(PostsDto posts) {
        this.id = posts.getId();
        this.authorId = posts.getAuthorId();
        this.content = posts.getContent();
        this.pictureUrl = posts.getPictureUrl();
        this.postStatus = posts.getPostStatus();
        this.postingTime = posts.getPostingTime();
        this.countLikes = posts.getCountLikes();

        return this;
    }
}
