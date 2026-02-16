package com.deep_search.deep_search.repository;

import com.deep_search.deep_search.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

    List<Service> findByServiceNameContainingIgnoreCase(String serviceName);
}

