import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;


public enum ServerHandler
{
	INSTANCE;
	
	private RestClient client = RestClient.builder()
			.baseUrl("http://localhost:9000/v1/")
			.build();
	
	
	//private RestClient client = RestClient.create();
			
			
			//.create();
			
			/**

			**/
	
	
	public record Desc(String displayName, String description, String location) {};
	
	public record TestUserRecord(String name, 
			String description, 
			int pastJobs, 
			ArrayList<String> posts,
			TestUser firstFollower) {};
	
	public RestClient getClient()
	{
		return this.client;
	}
	
	public void testPost()
	{
		
		System.out.println("Made it into test post");
		Desc team = new Desc("StephenRout", "Stephen Rout's project: Nexus", "");
		System.out.println(
				client.post()
				.uri("http://localhost:9000/v1/StephenRout")
				.body(team)
				.retrieve()
				.body(String.class));
		
		
		System.out.println(
				client.post()
				.uri("http://localhost:9000/v1/StephenRout/User")
				.body(new Desc("User", "User objects", ""))
				.retrieve()
				.body(String.class));
	}
	
	
	public void postTestUser(TestUser user)
	{
		System.out.println(
				client.post()
				.uri("http://localhost:9000/v1/StephenRout/User/user1")
				.body(user)
				.retrieve()
				.body(String.class));
	}
	
	public static void main(String[] args)
	{
		INSTANCE.testPost();
		
		TestUser user1 = new TestUser("Inigo Montoya", "A number-peasant", 37);
		TestUser user2 = new TestUser("John Smith", "A number-king", 430179586);
		
		System.out.println(user1.getDisplayName());
		
		user1.setFirstFollower(user2);
		
		INSTANCE.postTestUser(user1);
		
		System.out.println(
				INSTANCE.client.get()
				.uri("http://localhost:9000/v1/StephenRout/User/user1")
				.retrieve()
				.body(String.class));
		
		TestUser downloadedUser1 = 
				(INSTANCE.client.get()
				.uri("http://localhost:9000/v1/StephenRout/User/user1")
				.retrieve()
				.body(TestUser.class));
		
		System.out.println(downloadedUser1.getDisplayName());
	}

}

