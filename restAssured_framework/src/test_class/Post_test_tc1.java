package test_class;

import java.io.IOException;
import java.time.LocalDate;

import org.testng.Assert;

import common_method.Common_method_api;
import common_method.Common_method_utilities;
import io.restassured.path.json.JsonPath;
import request_repository.Post_Request_Repository;

public class Post_test_tc1 {
	public static void orchestrator() throws IOException 
	{
		@Test
		String responseBody = "" ;
		int responseStatuscode = 0;
		String baseuri = Post_Request_Repository.baseuri();
		String resource = Post_Request_Repository.resource();
		String requestBody = Post_Request_Repository.Post_request_tc1();
		System.out.println(requestBody);
		for(int i=0 ; i<5 ; i++)
		{
			responseStatuscode = Common_method_api.responsestatuscode_extractor(baseuri, resource, requestBody);
			if(responseStatuscode == 201)
			{
		     responseBody = Common_method_api.responsebody_extractor(baseuri, resource, requestBody);
				            responseBodyValidator(responseBody,requestBody);
				      System.out.println(responseBody);  
				        break;
			        }
			else
			{
				System.out.println("correct status code is not found in the iteration" + i);
			}
		}
				Common_method_utilities.evidenceFileCreator("Post_tc1",requestBody,responseBody);
			
		     Assert.assertEquals(responseStatuscode, 201);
	}
	 
	public static void responseBodyValidator(String responseBody,String requestBody) 
	{
		// create jsonPath object to extract response body parameters
				JsonPath jsp = new JsonPath(responseBody);

				// extract response body parameters
				String res_name = jsp.getString("name");
				String res_job = jsp.getString("job");
				String res_id = jsp.getString("id");
				String res_createdAt = jsp.getString("createdAt");
				
				
				JsonPath jsp1 = new JsonPath(requestBody);

			
				String req_name = jsp1.getString("name");
				String req_job = jsp1.getString("job");
				

				// validate response body parameter
				Assert.assertEquals(res_name,  req_name);
				Assert.assertEquals(res_job, req_job);
				Assert.assertNotNull(res_id, "assertion error , id parameter is null");

				// extract date from createdAt parameter
				String actual_date = res_createdAt.substring(0, 10);
				String current_date = LocalDate.now().toString(); // Create a date object
				Assert.assertEquals(actual_date, current_date);
				System.out.println("Actual DATE : " + actual_date + "\nCURRENT DATE : " + current_date);

 	}

	
}
