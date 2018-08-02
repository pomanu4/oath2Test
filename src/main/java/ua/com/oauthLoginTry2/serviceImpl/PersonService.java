package ua.com.oauthLoginTry2.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.oauthLoginTry2.dao.PersonDAOIntrf;
import ua.com.oauthLoginTry2.entity.CustomUserDetails;
import ua.com.oauthLoginTry2.entity.Person;
import ua.com.oauthLoginTry2.service.PersonServiceInerf;

@Service
@Transactional
public class PersonService implements PersonServiceInerf{
	
	@Autowired
	private PersonDAOIntrf personDAO;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public void savePerson(Person person) {
		String encoded = encoder.encode(person.getPassword());
		person.setPassword(encoded);
		person.setUserDetails(new CustomUserDetails());
		personDAO.save(person);		
	}

	@Override
	public List<Person> getAllUsers() {
		return personDAO.findAll();
	}

	@Override
	public Person findByEmail(String email) {
		return personDAO.findByEmail(email);
	}
	
}
