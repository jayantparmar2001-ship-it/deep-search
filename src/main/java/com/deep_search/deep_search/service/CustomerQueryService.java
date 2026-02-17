package com.deep_search.deep_search.service;

import com.deep_search.deep_search.dto.CustomerQueryRequest;
import com.deep_search.deep_search.dto.CustomerQueryResponse;
import com.deep_search.deep_search.dto.UserQueriesResponse;
import com.deep_search.deep_search.entity.CustomerQuery;
import com.deep_search.deep_search.repository.CustomerQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /**
     * Get all queries for a specific user by email.
     */
    public UserQueriesResponse getUserQueries(String email) {
        try {
            log.info("Retrieving queries for email: {}", email);
            
            List<CustomerQuery> queries = customerQueryRepository.findByEmailOrderByCreatedAtDesc(email);
            
            if (queries.isEmpty()) {
                return UserQueriesResponse.error("No queries found for this user");
            }
            
            log.info("Found {} queries for email: {}", queries.size(), email);
            return UserQueriesResponse.success(
                    "Queries retrieved successfully",
                    queries
            );
        } catch (Exception e) {
            log.error("Error retrieving user queries: {}", e.getMessage(), e);
            return UserQueriesResponse.error("Failed to retrieve queries: " + e.getMessage());
        }
    }
}


