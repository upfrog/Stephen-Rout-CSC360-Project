import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ServerHandlerTest
{

	
	@BeforeEach
	void setUp() throws Exception
	{
		//Implicitly tests clearServer and configureServer
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();

	}
	
	
	@Test
	void testGetAllUsers()
	{		
		//Test that the method returns zero objects when none have been posted
		ArrayList<User>  objectList = ServerHandler.INSTANCE.getAllUsers();
		assertEquals(objectList.size(), 0);

		String o1UID = new User("Individual").getUID();
		String o2UID = new User("Individual").getUID();
		User o1 = ServerHandler.INSTANCE.getUser(o1UID); //Ensures that the objects are on the server
		User o2 = ServerHandler.INSTANCE.getUser(o2UID);
		
		//Test that the method returns two objects when two have been posted, and that the objects are correct
		objectList = ServerHandler.INSTANCE.getAllUsers();
		assertEquals(objectList.size(), 2);
		assertEquals(objectList.contains(o1), true);
		assertEquals(objectList.contains(o2), true);
	}
	
	@Test
	void testGetAllUserPosts()
	{		
		//Test that the method returns zero objects when none have been posted
		ArrayList<UserPost>  objectList = ServerHandler.INSTANCE.getAllUserPosts();
		assertEquals(objectList.size(), 0);
		User u1 = new User("Individual");

		String o1UID = u1.createUserPost("I'm on Nexus", false).getUID();
		String o2UID = u1.createUserPost("Nexus is great!", true).getUID();
		UserPost o1 = ServerHandler.INSTANCE.getUserPost(o1UID);//Ensures that the objects are on the server
		UserPost o2 = ServerHandler.INSTANCE.getUserPost(o2UID);
		
		//Test that the method returns two objects when two have been posted, and that the objects are correct
		objectList = ServerHandler.INSTANCE.getAllUserPosts();
		assertEquals(objectList.size(), 2);
		assertEquals(objectList.contains(o1), true);
		assertEquals(objectList.contains(o2), true);
	}
	
	
	@Test
	void testGetAllJobPosts()
	{		
		//Test that the method returns zero objects when none have been posted
		ArrayList<JobPost>  objectList = ServerHandler.INSTANCE.getAllJobPosts();
		assertEquals(objectList.size(), 0);
		User u1 = new User("Individual");

		System.out.println("Made it here");
		String o1UID = u1.createJobPost("Software Developer", "We pay money!").getUID();
		String o2UID = u1.createJobPost("CEO", "Generous stock options!").getUID();
		JobPost o1 = ServerHandler.INSTANCE.getJobPost(o1UID);//Ensures that the objects are on the server
		JobPost o2 = ServerHandler.INSTANCE.getJobPost(o2UID);
		
		//Test that the method returns two objects when two have been posted, and that the objects are correct
		objectList = ServerHandler.INSTANCE.getAllJobPosts();
		assertEquals(objectList.size(), 2);
		assertEquals(objectList.contains(o1), true);
		assertEquals(objectList.contains(o2), true);
	}
	
	
	@Test
	void testGetAllComments()
	{		
		//Test that the method returns zero objects when none have been posted
		ArrayList<Comment>  objectList = ServerHandler.INSTANCE.getAllComments();
		assertEquals(objectList.size(), 0);
		User u1 = new User("Individual");
		UserPost p1 = u1.createUserPost("Hi!", true);

		String o1UID = u1.createComment("Hello!", p1).getUID();
		String o2UID = u1.createComment("It's you?", p1).getUID();
		Comment o1 = ServerHandler.INSTANCE.getComment(o1UID);//Ensures that the objects are on the server
		Comment o2 = ServerHandler.INSTANCE.getComment(o2UID);
		
		//Test that the method returns two objects when two have been posted, and that the objects are correct
		objectList = ServerHandler.INSTANCE.getAllComments();
		assertEquals(objectList.size(), 2);
		assertEquals(objectList.contains(o1), true);
		assertEquals(objectList.contains(o2), true);
	}
}