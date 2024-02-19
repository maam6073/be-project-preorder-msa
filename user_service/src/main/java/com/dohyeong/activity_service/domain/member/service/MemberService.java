package com.dohyeong.activity_service.domain.member.service;


import com.dohyeong.activity_service.domain.member.dto.MemberPasswordDto;
import com.dohyeong.activity_service.domain.member.entity.Member;
import com.dohyeong.activity_service.domain.member.repository.MemberRepository;
import com.dohyeong.activity_service.global.exception.BusinessLogicException;
import com.dohyeong.activity_service.global.exception.ExceptionCode;
import com.dohyeong.activity_service.global.s3.service.S3Service;
import com.dohyeong.activity_service.global.security.dto.UserLoginDto;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Optional;


@Transactional
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final S3Service s3Service;



    //회원가입
    public Member saveMember(Member member, MultipartFile image) throws IOException{
        verifyMemberByEmail(member.getEmail());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());

        member.setPassword(encryptedPassword);

        if(!image.isEmpty()){
            String imgUrl = s3Service.imgUpload(image);
            member.setProfile_img(imgUrl);
        }
        return memberRepository.save(member);
    }


    //회원 정보 수정(이름,프로필 이미지, 인사말)
    public Member updateMember(Member member, MultipartFile image) throws IOException{
        verifyMemberByNickName(member.getNickname());
        Member curMember = getCurMember();


        if(!image.isEmpty()){
            String imgUrl = s3Service.imgUpdate(image,findMemberByImgName(curMember.getProfile_img()));
            curMember.setProfile_img(imgUrl);
        }
        Optional.ofNullable(member.getDescription()).ifPresent(curMember::setDescription);
        Optional.ofNullable(member.getNickname()).ifPresent(curMember::setNickname);

        return memberRepository.save(curMember);
    }


    //비밀번호 변경
    //현재 비밀번호 입력해서 체크 후 변경
    public void alterPassword(MemberPasswordDto memberPasswordDto){
        Member curMember = getCurMember();

        if(passwordEncoder.matches(memberPasswordDto.getCur_password(), curMember.getPassword())){
            String encryptedPassword = passwordEncoder.encode(memberPasswordDto.getAlter_password());
            curMember.setPassword(encryptedPassword);
            memberRepository.save(curMember);
        }
        else
            throw new BusinessLogicException(ExceptionCode.BAD_REQUEST_PW);

    }

    //닉네임 중복 검사
    @Transactional(readOnly = true)
    private void verifyMemberByNickName(String nickname){
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if(member.isPresent())
            throw new BusinessLogicException(ExceptionCode.EXISTS_MEMBER);
    }

    //이메일 중복 검사
    @Transactional(readOnly = true)
    private void verifyMemberByEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent())
            throw new BusinessLogicException(ExceptionCode.EXISTS_EMAIL);
    }

    //로그인 사용자 정보
    private Member getCurMember(){
        return (Member)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    //이메일 찾기
    public Member getLoginMemberByLoginEmail(String email){
        Optional<Member> loginMember = memberRepository.findByEmail(email);
        if(loginMember.isEmpty())
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER);
        
        return loginMember.get();
    }

    //이름으로 사용자 찾기
    public Member getMemberByName(String name){
        Optional<Member> Member = memberRepository.findByName(name);
        if(Member.isEmpty())
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER);

        return Member.get();
    }



    //아이디로 사용자 찾기
    public Member getMemberById(long memberId){
        Optional<Member> Member = memberRepository.findById(memberId);
        if(Member.isEmpty())
            throw new BusinessLogicException(ExceptionCode.NOT_FOUND_MEMBER);

        return Member.get();
    }


    //이미지이름 가공
    private String findMemberByImgName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }





    @Override
    public UserLoginDto loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member;
        try {
            member = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new LoginException("ID_NOT_FOUND"));
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }

        return new UserLoginDto(member);
    }


}
