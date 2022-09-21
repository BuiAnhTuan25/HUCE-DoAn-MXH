package com.huce.doan.mxh.model.entity;

import com.huce.doan.mxh.constains.PostStatusEnum;
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
public class Posts {
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
}
