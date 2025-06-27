package com.example.splearn.application.required;

import com.example.splearn.domain.Email;
import org.springframework.data.repository.Repository;
import com.example.splearn.domain.Member;

import java.util.Optional;

/**
 * 회원 정보를 저장하거나 조회한다.
 */
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);
}
