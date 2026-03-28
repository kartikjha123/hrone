package com.usermanagement.repository;

import com.usermanagement.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

	@Query("SELECT h FROM Holiday h WHERE YEAR(h.date) = :year AND h.active = true ORDER BY h.date ASC")
	List<Holiday> findByYear(@Param("year") int year);

	List<Holiday> findByTypeIgnoreCaseAndActiveTrue(String type);

	List<Holiday> findByStateIgnoreCaseAndActiveTrue(String state);

	@Query("SELECT h FROM Holiday h WHERE YEAR(h.date) = :year AND UPPER(h.type) = UPPER(:type) AND h.active = true ORDER BY h.date ASC")
	List<Holiday> findByYearAndType(@Param("year") int year, @Param("type") String type);

	@Query("SELECT h FROM Holiday h WHERE h.date >= :today AND h.active = true ORDER BY h.date ASC")
	List<Holiday> findUpcoming(@Param("today") LocalDate today);
	
	@Query("SELECT h FROM Holiday h WHERE MONTH(h.date) = :month AND YEAR(h.date) = :year AND h.active = true ORDER BY h.date ASC")
	List<Holiday> findByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
