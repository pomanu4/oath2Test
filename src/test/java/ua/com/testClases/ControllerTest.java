package ua.com.testClases;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.oauthLoginTry2.config.AppConfig;
import ua.com.oauthLoginTry2.controller.AppController;
import ua.com.oauthLoginTry2.entity.Person;
import ua.com.oauthLoginTry2.entity.Role;
import ua.com.oauthLoginTry2.service.PersonServiceInerf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes= {AppConfig.class})
@TestPropertySource("classpath:test/test.properties")
public class ControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicatioContext;
	
	@Autowired
	PersonServiceInerf personSericeMOCK;
	
	@Autowired
	private Environment env;
	
	@Before
	public void setUp() {		
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicatioContext).build();
	}
	
	@Test
	public void checkServletandControllerInstancess() {
		ServletContext servletContext = webApplicatioContext.getServletContext();
		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(webApplicatioContext.getBean(AppController.class));
	}
	
	@Test
	public void controllerIndexPageMethodTest() throws Exception {
		
		mockMvc.perform(get("/")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().contentType("text/html;charset=UTF-8"))
		.andExpect(view().name("index"))
		.andExpect(model().attribute("line", "hello"))
		.andExpect(model().attribute("roles", hasItemInArray(Role.ROLE_ADMIN) ));
		
	}
	
	@Test
	@Rollback
	public void controlleRegistrUserMethodTest() throws Exception {
		PersonServiceInerf personSericeMOCK = Mockito.mock(PersonServiceInerf.class);
		Mockito.doNothing().when(personSericeMOCK).savePerson(new Person());
		
		mockMvc.perform(post("/registration").param("password", "password")).andDo(print())
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/"))
		.andExpect(view().name("redirect:/"))
		.andExpect(model().attributeExists("formObject"));
	}
	
	@Test
	public void controllerToSecurePageMethodTest() throws Exception {
		mockMvc.perform(get("/page")).andDo(print())
		.andExpect(status().isOk())
		.andExpect(view().name("secured"));
	}
	
	@Test
	public void propertySourceTest() {
		String mustBe = "test property line";
		Assert.assertEquals(mustBe, env.getProperty("test.test"));
	}
	
	@Test
	public void personServiceTest() {
		String email = "poma@111";
		Person person = personSericeMOCK.findByEmail(email);
		Assert.assertEquals(person.getEmail(), "poma@111");
	}
	
	

}
