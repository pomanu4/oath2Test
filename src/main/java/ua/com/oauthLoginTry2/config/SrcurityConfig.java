package ua.com.oauthLoginTry2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@EnableWebSecurity
@PropertySource("classpath:OAuth/credentials.properties")
public class SrcurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("USER");
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/registration").permitAll()
		.and()
		.authorizeRequests().antMatchers("/page").hasRole("USER")
		.and()
		.formLogin()
		.usernameParameter("email")
		.passwordParameter("password")
		.and()
		.logout().logoutUrl("/logout").logoutSuccessUrl("/")
		.and()
		.oauth2Login()
		.clientRegistrationRepository(clientRegistrationRepository())
		.authorizedClientService(auth2AuthorizedClientService());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {	
		 return  PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(githubClientRegistration(), googleClientRegistration());
	}

	@Bean
	public OAuth2AuthorizedClientService auth2AuthorizedClientService() {
		return new InMemoryOAuth2AuthorizedClientService(this.clientRegistrationRepository());
	}
	
	private ClientRegistration githubClientRegistration() {
		return CommonOAuth2Provider.GITHUB.getBuilder("github")
			.clientId(env.getProperty("github.client.id"))
			.clientSecret(env.getProperty("github.client.secret"))
			.build();
	}
	
	private ClientRegistration googleClientRegistration() {
		return CommonOAuth2Provider.GOOGLE.getBuilder("google")
			.clientId(env.getProperty("google.client.id"))
			.clientSecret(env.getProperty("google.client.secret"))
			.build();
	}

}
