import java.util.ArrayList;

import org.springframework.web.client.RestClient;


/*
 * This class feels very inefficient. I would like to condense the various post and get methods, as 
 * they have a great deal of re-used code, but that will involve a fair bit of extra effort.
 */

public enum ServerHandler
{
	INSTANCE;

	private RestClient client = RestClient.create();
	private static String base = "http://localhost:9000/v1/StephenRout";
	private static String[] classList = {"Users","UserPosts", "Comments", "JobPosts", "WorkExperiences"};
	


		
		/*	
	public record PostRecord(String content, int likes, ArrayList<Comment> comments, 
			boolean isPublic, User creator) {};*/
	
	public RestClient getClient()
	{
		return this.client;
	}
	
	
	public record PutResponse(String request,
			boolean successful,
			String message,
			String data) {};
	
	
	
	
	public record CommentResponse(String request,
			boolean successful,
			String message,
			Comment  data) {};	
	
	/*
	 * Consider expanding this to also take the posting user as a parameter (or just get 
	 * it from the comment) and put it in the user's comment list. THis is waiting on
	 * getting my question about REST update methods answered.
	 */
	public void postComment(Comment comment)
	{
		String location = base + "/Comment/" + comment.getUID();
		
		try
		{
			//Post the comment the global comment list
			CommentResponse response = INSTANCE.client.post()
					.uri(location)
					.body(comment)
					.retrieve()
					.body(CommentResponse.class);
			
			Post post = client.get()
					.uri(null)
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void putComment(Comment comment)
	{
		String location = base + "/JobPosts/" + comment.getUID();
		
		try
		{
			PutResponse response = INSTANCE.client.put()
					.uri(location)
					.body(comment)
					.retrieve()
					.body(PutResponse.class);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public Comment getCommnent(String UID)
	{
		String location = base + "/Comment/" + UID;
		CommentResponse response = null;
		try
		{
			response = client.get()
					.uri(location)
					.retrieve()
					.body(CommentResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response.data();
	}
	
	
	
	
	public record UserPostResponse(String request,
			boolean successful,
			String message,
			UserPost  data) {};	
	
	public void postUserPost(UserPost userPost)
	{
		String location = base + "/UserPosts/" + userPost.getUID();
		
		try
		{
			//push the post to the global UserPost list
			UserPostResponse response = INSTANCE.client.post()
					.uri(location)
					.body(userPost)
					.retrieve()
					.body(UserPostResponse.class);
			
			//push the post to the creator's post list
			User postingUser = client.get()
					.uri(location)
					.retrieve()
					.body(User.class);
			//postingUser.add
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void putUserPost(UserPost userPost)
	{
		String location = base + "/JobPosts/" + userPost.getUID();
		
		try
		{
			PutResponse response = INSTANCE.client.put()
					.uri(location)
					.body(userPost)
					.retrieve()
					.body(PutResponse.class);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public UserPost getUserPost(String UID)
	{
		String location = base + "/UserPosts/" + UID;
		UserPostResponse response = null;
		try
		{
			response = client.get()
					.uri(location)
					.retrieve()
					.body(UserPostResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response.data();
	}
	
	
	
	//JOB POST METHODS
	public record JobPostResponse(String request,
			boolean successful,
			String message,
			JobPost data) {};	
	
	public void postJobPost(JobPost jobPost)
	{
		String location = base + "/JobPosts/" + jobPost.getUID();
		
		try
		{
			JobPostResponse response = INSTANCE.client.post()
					.uri(location)
					.body(jobPost)
					.retrieve()
					.body(JobPostResponse.class);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void putJobPost(JobPost jobPost)
	{
		String location = base + "/JobPosts/" + jobPost.getUID();
		
		try
		{
			PutResponse response = INSTANCE.client.put()
					.uri(location)
					.body(jobPost)
					.retrieve()
					.body(PutResponse.class);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public JobPost getJobPost(String UID)
	{
		String location = base + "/JobPosts/" + UID;
		JobPostResponse response = null;
		try
		{
			response = client.get()
					.uri(location)
					.retrieve()
					.body(JobPostResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response.data();
	}
	
	
	
	
	
	
	
	
	
	
	public record UserResponse(String request,
			boolean successful,
			String message,
			User  data) {};	
	
	public void postUser(User user)
	{
		String location = base + "/Users/" + user.getUID();
		try
		{
			UserResponse response = INSTANCE.client.post()
					.uri(location)
					.body(user)
					.retrieve()
					.body(UserResponse.class);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void putUser(User user)
	{
		String location = base + "/Users/" + user.getUID();
		try
		{
			
			PutResponse response = INSTANCE.client.put()
					.uri(location)
					.body(user)
					.retrieve()
					.body(PutResponse.class);
					
			/*
			System.out.println(INSTANCE.client.put()
					.uri(location)
					.body(user)
					.retrieve()
					.body(String.class));
					*/
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public User getUser(String UID)
	{
		String location = base + "/Users/" + UID;
		UserResponse response = null;
		try
		{
			response = client.get()
					.uri(location)
					.retrieve()
					.body(UserResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response.data();
	}
	

	public record Desc(String displayName, String description, String location) {};

	public record DescResponse(String request,
			boolean successful,
			String message,
			Desc  data) {};
	
	public void configureServer()
	{
		try
		{
			System.out.println("Made it into test post");
			//I'm not currently using these Strings, but they'll make debugging easier
			String teamResponse = 
				client.post()
				.uri("http://localhost:9000/v1/StephenRout")
				.body(new Desc("StephenRout", "Stephen Rout's project: Nexus", ""))
				.retrieve()
				.body(String.class);
		}
		catch (Exception e) 
		{
			throw e;
		}
		
		for (String className : classList)
		{
			try
			{
				String response = 
					client.post()
					.uri(base + "/" + className)
					.body(new Desc(className, className + " objects", ""))
					.retrieve()
					.body(String.class);
			} catch (Exception e)
			{
				throw e;
			}
		}
	}
	
	public void clearServer()
	{
		try
		{
			String response = 
				client.delete()
				.uri(base)
				.retrieve()
				.body(String.class);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public static void main(String[] args)
	{
		/*
		INSTANCE.configureServer();
		//INSTANCE.clearServer();
		*/
		User user1 = new User("Individual");
		user1.setDisplayName(new Name("Zim Mer"));
		System.out.println("The local user's UID is: " + user1.getUID());
		INSTANCE.postUser(user1);
		
		User user2 = INSTANCE.getUser(user1.getUID());
		System.out.println(user2.getUID());
		
		user2.setDisplayName(new Name("Jeacowitz"));
		INSTANCE.putUser(user2);
		
		
		/*
		UserResponse user = INSTANCE.client.delete()
				.uri("http://localhost:9000/v1/StephenRout/Users/" + user1.getUID())
				.retrieve()
				.body(UserResponse.class);
		
		System.out.println(user.toString());
		
		User newUser = user.data;
		User newUser2 = user.data;
		System.out.println("The UID of the pulled (but not transferred) user is: " + user.data.getUID());
		System.out.println("After pulling and transferring to a new user, the UID is: " + newUser.getUID());
		System.out.println(newUser2.getUID());
				
		*/
		
		
	
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