package com.usermanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.ProductionEntry;

@Repository
public interface ProductionEntryRepository  extends JpaRepository<ProductionEntry, Long>{
	
	 @Query("""
		        SELECT pe FROM ProductionEntry pe
		        JOIN FETCH pe.employee e
		        JOIN FETCH pe.item i
		        WHERE (:employeeId IS NULL OR e.id = :employeeId)
		          AND (:itemId IS NULL OR i.id = :itemId)
		          AND (:fromDate IS NULL OR pe.workDate >= :fromDate)
		          AND (:toDate IS NULL OR pe.workDate <= :toDate)
		        ORDER BY pe.workDate DESC
		    """)
	Page<ProductionEntry> filterProductionEntries(@Param("employeeId") Long employeeId, @Param("itemId") Long itemId,
			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate,Pageable pageable);

	@Query("SELECT SUM(pe.amount) FROM ProductionEntry pe WHERE pe.employee.id = :employeeId AND pe.status = 'APPROVED' AND pe.workDate BETWEEN :startDate AND :endDate")
	Double sumApprovedAmountByEmployeeAndDateRange(@Param("employeeId") Long employeeId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
	
	
	
	@Query("""
	        SELECT p FROM ProductionEntry p
	        WHERE p.employee.manager.id = :managerId
	        AND (:status IS NULL OR p.status = :status)
	        AND (:fromDate IS NULL OR p.workDate >= :fromDate)
	        AND (:toDate IS NULL OR p.workDate <= :toDate)
	        ORDER BY p.workDate DESC
	        """)
	Page<ProductionEntry> findByManagerId(
	        @Param("managerId") Long managerId,
	        @Param("status") String status,
	        @Param("fromDate") LocalDate fromDate,
	        @Param("toDate") LocalDate toDate,
	        Pageable pageable);
	
	
	
	
	
	
	
	// Entries with filter
	@Query("""
	        SELECT p FROM ProductionEntry p
	        WHERE p.employee.id = :employeeId
	        AND (:status IS NULL OR p.status = :status)
	        AND (:fromDate IS NULL OR p.workDate >= :fromDate)
	        AND (:toDate IS NULL OR p.workDate <= :toDate)
	        ORDER BY p.workDate DESC
	        """)
	Page<ProductionEntry> findMyEntries(
	        @Param("employeeId") Long employeeId,
	        @Param("status") String status,
	        @Param("fromDate") LocalDate fromDate,
	        @Param("toDate") LocalDate toDate,
	        Pageable pageable);

	// Summary counts
	@Query("""
	        SELECT COUNT(p) FROM ProductionEntry p
	        WHERE p.employee.id = :employeeId
	        AND (:status IS NULL OR p.status = :status)
	        """)
	Long countByEmployeeAndStatus(
	        @Param("employeeId") Long employeeId,
	        @Param("status") String status);

	// Total amount
	@Query("""
	        SELECT COALESCE(SUM(p.amount), 0)
	        FROM ProductionEntry p
	        WHERE p.employee.id = :employeeId
	        AND p.status = 'APPROVED'
	        """)
	Double getTotalApprovedAmount(@Param("employeeId") Long employeeId);
	
	
	
	// Dashboard summary ke liye
	@Query("""
	        SELECT COUNT(p) FROM ProductionEntry p
	        WHERE p.employee.id = :employeeId
	        AND (:status IS NULL OR p.status = :status)
	        AND MONTH(p.workDate) = :month
	        AND YEAR(p.workDate) = :year
	        """)
	Long countByEmployeeStatusMonthYear(
	        @Param("employeeId") Long employeeId,
	        @Param("status") String status,
	        @Param("month") int month,
	        @Param("year") int year);

	@Query("""
	        SELECT COALESCE(SUM(p.amount), 0)
	        FROM ProductionEntry p
	        WHERE p.employee.id = :employeeId
	        AND p.status = :status
	        AND MONTH(p.workDate) = :month
	        AND YEAR(p.workDate) = :year
	        """)
	Double sumAmountByStatusMonthYear(
	        @Param("employeeId") Long employeeId,
	        @Param("status") String status,
	        @Param("month") int month,
	        @Param("year") int year);

	// Monthly report ke liye
	@Query("""
	        SELECT p FROM ProductionEntry p
	        WHERE p.employee.id = :employeeId
	        AND MONTH(p.workDate) = :month
	        AND YEAR(p.workDate) = :year
	        ORDER BY p.workDate ASC
	        """)
	List<ProductionEntry> findMonthlyEntries(
	        @Param("employeeId") Long employeeId,
	        @Param("month") int month,
	        @Param("year") int year);

	// Item-wise summary
	@Query("""
	        SELECT p.item.itemName,
	               SUM(p.quantity),
	               SUM(p.amount)
	        FROM ProductionEntry p
	        WHERE p.employee.id = :employeeId
	        AND p.workDate BETWEEN :fromDate AND :toDate
	        GROUP BY p.item.id, p.item.itemName
	        ORDER BY SUM(p.amount) DESC
	        """)
	List<Object[]> findItemWiseSummary(
	        @Param("employeeId") Long employeeId,
	        @Param("fromDate") LocalDate fromDate,
	        @Param("toDate") LocalDate toDate);

	// Manager employee-wise summary
	@Query("""
	        SELECT p.employee.id,
	               p.employee.firstName,
	               p.employee.lastName,
	               p.employee.employeeCode,
	               COUNT(p),
	               COALESCE(SUM(CASE WHEN p.status = 'APPROVED' THEN p.amount ELSE 0 END), 0),
	               COALESCE(SUM(CASE WHEN p.status = 'PENDING' THEN p.amount ELSE 0 END), 0)
	        FROM ProductionEntry p
	        WHERE p.employee.manager.id = :managerId
	        AND MONTH(p.workDate) = :month
	        AND YEAR(p.workDate) = :year
	        GROUP BY p.employee.id, p.employee.firstName,
	                 p.employee.lastName, p.employee.employeeCode
	        ORDER BY SUM(p.amount) DESC
	        """)
	List<Object[]> findManagerEmployeeSummary(
	        @Param("managerId") Long managerId,
	        @Param("month") int month,
	        @Param("year") int year);
	
	

}
