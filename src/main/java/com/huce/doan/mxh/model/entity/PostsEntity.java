package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.PostStatusEnum;
import com.huce.doan.mxh.model.dto.PostsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "author_id", unique = true)
    private Long authorId;

    @Column(name = "content")
    private String content;

    @Column(name = "picture_url")
    private String pictureUrl;

    @Column(name = "post_status", unique = true)
    private PostStatusEnum postStatus;

    @Column(name = "posting_time", unique = true)
    private Long postingTime;

    public PostsEntity mapperPostsDto(PostsDto posts) {
        this.id = posts.getId();
        this.authorId = posts.getAuthorId();
        this.content = posts.getContent();
        this.pictureUrl = posts.getPictureUrl();
        this.postStatus = posts.getPostStatus();
        this.postingTime = posts.getPostingTime();

        return this;
    }
}
