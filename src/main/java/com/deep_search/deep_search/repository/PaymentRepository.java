package com.deep_search.deep_search.repository;

import com.deep_search.deep_search.entity.Payment;
import com.deep_search.deep_search.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findByUser(User user);

    List<Payment> findByUserAndPaymentStatus(User user, String paymentStatus);
}


