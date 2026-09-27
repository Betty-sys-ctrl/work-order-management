package com.example.demo.repository;
import com.example.demo.model.StatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StatusHistoryRepository extends JpaRepository<StatusHistory, Long> {
}