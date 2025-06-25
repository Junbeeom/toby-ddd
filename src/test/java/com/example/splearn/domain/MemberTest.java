package com.example.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {

        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };



        member = Member.create("toby@splearn.app", "Toby", "secret", passwordEncoder);
    }

    @Test
    void createMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
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

        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }



}