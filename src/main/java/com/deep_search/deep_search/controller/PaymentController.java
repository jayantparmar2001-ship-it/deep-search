package com.deep_search.deep_search.controller;

import com.deep_search.deep_search.dto.PaymentRequest;
import com.deep_search.deep_search.dto.PaymentResponse;
import com.deep_search.deep_search.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Create - Process Payment
    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.processPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Read - Get All
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {
        List<PaymentResponse> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    // Read - Get By ID
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Integer paymentId) {
        PaymentResponse payment = paymentService.getPaymentById(paymentId);
        return ResponseEntity.ok(payment);
    }

    // Read - Get By User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByUserId(@PathVariable Integer userId) {
        List<PaymentResponse> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    // Read - Get By User ID and Status
    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByUserIdAndStatus(
            @PathVariable Integer userId,
            @PathVariable String status) {
        List<PaymentResponse> payments = paymentService.getPaymentsByUserIdAndStatus(userId, status);
        return ResponseEntity.ok(payments);
    }

    // Update - Update Payment Status
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Integer paymentId,
            @RequestParam String status) {
        PaymentResponse response = paymentService.updatePaymentStatus(paymentId, status);
        return ResponseEntity.ok(response);
    }

    // Delete
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }
}


