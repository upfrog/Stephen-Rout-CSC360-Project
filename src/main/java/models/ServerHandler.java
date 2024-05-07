package models;

import java.util.ArrayList;

import org.springframework.web.client.RestClient;


/*
 * This class feels very inefficient. I would like to condense the various post and get methods, as 
 * they have a great deal of re-used code.
 * 
 * As a general rule:
 * 		post*() methods: 	take an object as input, post it to the server, return void
 * 		get*() methods:		take a string (a UID) as input, fetch it from the server, return the object
 * 		getAll*() methods: 	take no input, return all objects of a given type from the server.
 * 		put*() methods: 	take an object as input, put it to the server, return void
 * 		delete*() methods:	take a UID as input, delete the object of the matching type
 * 							with the matching UID from the server, return void
 *
 * Records should never be passed out of this class; all server data must be fully unpacked.
 */

public enum ServerHandler
{
	INSTANCE;

	private RestClient client = RestClient.create();
	private static final String base = "http://localhost:9000/v1/StephenRout";
	private static final String[] classList = {"Users","UserPosts", "Comments", "JobPosts"};

	
	public RestClient getClient()
	{
		return this.client;
	}
	
	public record PutDeleteResponse(String request,
			boolean successful,
			String message,
			String data) {};


			
			
	/************************************************************************************************/
	/*                                   COMMENT METHODS                                            */
	/************************************************************************************************/
			
	public record CommentResponse(String request,
		boolean successful,
		String message,
		Comment  data) {};	
			
	public void postComment(Comment comment)
	{
		String location = base + "/Comments/" + comment.getUID();
		try
		{
			client.post()
					.uri(location)
					.body(comment)
					.retrieve()
					.body(CommentResponse.class);
		}
		catch (Exception e)
		{	
			throw e;
		}
	}
	
	public void putComment(Comment comment)
	{
		String location = base + "/Comments/" + comment.getUID();		
		try
		{
			client.put()
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
		try
		{
			return client.get()
					.uri(location)
					.retrieve()
					.body(CommentResponse.class)
					.data();
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	
	public void deleteComment(String UID)
	{
		String location = base + "/Comments/" + UID;
		try
		{
			client.delete()
					.uri(location)
					.retrieve()
					.body(PutDeleteResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	public ArrayList<Comment> getAllComments()
	{
		GetAllResponse response = getAllUIDsIn("Comments");
		ArrayList<Comment> commentList = new ArrayList<Comment>();
		
		for (GenericResponse val: response.data())
		{
			commentList.add(getComment(val.name()));
		}
		return commentList;
	}
	
	/************************************************************************************************/
	/*                                   USERPOST METHODS                                           */
	/************************************************************************************************/
	
	public record UserPostResponse(String request,
			boolean successful,
			String message,
			UserPost  data) {};	
	
	public void postUserPost(UserPost userPost)
	{
		String location = base + "/UserPosts/" + userPost.getUID();
		try
		{
			 client.post()
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
		try
		{
			client.put()
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
		try
		{
			return client.get()
					.uri(location)
					.retrieve()
					.body(UserPostResponse.class)
					.data();
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	public ArrayList<UserPost> getAllUserPosts()
	{
		GetAllResponse response = getAllUIDsIn("UserPosts");
		ArrayList<UserPost> userPostList = new ArrayList<UserPost>();
		
		for (GenericResponse val: response.data())
		{
			userPostList.add(getUserPost(val.name()));
		}
		return userPostList;
	}
	
	public void deleteUserPost(String UID)
	{
		String location = base + "/UserPosts/" + UID;
		try
		{
			client.delete()
					.uri(location)
					.retrieve()
					.body(PutDeleteResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	
	/************************************************************************************************/
	/*                                   JOBPOST METHODS                                            */
	/************************************************************************************************/	
	
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
			client.post()
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
			client.put()
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
		try
		{
			return client.get()
					.uri(location)
					.retrieve()
					.body(JobPostResponse.class).data();
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	public ArrayList<JobPost> getAllJobPosts()
	{
		GetAllResponse response = getAllUIDsIn("JobPosts");
		ArrayList<JobPost> jobPostList = new ArrayList<JobPost>();
		
		for (GenericResponse val: response.data())
		{
			jobPostList.add(getJobPost(val.name()));
		}
		return jobPostList;
	}
	
	public void deleteJobPost(String UID)
	{
		String location = base + "/JobPosts/" + UID;
		try
		{
			client.delete()
					.uri(location)
					.retrieve()
					.body(PutDeleteResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	
	/************************************************************************************************/
	/*                                   USER METHODS                                               */
	/************************************************************************************************/
	
	public record UserResponse(String request,
			boolean successful,
			String message,
			User  data) {};	
	
	public void postUser(User user)
	{
		String location = base + "/Users/" + user.getUID();
		try
		{
			client.post()
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
		try
		{
			client.put()
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
		try
		{
			return client.get()
					.uri(location)
					.retrieve()
					.body(UserResponse.class)
					.data();
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/*
	 * Public-facing API to get all user objects currently stored in the server.
	 */
	public ArrayList<User> getAllUsers()
	{
		GetAllResponse response = getAllUIDsIn("Users");
		ArrayList<User> userList = new ArrayList<User>();
		
		for (GenericResponse val: response.data())
		{
			userList.add(getUser(val.name()));
		}
		return userList;
	}
	
	public void deleteUser(String UID)
	{
		String location = base + "/Users/" + UID;
		try
		{
			
			client.delete()
					.uri(location)
					.retrieve()
					.body(PutDeleteResponse.class);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}
	
	
	/************************************************************************************************/
	/*                                   HELPER METHODS                                             */
	/************************************************************************************************/
	
	
	public record GenericResponse(String name) {};
	
	public record GetAllResponse(String request,
			boolean successful,
			String message,
			ArrayList<GenericResponse>  data) {};	
	
	/*
	 * Helper method to fetch a list of all UIDs for all instances of a given object type
	 */
	private GetAllResponse getAllUIDsIn(String type)
	{
		String location = base + "/" + type;
		try
		{
			return client.get()
					.uri(location)
					.retrieve()
					.body(GetAllResponse.class);			
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	
	/************************************************************************************************/
	/*                                   SETUP/TEARDOWN METHODS                                     */
	/************************************************************************************************/
	
	public record Desc(String displayName, String description, String location) {};

	public record DescResponse(String request,
			boolean successful,
			String message,
			Desc  data) {};

	/*
	 * Run once at startup to ensure that the server is ready to receive information. Starts
	 * by creating a "team" for my data to live in, then creates a sub-directory for each
	 * of my top-level objects.
	 * 
	 * By nature, this should be server-side code, but because I'm running the server
	 * locally, it just lives here.
	 */
	public void configureServer()
	{
		try
		{
			client.post()
				.uri(base)
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
		try
		{
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