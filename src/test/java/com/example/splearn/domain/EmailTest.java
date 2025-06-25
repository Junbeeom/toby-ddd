package com.example.splearn.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    
    @Test
    @DisplayName("이메일 값 객체 동등성 비교")
    void equality () {
        var email1 = new Email("toby@splearn.app");
        var email2 = new Email("toby@splearn.app");


        assertThat(email1).isEqualTo(email2);
    }

}