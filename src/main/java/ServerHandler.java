
import java.util.ArrayList;

import org.springframework.web.client.RestClient;


/*
 * This class feels very inefficient. I would like to condense the various post and get methods, as 
 * they have a great deal of re-used code.
 */

public enum ServerHandler
{
	INSTANCE;

	private RestClient client = RestClient.create();
	private static String base = "http://localhost:9000/v1/StephenRout";
	private static String[] classList = {"Users","UserPosts", "Comments", "JobPosts", "WorkExperiences"};

	
	public RestClient getClient()
	{
		return this.client;
	}
	
	public record PutDeleteResponse(String request,
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
		String location = base + "/Comments/" + comment.getUID();
		try
		{
			//Post the comment the global comment list
			INSTANCE.client.post()
					.uri(location)
					.body(comment)
					.retrieve()
					.body(String.class);
		}
		catch (Exception e)
		{	
			throw e;
		}
	}
	
	public void putComment(Comment comment)
	{
		String location = base + "/Comments/" + comment.getUID();
		PutDeleteResponse response = null;
		
		try
		{
			response = INSTANCE.client.put()
					.uri(location)
					.body(comment)
					.retrieve()
					.body(PutDeleteResponse.class);

		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public Comment getComment(String UID)
	{
		String location = base + "/Comments/" + UID;
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
	
	
	public PutDeleteResponse deleteComment(String UID)
	{
		String location = base + "/Comments/" + UID;
		PutDeleteResponse response = null;
		try
		{
			response = client.delete()
					.uri(location)
					.retrieve()
					.body(PutDeleteResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response;
	}
	
	public record AllCommentsResponse(String request,
			boolean successful,
			String message,
			ArrayList<CommentDesc>  data) {};
			
	private ArrayList<String> getAllCommentUIDs()
	{
			
				
		public ArrayList<String> getAllUsers()
		{
			String location = base + "/Users";
			AllUsersResponse response = null;
			try
			{
				response = client.get()
						.uri(location)
						.retrieve()
						.body(AllUsersResponse.class);			
			}
			catch (Exception e)
			{
				throw e;
			}
			
			ArrayList<String> descList = new ArrayList<String>();
			for (UserDesc val: response.data())
			{
				descList.add(val.name);
			}
			return descList;
		}
	}
	
	
	public ArrayList<Comment> getAllComments()
	{
		
	}
	
	
	
	
	public record UserPostResponse(String request,
			boolean successful,
			String message,
			UserPost  data) {};	
	
	public void postUserPost(UserPost userPost)
	{
		String location = base + "/UserPosts/" + userPost.getUID();
		UserPostResponse response = null;
		try
		{
			//push the post to the global UserPost list
			 response = INSTANCE.client.post()
					.uri(location)
					.body(userPost)
					.retrieve()
					.body(UserPostResponse.class);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public void putUserPost(UserPost userPost)
	{
		String location = base + "/UserPosts/" + userPost.getUID();
		PutDeleteResponse response = null;
		try
		{
			response = INSTANCE.client.put()
					.uri(location)
					.body(userPost)
					.retrieve()
					.body(PutDeleteResponse.class);
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
			System.out.println(client.get()
					.uri(location)
					.retrieve()
					.body(UserPostResponse.class));
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response.data();
	}
	
	public PutDeleteResponse deleteUserPost(String UID)
	{
		String location = base + "/UserPosts/" + UID;
		PutDeleteResponse response = null;
		try
		{
			response = client.delete()
					.uri(location)
					.retrieve()
					.body(PutDeleteResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response;
	}
	
	
	
	//JOB POST METHODS
	public record JobPostResponse(String request,
			boolean successful,
			String message,
			JobPost data) {};	
	
	public void postJobPost(JobPost jobPost)
	{
		String location = base + "/JobPosts/" + jobPost.getUID();
		JobPostResponse response = null;
		try
		{
			response = INSTANCE.client.post()
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
		PutDeleteResponse response = null;
		try
		{
			response = INSTANCE.client.put()
					.uri(location)
					.body(jobPost)
					.retrieve()
					.body(PutDeleteResponse.class);
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
			//System.out.println(response.toString());
			throw e;
		}
		return response.data();
	}
	
	public PutDeleteResponse deleteJobPost(String UID)
	{
		String location = base + "/JobPosts/" + UID;
		PutDeleteResponse response = null;
		try
		{
			response = client.delete()
					.uri(location)
					.retrieve()
					.body(PutDeleteResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response;
	}
	
	public record UserResponse(String request,
			boolean successful,
			String message,
			User  data) {};	
	
	public void postUser(User user)
	{
		String location = base + "/Users/" + user.getUID();
		UserResponse response = null;
		try
		{
			response = INSTANCE.client.post()
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
	
	public void putUser(Entity user)
	{
		String location = base + "/Users/" + user.getUID();
		PutDeleteResponse response = null;
		try
		{
			
			response = INSTANCE.client.put()
					.uri(location)
					.body(user)
					.retrieve()
					.body(PutDeleteResponse.class);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public User getUser(String UID)
	{
		String location = base + "/Users/" + UID;
		UserResponse response;
		try
		{
			response = client.get()
					.uri(location)
					.retrieve()
					.body(UserResponse.class);
			return response.data();
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	public record UserDesc(String name) {};
	
	public record AllUsersResponse(String request,
			boolean successful,
			String message,
			ArrayList<UserDesc>  data) {};	
	
	private AllUsersResponse getAllUserUIDs()
	{
		String location = base + "/Users";
		AllUsersResponse response = null;
		try
		{
			response = client.get()
					.uri(location)
					.retrieve()
					.body(AllUsersResponse.class);			
		}
		catch (Exception e)
		{
			throw e;
		}
		
		return response;
	}
			
	/*
	 * Public-facing API to get all user objects currently stored in the server.	
	 */
	public ArrayList<User> getAllUsers()
	{
		AllUsersResponse response = getAllUserUIDs();
		ArrayList<User> userList = new ArrayList<User>();
		
		for (UserDesc val: response.data())
		{
			userList.add(getUser(val.name()));
		}
		return userList;
	}
	
	public PutDeleteResponse deleteUser(String UID)
	{
		String location = base + "/Users/" + UID;
		PutDeleteResponse response = null;
		try
		{
			
			response = client.delete()
					.uri(location)
					.retrieve()
					.body(PutDeleteResponse.class);
					

		} 
		catch (Exception e) 
		{
			throw e;
		}
		return response;
	}
	

	public record Desc(String displayName, String description, String location) {};

	public record DescResponse(String request,
			boolean successful,
			String message,
			Desc  data) {};
	
	public void configureServer()
	{
		DescResponse response = null;
		try
		{
			response = 
				client.post()
				.uri("http://localhost:9000/v1/StephenRout")
				.body(new Desc("StephenRout", "Stephen Rout's project: Nexus", ""))
				.retrieve()
				.body(DescResponse.class);
		}
		catch (Exception e) 
		{
			throw e;
		}
		
		for (String className : classList)
		{
			try
			{
				response = 
					client.post()
					.uri(base + "/" + className)
					.body(new Desc(className, className + " objects", ""))
					.retrieve()
					.body(DescResponse.class);
			} 
			catch (Exception e)
			{
				throw e;
			}
		}
	}
	
	public void clearServer()
	{
		String response = null;
		try
		{
			response = 
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
}