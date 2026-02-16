package com.deep_search.deep_search.repository;

import com.deep_search.deep_search.entity.CustomerQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerQueryRepository extends JpaRepository<CustomerQuery, Long> {

    List<CustomerQuery> findByEmailOrderByCreatedAtDesc(String email);

    List<CustomerQuery> findByStatusOrderByCreatedAtDesc(String status);
}

