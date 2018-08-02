package ua.com.oauthLoginTry2.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ua.com.oauthLoginTry2.entity.Person;
import ua.com.oauthLoginTry2.service.PersonServiceInerf;

@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private PersonServiceInerf personServ;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Person person = personServ.findByEmail(username);
		if(person == null) {
			throw new UsernameNotFoundException("no user find with " + username);
		}
		return person.getUserDetails();
	}
	
}
