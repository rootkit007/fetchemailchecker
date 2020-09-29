This is Fetch Email Checker project. It is built in Java/Spring Boot.

Use Eclipse to run as "Spring Boot App", or run Maven goal "spring-boot:run"

Integration and unit tests can be run using Maven test goal

Sample JSON request provided in SampleJSONRequest.txt. The request expects "requestID" and string array "emails" in JSON-formatted POST request body. The response is "requestID" (same ID that was passed in the request) and "uniqueEmails", JSON-encoded.
  
Assuming local webserver is on port 8080 (default) the service can be tested with cURL as:

curl --data @SampleJSONRequest.txt -X POST -H "Content-Type: application/json" http://localhost:8080/getuniqueemails
