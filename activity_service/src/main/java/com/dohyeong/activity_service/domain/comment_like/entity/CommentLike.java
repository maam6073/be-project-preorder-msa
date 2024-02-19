package com.dohyeong.activity_service.domain.comment_like.entity;

import com.dohyeong.activity_service.domain.comment.entity.Comment;
import com.dohyeong.activity_service.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"comment_id","member_id"}))
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_like_id")
    private Long comment_like_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
