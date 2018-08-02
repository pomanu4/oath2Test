package ua.com.oauthLoginTry2.service;

import java.util.List;

import ua.com.oauthLoginTry2.entity.Person;

public interface PersonServiceInerf {
	
	void savePerson(Person person);
	
	List<Person> getAllUsers();
	
	Person findByEmail(String email);

}
