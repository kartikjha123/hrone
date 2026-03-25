package com.usermanagement.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.PasswordResetOtp;

@Repository
public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Long> {

    // Latest unused OTP dhundo
    Optional<PasswordResetOtp> findTopByEmailAndIsUsedFalseOrderByCreatedAtDesc(String email);

    // Purane OTP delete karo
    void deleteByEmail(String email);

    // Expired OTP cleanup
    @Query("SELECT o FROM PasswordResetOtp o WHERE o.expiryTime < :now")
    List<PasswordResetOtp> findExpiredOtps(@Param("now") LocalDateTime now);
}
