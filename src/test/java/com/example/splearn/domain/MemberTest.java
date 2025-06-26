package com.example.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static com.example.splearn.domain.MemberFixture.createPasswordEncoder;
import static com.example.splearn.domain.MemberStatus.PENDING;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {
    private static final Logger log = LoggerFactory.getLogger(MemberTest.class);
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = createPasswordEncoder();

        member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }



    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(PENDING);
    }
/*
    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() -> Member.create(null, "Toby", "secret",  passwordEncoder))
                .isInstanceOf(NullPointerException.class);
    }*/
    
    @Test
    void activate() {
        member.activate();
        
         assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }
    
    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }
    
    
    @Test
    @DisplayName("탈퇴")        
    void deactivate() {
        member.activate();

        member.deActivate();

         assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);

    }
    
    @Test
    @DisplayName("탈퇴실패테스트")
    void deActivateFail() {
        assertThatThrownBy(member::deActivate).isInstanceOf(IllegalStateException.class);

        member.activate();
        member.deActivate();

        assertThatThrownBy(member::deActivate).isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    @DisplayName("비밀번호검증")        
    void verifyPassword() {
        assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }
    
    @Test
    @DisplayName("닉네임을 검증한다.")
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("Toby");

        member.changeNickname("Charlie");

        assertThat(member.getNickname()).isEqualTo("Charlie");
    }

    @Test
    void changePassword() {
        member.changePassword("veysecret", passwordEncoder);

        assertThat(member.verifyPassword("veysecret", passwordEncoder)).isTrue();
    }

    @Test
    @DisplayName("Active 체크 ")        
    void isActivate() {
         assertThat(member.isActive()).isFalse();

         member.activate();
         assertThat(member.isActive()).isTrue();

         member.deActivate();
        assertThat(member.isActive()).isFalse();

    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);
    }




}