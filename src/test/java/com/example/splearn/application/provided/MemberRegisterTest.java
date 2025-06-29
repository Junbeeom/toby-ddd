package com.example.splearn.application.provided;

import com.example.splearn.SplearnTestConfiguration;
import com.example.splearn.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager)  {

    @Test
    @DisplayName("멤버등록테스트")
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        System.out.println(member);

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    
    @Test
    @DisplayName("SpringBoots를 활용한 member 등록 테스트")        
    void duplicateEmailFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);

    }

    @Test
    @DisplayName("등록 실패 테스트")
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "Toby", "longsecret"));
        checkValidation(new MemberRegisterRequest("toby@splearn.app", "Charlie----------------", "longsecret"));
        checkValidation(new MemberRegisterRequest("toby splearn.app", "Charlie----------------", "longsecret"));

    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());

        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        Assertions.assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }


}
