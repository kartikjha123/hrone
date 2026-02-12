package com.usermanagement.repository;

import com.usermanagement.entity.AttendanceRegularization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AttendanceRegularizationRepository extends JpaRepository<AttendanceRegularization, Long> {
    @Query("SELECT ar FROM AttendanceRegularization ar WHERE ar.employee.id = :employeeId")
    List<AttendanceRegularization> findByEmployeeId(@Param("employeeId") Long employeeId);
    @Query("SELECT ar FROM AttendanceRegularization ar WHERE ar.manager.id = :managerId AND ar.status = :status")
    List<AttendanceRegularization> findByManagerIdAndStatus(@Param("managerId") Long managerId, @Param("status") String status);
}
