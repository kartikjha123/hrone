package com.usermanagement.repository;

import java.time.LocalDate;
import java.util.List;

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
	List<ProductionEntry> filterProductionEntries(@Param("employeeId") Long employeeId, @Param("itemId") Long itemId,
			@Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
	
	

}
