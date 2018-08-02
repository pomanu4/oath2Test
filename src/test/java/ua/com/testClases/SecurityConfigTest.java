package ua.com.testClases;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;


import ua.com.oauthLoginTry2.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {AppConfig.class})
@WebAppConfiguration
public class SecurityConfigTest {
	
	@Autowired
	private WebApplicationContext webAppContext;
	
	@Autowired
	UserDetailsService detailsService;
		
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}
	
	@Test
	@WithUserDetails()
	public void acessToSecuredPage() throws Exception {
		mockMvc.perform(get("/page").with(user("qwerty").password("qwerty")))
		.andDo(print())
		.andExpect(status().isFound());
		
	}

}
