package com.deep_search.deep_search.repository;

import com.deep_search.deep_search.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    /**
     * Find the most recent unverified OTP for a phone number
     */
    @Query("SELECT o FROM Otp o WHERE o.phoneNumber = :phoneNumber AND o.isVerified = false ORDER BY o.createdAt DESC")
    Optional<Otp> findLatestUnverifiedOtpByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    /**
     * Mark all OTPs for a phone number as verified (cleanup after successful verification)
     */
    @Modifying
    @Query("UPDATE Otp o SET o.isVerified = true WHERE o.phoneNumber = :phoneNumber AND o.isVerified = false")
    void markAllAsVerifiedForPhone(@Param("phoneNumber") String phoneNumber);

    /**
     * Clean up expired OTPs (can be called periodically)
     */
    @Modifying
    @Query("DELETE FROM Otp o WHERE o.expiresAt < :now")
    void deleteExpiredOtps(@Param("now") LocalDateTime now);
}

