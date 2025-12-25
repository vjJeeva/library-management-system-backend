package com.jeeva.Smart.Library.Management.repository;

import com.jeeva.Smart.Library.Management.model.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(@NotBlank @Email(message = "should be an valid email") String email);
}
