package com.deep_search.deep_search.repository;

import com.deep_search.deep_search.entity.LabourServiceMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabourServiceMappingRepository extends JpaRepository<LabourServiceMapping, Long> {

    List<LabourServiceMapping> findByLabourUserIdAndIsActiveTrueOrderByCreatedAtDesc(Integer labourUserId);

    boolean existsByLabourUserIdAndServiceIdAndIsActiveTrue(Integer labourUserId, Integer serviceId);
}


