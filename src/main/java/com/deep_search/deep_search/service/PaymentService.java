package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.PaymentRequest;
import com.deep_search.deep_search.dto.PaymentResponse;
import com.deep_search.deep_search.entity.Payment;
import com.deep_search.deep_search.entity.Subscription;
import com.deep_search.deep_search.entity.User;
import com.deep_search.deep_search.repository.PaymentRepository;
import com.deep_search.deep_search.repository.SubscriptionRepository;
import com.deep_search.deep_search.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          UserRepository userRepository,
                          SubscriptionRepository subscriptionRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    // Create - Process Payment
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment for user ID: {}, amount: {}", request.getUserId(), request.getAmount());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        Subscription subscription = null;
        if (request.getSubscriptionId() != null) {
            subscription = subscriptionRepository.findById(request.getSubscriptionId())
                    .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + request.getSubscriptionId()));
        }

        Payment payment = new Payment(user, subscription, request.getAmount(), request.getPaymentMethod());
        if (request.getTransactionId() != null) {
            payment.setTransactionId(request.getTransactionId());
        }

        // Simulate payment processing - in production, integrate with payment gateway
        payment.setPaymentStatus("COMPLETED");

        Payment saved = paymentRepository.save(payment);
        return toResponse(saved);
    }

    // Read - Get All
    public List<PaymentResponse> getAllPayments() {
        log.info("Fetching all payments");
        return paymentRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Read - Get By ID
    public PaymentResponse getPaymentById(Integer paymentId) {
        log.info("Fetching payment with ID: {}", paymentId);
        return paymentRepository.findById(paymentId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));
    }

    // Read - Get By User ID
    public List<PaymentResponse> getPaymentsByUserId(Integer userId) {
        log.info("Fetching payments for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return paymentRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Read - Get By User ID and Status
    public List<PaymentResponse> getPaymentsByUserIdAndStatus(Integer userId, String status) {
        log.info("Fetching payments for user ID: {} with status: {}", userId, status);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return paymentRepository.findByUserAndPaymentStatus(user, status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Update - Update Payment Status
    @Transactional
    public PaymentResponse updatePaymentStatus(Integer paymentId, String status) {
        log.info("Updating payment ID: {} status to: {}", paymentId, status);
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));

        payment.setPaymentStatus(status);
        Payment updated = paymentRepository.save(payment);
        return toResponse(updated);
    }

    // Delete
    @Transactional
    public void deletePayment(Integer paymentId) {
        log.info("Deleting payment ID: {}", paymentId);
        if (!paymentRepository.existsById(paymentId)) {
            throw new RuntimeException("Payment not found with ID: " + paymentId);
        }
        paymentRepository.deleteById(paymentId);
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getUser().getUserId(),
                payment.getUser().getName(),
                payment.getSubscription() != null ? payment.getSubscription().getSubscriptionId() : null,
                payment.getAmount(),
                payment.getPaymentDate(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId()
        );
    }
}

