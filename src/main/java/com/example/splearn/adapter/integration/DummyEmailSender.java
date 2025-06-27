package com.example.splearn.adapter.integration;

import com.example.splearn.application.required.EmailSender;
import com.example.splearn.domain.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback //빈을 다 찾다가 없으면 얘를 사용해줘..
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender send email: " + email);
    }
}
