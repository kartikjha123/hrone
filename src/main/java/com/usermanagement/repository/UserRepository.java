package com.usermanagement.repository;

import java.util.Optional;

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

}
