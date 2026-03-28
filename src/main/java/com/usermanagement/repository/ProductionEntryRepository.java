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
	
	
	//
	
	
	// ✅ Salary ke liye — approved overtime amount
	@Query("""
	    SELECT COALESCE(SUM(p.amount), 0)
	    FROM ProductionEntry p
	    WHERE p.employee.id = :empId
	      AND p.status = 'APPROVED'
	      AND p.isOvertime = true
	      AND MONTH(p.workDate) = :month
	      AND YEAR(p.workDate) = :year
	    """)
	Double sumApprovedOvertimeAmount(
	        @Param("empId") Long empId,
	        @Param("month") int month,
	        @Param("year") int year);

	// ✅ Monthly overtime entries list
	@Query("""
	    SELECT p FROM ProductionEntry p
	    WHERE p.employee.id = :empId
	      AND p.isOvertime = true
	      AND (:status IS NULL OR p.status = :status)
	      AND MONTH(p.workDate) = :month
	      AND YEAR(p.workDate) = :year
	    ORDER BY p.workDate DESC
	    """)
	List<ProductionEntry> findOvertimeEntries(
	        @Param("empId") Long empId,
	        @Param("status") String status,
	        @Param("month") int month,
	        @Param("year") int year);

	// ✅ Monthly overtime summary
	@Query("""
	    SELECT 
	        COUNT(p),
	        COALESCE(SUM(p.quantity), 0),
	        COALESCE(SUM(p.amount), 0)
	    FROM ProductionEntry p
	    WHERE p.employee.id = :empId
	      AND p.isOvertime = true
	      AND p.status = 'APPROVED'
	      AND MONTH(p.workDate) = :month
	      AND YEAR(p.workDate) = :year
	    """)
	Object[] getOvertimeSummary(
	        @Param("empId") Long empId,
	        @Param("month") int month,
	        @Param("year") int year);

	// ✅ Aaj ki overtime entries
	@Query("""
	    SELECT p FROM ProductionEntry p
	    WHERE p.employee.id = :empId
	      AND p.isOvertime = true
	      AND p.workDate = :today
	    ORDER BY p.id DESC
	    """)
	List<ProductionEntry> findTodayOvertimeEntries(
	        @Param("empId") Long empId,
	        @Param("today") LocalDate today);

	// ✅ Aaj ka overtime total
	@Query("""
	    SELECT 
	        COUNT(p),
	        COALESCE(SUM(p.quantity), 0),
	        COALESCE(SUM(p.amount), 0)
	    FROM ProductionEntry p
	    WHERE p.employee.id = :empId
	      AND p.isOvertime = true
	      AND p.workDate = :today
	    """)
	Object[] getTodayOvertimeSummary(
	        @Param("empId") Long empId,
	        @Param("today") LocalDate today);
	
	
	
	
	
	// ✅ Daily graph — overtime bhi saath
	@Query("""
	    SELECT
	        p.workDate,
	        COUNT(p),
	        SUM(p.quantity),
	        SUM(p.amount),
	        SUM(CASE WHEN p.status = 'APPROVED' THEN 1 ELSE 0 END),
	        SUM(CASE WHEN p.status = 'PENDING'  THEN 1 ELSE 0 END),
	        SUM(CASE WHEN p.status = 'REJECTED' THEN 1 ELSE 0 END),
	        SUM(CASE WHEN p.isOvertime = true   THEN 1 ELSE 0 END),
	        SUM(CASE WHEN p.isOvertime = true   THEN p.quantity ELSE 0 END),
	        SUM(CASE WHEN p.isOvertime = true   THEN p.amount   ELSE 0 END)
	    FROM ProductionEntry p
	    WHERE MONTH(p.workDate) = :month
	      AND YEAR(p.workDate)  = :year
	    GROUP BY p.workDate
	    ORDER BY p.workDate ASC
	    """)
	List<Object[]> findDailyProductionGraph(
	        @Param("month") int month,
	        @Param("year") int year);


	// ✅ Monthly totals — overtime bhi saath
	@Query("""
		    SELECT
		        COUNT(p),
		        SUM(p.quantity),
		        SUM(p.amount),
		        SUM(CASE WHEN p.status = 'APPROVED' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN p.status = 'PENDING'  THEN 1 ELSE 0 END),
		        SUM(CASE WHEN p.status = 'REJECTED' THEN 1 ELSE 0 END),
		        SUM(CASE WHEN p.isOvertime = true   THEN 1 ELSE 0 END),
		        SUM(CASE WHEN p.isOvertime = true   THEN p.quantity ELSE 0 END),
		        SUM(CASE WHEN p.isOvertime = true   THEN p.amount   ELSE 0 END)
		    FROM ProductionEntry p
		    WHERE MONTH(p.workDate) = :month
		      AND YEAR(p.workDate)  = :year
		""")
		List<Object[]> findMonthlyTotals(@Param("month") int month,
		                                 @Param("year") int year);
	
	
	@Query("SELECT COUNT(p) FROM ProductionEntry p WHERE " +
		       "p.employee.id IN :ids AND MONTH(p.workDate) = :month AND YEAR(p.workDate) = :year")
		long countByEmployeeIdsAndMonth(@Param("ids") List<Long> ids,
		        @Param("month") int month, @Param("year") int year);

		@Query("SELECT COUNT(p) FROM ProductionEntry p WHERE " +
		       "p.employee.id IN :ids AND p.status = :status " +
		       "AND MONTH(p.workDate) = :month AND YEAR(p.workDate) = :year")
		long countByEmployeeIdsStatusAndMonth(@Param("ids") List<Long> ids,
		        @Param("status") String status,
		        @Param("month") int month, @Param("year") int year);

		@Query("SELECT COALESCE(SUM(p.amount), 0) FROM ProductionEntry p WHERE " +
		       "p.employee.id IN :ids AND MONTH(p.workDate) = :month AND YEAR(p.workDate) = :year")
		double sumAmountByEmployeeIdsAndMonth(@Param("ids") List<Long> ids,
		        @Param("month") int month, @Param("year") int year);

		@Query("SELECT COALESCE(SUM(p.amount), 0) FROM ProductionEntry p WHERE " +
		       "p.employee.id IN :ids AND p.status = :status " +
		       "AND MONTH(p.workDate) = :month AND YEAR(p.workDate) = :year")
		double sumAmountByEmployeeIdsStatusAndMonth(@Param("ids") List<Long> ids,
		        @Param("status") String status,
		        @Param("month") int month, @Param("year") int year);

		@Query("SELECT COUNT(p) FROM ProductionEntry p WHERE " +
		       "p.employee.id IN :ids AND p.isOvertime = true " +
		       "AND MONTH(p.workDate) = :month AND YEAR(p.workDate) = :year")
		long countOvertimeByEmployeeIdsAndMonth(@Param("ids") List<Long> ids,
		        @Param("month") int month, @Param("year") int year);

		@Query("SELECT COALESCE(SUM(p.amount), 0) FROM ProductionEntry p WHERE " +
		       "p.employee.id IN :ids AND p.isOvertime = true " +
		       "AND MONTH(p.workDate) = :month AND YEAR(p.workDate) = :year")
		double sumOvertimeAmountByEmployeeIdsAndMonth(@Param("ids") List<Long> ids,
		        @Param("month") int month, @Param("year") int year);

		// SuperAdmin — department-wise summary
		@Query("SELECT e.department, COUNT(p), SUM(p.amount) " +
		       "FROM ProductionEntry p JOIN p.employee e WHERE " +
		       "MONTH(p.workDate) = :month AND YEAR(p.workDate) = :year " +
		       "GROUP BY e.department ORDER BY SUM(p.amount) DESC")
		List<Object[]> findDepartmentWiseSummary(
		        @Param("month") int month, @Param("year") int year);
	

}
