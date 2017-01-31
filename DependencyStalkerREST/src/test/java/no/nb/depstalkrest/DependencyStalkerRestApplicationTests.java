package no.nb.depstalkrest;

import no.nb.depstalkrest.repo.MysqlRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DependencyStalkerRestApplicationTests {


	@Autowired
	private TestRestTemplate restTemplate;

	@InjectMocks
	MysqlRepositoryImpl mysql;

	@Before
	public void setup() {
	}

	@Test
	public void test() {


//		String forObject = restTemplate.getForObject("/oddis", String.class);
//		assertEquals(forObject,"oddis");
//		System.out.println(forObject);

	}

}
