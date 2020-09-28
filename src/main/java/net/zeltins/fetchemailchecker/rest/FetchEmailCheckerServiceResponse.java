package net.zeltins.fetchemailchecker.rest;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Peter Zeltins
 * Response class for Fetch email checker service
 *
 */
@ResponseBody
public class FetchEmailCheckerServiceResponse {

	private final String requestId;
	private final Integer uniqueEmails;
	
	public FetchEmailCheckerServiceResponse(String requestId, Integer uniqueEmails) {
		this.requestId = requestId;
		this.uniqueEmails = uniqueEmails;
	}
	
	/**
	 * @return Request ID
	 */
	public String getRequestId() {
		return requestId;
	}
	
	/**
	 * @return Number of unique emails in original request
	 */
	public Integer getUniqueEmails() {
		return uniqueEmails;
	}
	
	
}
