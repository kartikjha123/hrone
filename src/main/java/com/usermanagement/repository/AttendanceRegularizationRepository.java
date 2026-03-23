package com.usermanagement.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.AttendanceRegularization;

@Repository
public interface AttendanceRegularizationRepository
        extends JpaRepository<AttendanceRegularization, Long> {

    // Employee ki saari AR
    @Query("SELECT ar FROM AttendanceRegularization ar " +
           "WHERE ar.employee.id = :employeeId " +
           "ORDER BY ar.appliedAt DESC")
    List<AttendanceRegularization> findByEmployeeId(
            @Param("employeeId") Long employeeId);

    // Manager ke pending AR
    @Query("SELECT ar FROM AttendanceRegularization ar " +
           "WHERE ar.manager.id = :managerId " +
           "AND ar.status = :status " +
           "ORDER BY ar.appliedAt ASC")
    List<AttendanceRegularization> findByManagerIdAndStatus(
            @Param("managerId") Long managerId,
            @Param("status") String status);

    // Same date pe duplicate check
    @Query("SELECT ar FROM AttendanceRegularization ar " +
           "WHERE ar.employee.id = :employeeId " +
           "AND ar.missingDate = :date " +
           "AND ar.status <> 'REJECTED'")
    Optional<AttendanceRegularization> findActiveByEmployeeAndDate(
            @Param("employeeId") Long employeeId,
            @Param("date") LocalDate date);

    // Month wise used AR count
    @Query("SELECT COUNT(ar) FROM AttendanceRegularization ar " +
           "WHERE ar.employee.id = :employeeId " +
           "AND ar.arMonth = :month " +
           "AND ar.arYear = :year " +
           "AND ar.status <> 'REJECTED'")
    long countUsedAR(
            @Param("employeeId") Long employeeId,
            @Param("month") int month,
            @Param("year") int year);
    
    
    @Query("SELECT ar FROM AttendanceRegularization ar " +
    	       "WHERE ar.employee.id = :employeeId " +
    	       "AND (:month IS NULL OR ar.arMonth = :month) " +
    	       "AND (:year IS NULL OR ar.arYear = :year) " +
    	       "ORDER BY ar.appliedAt DESC")
    	List<AttendanceRegularization> findByEmployeeIdAndMonthYear(
    	        @Param("employeeId") Long employeeId,
    	        @Param("month") Integer month,
    	        @Param("year") Integer year);
}
