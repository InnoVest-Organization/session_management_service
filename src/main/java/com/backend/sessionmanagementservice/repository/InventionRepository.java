package com.backend.sessionmanagementservice.repository;

import com.backend.sessionmanagementservice.model.Invention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventionRepository extends JpaRepository<Invention, Long> {
}