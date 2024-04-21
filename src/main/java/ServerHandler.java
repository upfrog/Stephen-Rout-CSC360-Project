import java.util.ArrayList;

import org.springframework.web.client.RestClient;


/*
 * Add a class to setup the server by adding the various classes
 */

public enum ServerHandler
{
	INSTANCE;

	private RestClient client = RestClient.create();
	
	public record Desc(String displayName, String description, String location) {};

	public record Response(String request,
			boolean successful,
			String message,
			User data) {};

	public record PostRecord(String content, int likes, ArrayList<Comment> comments, 
			boolean isPublic, User creator) {};
	
	public RestClient getClient()
	{
		return this.client;
	}
	
	
	
	
	
	public void pushUser(User user)
	{
		String location = "http://localhost:9000/v1/StephenRout/Users/" + user.getUID();
		

		Response response = INSTANCE.client.post()
				.uri(location)
				.body(user)
				.retrieve()
				.body(Response.class);
		
		System.out.println(response);
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
	
	public static void main(String[] args)
	{
		INSTANCE.testPost();
		User user1 = new User("Individual");
		
		INSTANCE.pushUser(user1);
		
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public ArrayList<UserPost> fetchPostList(String userUID)
	{
		String location = "http://localhost:9000/v1/StephenRout/Users/" + userUID + "/Posts";
		Response response = INSTANCE.client.get()
				.uri(location)
				.retrieve()
				.body(Response.class);
		
		return response.data;
	}
	
	public void pushPost(String userUID, Post user1Post1)
	{
		String location = "http://localhost:9000/v1/StephenRout/Users/" + userUID + "/Posts";
		

		Response response = INSTANCE.client.post()
				.uri(location)
				.body(user1Post1)
				.retrieve()
				.body(Response.class);
	}
	*/
	
}