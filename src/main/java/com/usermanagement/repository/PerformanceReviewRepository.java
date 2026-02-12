package com.usermanagement.repository;

import com.usermanagement.entity.PerformanceReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Long> {
    @Query("SELECT pr FROM PerformanceReview pr WHERE pr.employee.id = :employeeId")
    List<PerformanceReview> findByEmployeeId(@Param("employeeId") Long employeeId);
}
