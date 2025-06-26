package com.example.splearn.application.provided;

import com.example.splearn.application.MemberService;
import com.example.splearn.application.required.EmailSender;
import com.example.splearn.application.required.MemberRepository;
import com.example.splearn.domain.Email;
import com.example.splearn.domain.Member;
import com.example.splearn.domain.MemberFixture;
import com.example.splearn.domain.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterTest {

    @Test
    @DisplayName("Stub을 사용한 저장테스트")
    void registerTestStub() {
        MemberRegister register1 = new MemberService(
                new MemberRepositoryStub(), new EmailSenderStub(), MemberFixture.createPasswordEncoder()
        );

        Member member = register1.register(MemberFixture.createMemberRegisterRequest());
        
        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("Mock을 사용한 저장테스트")
    void registerTestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();

        MemberRegister register1 = new MemberService(
                new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
        );

        Member member = register1.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        
        assertThat(emailSenderMock.getTos()).hasSize(1);
        assertThat(emailSenderMock.getTos().getFirst()).isEqualTo(member.getEmail());


    }

    @Test
    @DisplayName("Mockito library을 사용한 저장테스트")
    void registerTestMockito() {
        EmailSender emailSenderMock = Mockito.mock(EmailSender.class);

        MemberRegister register1 = new MemberService(
                new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
        );

        Member member = register1.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());


    }

    static class MemberRepositoryStub implements MemberRepository {
        @Override
        public Member save(Member member) {
            ReflectionTestUtils.setField(member, "id", 1L); // id에 1값을 세팅 해준다.
            return member;
        }
    }


    static class EmailSenderStub implements EmailSender {
        @Override
        public void send(Email email, String subject, String body) {

        }
    }

    static class EmailSenderMock implements EmailSender {
        List<Email> tos = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }

        public List<Email> getTos() {
            return tos;
        }

    }


}