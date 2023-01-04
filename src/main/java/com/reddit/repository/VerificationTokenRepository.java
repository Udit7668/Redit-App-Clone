package com.reddit.repository;

import com.reddit.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
       Optional<VerificationToken> findByToken(String token);
}
