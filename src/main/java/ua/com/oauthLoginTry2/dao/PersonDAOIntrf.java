package ua.com.oauthLoginTry2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ua.com.oauthLoginTry2.entity.Person;

public interface PersonDAOIntrf extends JpaRepository<Person, Integer>{
	
	@Query("SELECT p FROM Person p WHERE p.email = (:email)")
	Person findByEmail(@Param("email") String email);

}
