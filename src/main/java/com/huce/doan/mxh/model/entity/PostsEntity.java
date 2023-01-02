package com.huce.doan.mxh.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.huce.doan.mxh.constains.PrivacyEnum;
import com.huce.doan.mxh.constains.StatusEnum;
import com.huce.doan.mxh.model.dto.PostsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    private int countLikes;

    @Column(name = "privacy")
    private PrivacyEnum privacy;

    @Column(name = "is_share")
    private Boolean isShare;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "posting_time")
    private LocalDateTime postingTime;

    @Column(name = "status")
    private StatusEnum status;

    public PostsEntity mapperPostsDto(PostsDto posts) {
        this.id = posts.getId();
        this.authorId = posts.getAuthorId();
        this.content = posts.getContent();
        this.pictureUrl = posts.getPictureUrl();
        this.privacy = posts.getPrivacy();
        this.postingTime = posts.getPostingTime();
        this.countLikes = posts.getCountLikes();
        this.isShare = posts.getIsShare();

        return this;
    }
}
