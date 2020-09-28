package net.zeltins.fetchemailchecker.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import net.zeltins.fetchemailchecker.FetchEmailCheckerLogic;

@RestController
public class FetchEmailCheckerServiceController {

	public static final String ServicePath = "/getuniqueemails";
	
	@RequestMapping(method = RequestMethod.POST,path = ServicePath,consumes = MediaType.APPLICATION_JSON_VALUE)
	public FetchEmailCheckerServiceResponse GetUniqueEmails(@RequestBody FetchEmailCheckerServiceRequest request) throws Exception {
		
		FetchEmailCheckerLogic checker = new FetchEmailCheckerLogic();
		
		for ( String email : request.getEmails() ) {
			checker.AddEmail(email);
		}
		
		return new FetchEmailCheckerServiceResponse(
				request.getRequestId(), 
				checker.GetUniqueEmails());
		
	}
	
	
}
