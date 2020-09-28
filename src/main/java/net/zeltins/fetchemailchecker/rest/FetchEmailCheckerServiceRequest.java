package net.zeltins.fetchemailchecker.rest;

/**
 * @author Peter Zeltins
 * Request class for Fetch email checker service
 *
 */
public class FetchEmailCheckerServiceRequest {
	
	private String requestId;
	private String[] emails;

	
	public String getRequestId() {
		return requestId;
	}
	
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public String[] getEmails() {
		return emails;
	}
	
	public void setEmails(String[] emails) {
		this.emails = emails;
	}

}
