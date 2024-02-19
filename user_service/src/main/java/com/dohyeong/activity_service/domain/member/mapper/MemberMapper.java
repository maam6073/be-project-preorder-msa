package com.dohyeong.activity_service.domain.member.mapper;

import com.dohyeong.activity_service.domain.member.dto.MemberPatchDto;
import com.dohyeong.activity_service.domain.member.dto.MemberPostDto;
import com.dohyeong.activity_service.domain.member.dto.MemberResponseDto;
import com.dohyeong.activity_service.domain.member.entity.Member;
import com.dohyeong.activity_service.domain.member.entity.MemberRole;
import org.springframework.stereotype.Component;


@Component
public class MemberMapper {
    public Member memberPostToMember(MemberPostDto memberPostDto){
        return Member.builder().
                email(memberPostDto.getEmail()).
                name(memberPostDto.getName()).
                nickname(memberPostDto.getNickname()).
                password(memberPostDto.getPassword()).
                description(memberPostDto.getDescription()).
                memberRole(MemberRole.USER).
                build();
    }

    public Member memberPatchToMember(MemberPatchDto memberPatchDto){
        return Member.builder().
                nickname(memberPatchDto.getNickname()).
                description(memberPatchDto.getDescription()).
                build();
    }

    public MemberResponseDto.PatchDto memberToPatchResponse(Member member){
        return MemberResponseDto.PatchDto.builder().
                email(member.getEmail()).
                name(member.getName()).
                nickname(member.getNickname()).
                profile_img(member.getProfile_img()).
                description(member.getDescription())
                .build();
    }
}
