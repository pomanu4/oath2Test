package ua.com.oauthLoginTry2.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.com.oauthLoginTry2.component.RoleSuplaer;
import ua.com.oauthLoginTry2.entity.CustomUserDetails;
import ua.com.oauthLoginTry2.entity.Person;
import ua.com.oauthLoginTry2.service.PersonServiceInerf;

@Controller
public class AppController {
	
	@Autowired
	private OAuth2AuthorizedClientService service;
	
	@Autowired
	private PersonServiceInerf personServ;
	
	@RequestMapping(path= {"/"}, method=RequestMethod.GET)
	public String indexPage(Model model) {
		Person formObject = new Person();
		model.addAttribute("line", "hello");
		model.addAttribute("formObject", formObject);
		model.addAttribute("roles", RoleSuplaer.supplyRoles());
		return "index";
	}
	
	@RequestMapping(path= {"page"}, method=RequestMethod.GET)
	public String toSecurePage( Authentication authentication ) {
		
//		if(authentication.getClass() == UsernamePasswordAuthenticationToken.class) {
//			System.out.println("form login");
//		}else if (authentication.getClass() == OAuth2AuthenticationToken.class) {
//			System.out.println("oauth login");
//		}
		
//		OAuth2AuthorizedClient client = service.loadAuthorizedClient(token.getAuthorizedClientRegistrationId(), token.getName());
//		OAuth2AccessToken accessToken = client.getAccessToken();
//		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		
//		OAuth2User user = token.getPrincipal();
//		Map<String, Object> attributes = user.getAttributes();
//		String name = (String) attributes.get("given_name");
		
	
		
		return "secured";
	}
	
	@RequestMapping(path= {"registration"}, method=RequestMethod.POST)
	public String registrUser(@ModelAttribute("formObject") Person person) {
//		personServ.savePerson(person);
		return "redirect:/";
	}

}
