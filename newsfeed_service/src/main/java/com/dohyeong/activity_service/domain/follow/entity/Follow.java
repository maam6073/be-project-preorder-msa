package com.dohyeong.activity_service.domain.follow.entity;


import com.dohyeong.activity_service.domain.common.BaseEntity;
import com.dohyeong.activity_service.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Follow extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long follow_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "following")
    private Member following;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "follower")
    private Member follower;
}
