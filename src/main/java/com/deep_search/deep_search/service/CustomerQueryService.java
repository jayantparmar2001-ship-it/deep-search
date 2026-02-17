package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.CustomerQueryRequest;
import com.deep_search.deep_search.dto.CustomerQueryResponse;
import com.deep_search.deep_search.entity.CustomerQuery;
import com.deep_search.deep_search.repository.CustomerQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerQueryService {

    private static final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);

    private final CustomerQueryRepository customerQueryRepository;

    public CustomerQueryService(CustomerQueryRepository customerQueryRepository) {
        this.customerQueryRepository = customerQueryRepository;
    }

    @Transactional
    public CustomerQueryResponse submitQuery(CustomerQueryRequest request) {
        try {
            log.info("Submitting customer query from email: {}", request.getEmail());

            CustomerQuery query = new CustomerQuery(
                    request.getEmail(),
                    request.getSubject(),
                    request.getMessage()
            );

            CustomerQuery savedQuery = customerQueryRepository.save(query);

            log.info("Customer query saved with ID: {}", savedQuery.getId());

            return CustomerQueryResponse.success(
                    "Query submitted successfully",
                    savedQuery.getId(),
                    savedQuery.getEmail(),
                    savedQuery.getSubject(),
                    savedQuery.getCreatedAt(),
                    savedQuery.getStatus()
            );
        } catch (Exception e) {
            log.error("Error submitting customer query: {}", e.getMessage(), e);
            return CustomerQueryResponse.error("Failed to submit query: " + e.getMessage());
        }
    }
}


