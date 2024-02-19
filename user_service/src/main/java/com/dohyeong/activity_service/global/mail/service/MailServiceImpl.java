package com.dohyeong.activity_service.global.mail.service;

import com.dohyeong.activity_service.global.util.RedisUtil;
import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;


import org.springframework.mail.MailException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final JavaMailSender emailSender;
    private final RedisUtil redisUtil;


    public static final String code = createKey();

    private MimeMessage createMessage(String to)throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO,to);
        message.setSubject("이메일 인증번호입니다.");

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1> 인증번호입니다. </h1>";
        msgg += "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= code+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("maam10@knou.ac.kr", "kimdohyeong"));



        return message;
    }

    public static String createKey(){
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for(int i = 0; i < 8; i++){
            int index = rnd.nextInt(3);

            switch (index){
                case 0:
                    key.append((char) ((rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    key.append((char) ((rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    key.append(rnd.nextInt(10));
                    break;
            }
        }
        return key.toString();
    }

    @Override
    public String sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);

        if(redisUtil.existData(to)){
            redisUtil.deleteData(to);
        }
        try{
            emailSender.send(message);
            redisUtil.setDataExpire(to,code, 60 * 30L);
        }catch (MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }


        return code;
    }


    public Boolean verifyEmailCode(String email, String code){
        String codeFoundByEmail = redisUtil.getData(email);
        if(codeFoundByEmail == null){
            return false;
        }
        return codeFoundByEmail.equals(code);
    }

}

