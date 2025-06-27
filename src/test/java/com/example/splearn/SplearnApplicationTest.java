package com.example.splearn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.*;

class SplearnApplicationTest {
    @Test
    @DisplayName("run을 테스트한다")
    void run() {
        try(MockedStatic<SplearnApplication> mocked = Mockito.mockStatic(SplearnApplication.class)) {

            SplearnApplication.main(new String[0]);

            mocked.verify(() -> SpringApplication.run(SplearnApplication.class, new String[0]));
        }


    }
}