package com.deep_search.deep_search.repository;

import com.deep_search.deep_search.entity.LabourRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabourRequestRepository extends JpaRepository<LabourRequest, Long> {

    List<LabourRequest> findByUserIdOrderByCreatedAtDesc(Integer userId);
}


