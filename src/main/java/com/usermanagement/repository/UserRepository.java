package com.usermanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	@Query("""
	        select distinct u
	        from User u
	        left join fetch u.roles r
	        left join fetch r.privileges p
	        where u.username = :username
	        """)
	    Optional<User> findByUsernameWithRolesAndPrivileges(@Param("username") String username);
	
	Optional<User> findByUsername(String email);
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'MANAGER'")
	List<User> findAllManagers();
	
	@Query("""
	        SELECT u FROM User u
	        LEFT JOIN u.employee e
	        WHERE (:search IS NULL OR :search = ''
	               OR LOWER(e.firstName)    LIKE LOWER(CONCAT('%', :search, '%'))
	               OR LOWER(e.lastName)     LIKE LOWER(CONCAT('%', :search, '%'))
	               OR LOWER(e.employeeCode) LIKE LOWER(CONCAT('%', :search, '%')))
	        """)
	    Page<User> searchUsers(@Param("search") String search, Pageable pageable);
	
	Optional<User> findByEmail(String email);

}
