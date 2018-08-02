package ua.com.testClases;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.com.oauthLoginTry2.config.PersistenceConfig;
import ua.com.oauthLoginTry2.dao.PersonDAOIntrf;
import ua.com.oauthLoginTry2.entity.Person;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback(value=true)
@ContextConfiguration(classes= {PersistenceConfig.class})
public class JPARepositoryTest {
	
	@Autowired
	private PersonDAOIntrf personDAO;
	
	@Test
	public void repositoryFindAllTest() {
		List<Person> entities = personDAO.findAll();
		Assert.assertNotNull(entities);
		Assert.assertThat(entities, hasSize(5));
	}
	
	
}

	
	
	