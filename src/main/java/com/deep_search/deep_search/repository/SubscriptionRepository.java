package com.deep_search.deep_search.repository;

import com.deep_search.deep_search.entity.Subscription;
import com.deep_search.deep_search.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    List<Subscription> findByUser(User user);

    List<Subscription> findByUserAndStatus(User user, String status);
}

