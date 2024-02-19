package com.dohyeong.activity_service.domain.member.controller;


import com.dohyeong.activity_service.domain.member.dto.*;
import com.dohyeong.activity_service.domain.member.entity.Member;
import com.dohyeong.activity_service.domain.member.mapper.MemberMapper;
import com.dohyeong.activity_service.domain.member.service.MemberService;
import com.dohyeong.activity_service.global.mail.service.MailServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/members")
public class MemberController {

    private final MailServiceImpl mailService;
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    //이메일 보내기
    @PostMapping("/emailSend")
    public ResponseEntity emailSend(@Valid @RequestBody MailTestDto mailTestDto) throws Exception{
        //인증번호
        String confirm = mailService.sendSimpleMessage(mailTestDto.getEmail());
        System.out.println(confirm);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //이메일 인증 체크
    @PostMapping("/emailConfirm")
    public ResponseEntity emailConfirm(@Valid @RequestBody MailTestDto mailTestDto) throws Exception{
            if(mailService.verifyEmailCode(mailTestDto.getEmail(),mailTestDto.getCode()))
                return ResponseEntity.ok("이메일체크확인 완료");

       return ResponseEntity.notFound().build();
    }


    //회원가입
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity createMember(@Valid @RequestPart(value = "key")MemberPostDto memberPostDto,
                                       @RequestParam(value = "image")MultipartFile image) throws IOException{
        memberService.saveMember(memberMapper.memberPostToMember(memberPostDto),image);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //프로필 수정
    @PatchMapping()
    public ResponseEntity updateMember(@Valid @RequestPart(value = "key")MemberPatchDto memberPatchDto,
                                       @RequestParam(value = "image")MultipartFile image) throws IOException{
        Member member = memberService.updateMember(memberMapper.memberPatchToMember(memberPatchDto),image);
        MemberResponseDto.PatchDto response = memberMapper.memberToPatchResponse(member);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //비밀번호 업데이트
    @PatchMapping("/password")
    public ResponseEntity updatePassword(@Valid @RequestBody MemberPasswordDto memberPasswordDto){
        memberService.alterPassword(memberPasswordDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //회원 조회

}
