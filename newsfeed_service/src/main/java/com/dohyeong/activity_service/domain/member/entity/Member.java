package com.dohyeong.activity_service.domain.member.entity;


import com.dohyeong.activity_service.domain.common.BaseEntity;
import com.dohyeong.activity_service.domain.follow.entity.Follow;
import com.dohyeong.activity_service.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    //pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long member_id;

    //팔로우
    @OneToMany(mappedBy = "following", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followings;

    @OneToMany(mappedBy = "follower", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers;

    //내가 작성한 글
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private @Builder.Default List<Post> posts = new ArrayList<>();

    //권한
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    //이메일
    @Column(nullable = false, unique = true)
    private String email;

    //이름
    @Column(nullable = false, unique = true)
    private String name;

    //닉네임
    @Column(nullable = false, unique = true)
    private String nickname;

    //비밀번호
    @Column(nullable = false, length = 100)
    private String password;

    //자기소개
    @Column(nullable = false)
    private String description;

    //사진 url
    @Column(nullable = false)
    private String profile_img;
}
