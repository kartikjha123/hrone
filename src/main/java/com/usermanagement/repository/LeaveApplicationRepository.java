package com.usermanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.LeaveApplication;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

//	@Query("SELECT la FROM LeaveApplication la WHERE la.employee.id = :employeeId")
//	List<LeaveApplication> findByEmployeeId(@Param("employeeId") Long employeeId);
//
//	List<LeaveApplication> findByManagerIdAndStatus(Long managerId, String status);
	
	
	 // Employee ki saari leaves
    @Query("SELECT l FROM LeaveApplication l WHERE l.employee.id = :employeeId ORDER BY l.appliedAt DESC")
    List<LeaveApplication> findByEmployeeId(@Param("employeeId") Long employeeId);

    // ✅ Manager ke pending leaves
    @Query("SELECT l FROM LeaveApplication l WHERE l.managerId = :managerId AND l.status = :status ORDER BY l.appliedAt ASC")
    List<LeaveApplication> findByManagerIdAndStatus(
            @Param("managerId") Long managerId,
            @Param("status") String status);

    // ✅ Manager ke sab leaves (all status)	
    @Query("SELECT l FROM LeaveApplication l WHERE l.managerId = :managerId ORDER BY l.appliedAt DESC")
    List<LeaveApplication> findByManagerId(@Param("managerId") Long managerId);

    // ✅ Status filter
    @Query("SELECT l FROM LeaveApplication l WHERE l.employee.id = :employeeId AND l.status = :status")
    List<LeaveApplication> findByEmployeeIdAndStatus(
            @Param("employeeId") Long employeeId,
            @Param("status") String status);

    // ✅ Date range filter
    @Query("SELECT l FROM LeaveApplication l WHERE l.employee.id = :employeeId " +
           "AND l.fromDate >= :from AND l.toDate <= :to ORDER BY l.fromDate DESC")
    List<LeaveApplication> findByEmployeeIdAndDateRange(
            @Param("employeeId") Long employeeId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to);
}
