package com.example.splearn.application;

import com.example.splearn.application.provided.MemberRegister;

import com.example.splearn.application.required.EmailSender;
import com.example.splearn.application.required.MemberRepository;

import com.example.splearn.domain.Member;
import com.example.splearn.domain.MemberRegisterRequest;
import com.example.splearn.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요.");

        return member;
    }
}
