package com.deep_search.deep_search.repository;

import com.deep_search.deep_search.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByToken(String token);

    Optional<Session> findByTokenAndIsActiveTrue(String token);

    Optional<Session> findByUserIdAndIsActiveTrue(Integer userId);

    @Modifying
    @Query("UPDATE Session s SET s.isActive = false WHERE s.userId = :userId")
    void deactivateAllUserSessions(@Param("userId") Integer userId);

    @Modifying
    @Query("UPDATE Session s SET s.isActive = false WHERE s.token = :token")
    void deactivateSessionByToken(@Param("token") String token);

    @Modifying
    @Query("DELETE FROM Session s WHERE s.expiresAt < :now")
    void deleteExpiredSessions(@Param("now") LocalDateTime now);
}

