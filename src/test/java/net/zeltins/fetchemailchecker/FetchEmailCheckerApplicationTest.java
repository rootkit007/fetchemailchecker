package net.zeltins.fetchemailchecker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import net.zeltins.fetchemailchecker.rest.FetchEmailCheckerServiceController;
import net.zeltins.fetchemailchecker.rest.FetchEmailCheckerServiceRequest;
import net.zeltins.fetchemailchecker.rest.FetchEmailCheckerServiceResponse;


/**
 * @author Peter Zeltins
 * Integration tests for Fetch email checker service
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FetchEmailCheckerApplicationTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private FetchEmailCheckerServiceController controller;
	
	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	
	/**
	 * Integration test to verify service is up and running
	 * Only does basic test to ensure service is working. More automated testing coverage in unit tests
	 * @throws Exception
	 */
	@Test
	public void TestGetUniquEmailsService() throws Exception {
		
		String requestId = UUID.randomUUID().toString();
		
		FetchEmailCheckerServiceRequest request = new FetchEmailCheckerServiceRequest();
		request.setRequestId(requestId);
		String[] emails = new String[] { "peter@zeltins.net",
				"peter@zeltins.net",
				"peter@gmail.com",
				"peter+1@gmail.com",
				"pe.t.er@gmail.com" };
		request.setEmails(emails);
		
		ResponseEntity<FetchEmailCheckerServiceResponse> response = this.restTemplate.postForEntity(
				"/" + FetchEmailCheckerServiceController.ServicePath,
				request, FetchEmailCheckerServiceResponse.class);
		
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getRequestId()).isEqualTo(requestId);
		assertThat(response.getBody().getUniqueEmails()).isEqualTo(2);
		
	}
	
}
