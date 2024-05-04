import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LinkContainerTest
{
	
	User user1 = new User();
	
	@BeforeEach
	void setUp() throws Exception
	{
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		user1 = new User("Individual");
		System.out.println(user1.getLC().getLinkMap().keySet());
		System.out.println(user1.getUID());

		
		/*
		String[] nameList = {"Inigo", "Montoya", "Joe"};
		Boolean[] useList = {true, true, false};
		name = new Name(nameList, useList);
		tooLong = new String(new char[350]).replace('\0', ' ');
		
		*/
	}
	
	@Test
	void testUserPostEquality()
	{
		System.out.println("now in the test");
		//user1 = ServerHandler.INSTANCE.getUser(user1.getUID());
		System.out.println(user1.getLC().getLinkMap().keySet());
		UserPost post1 = user1.createUserPost("I am on Nexus, wow!", true);
		UserPost post1Copy = ServerHandler.INSTANCE.getUserPost(post1.getUID());
		
		assertEquals(post1.equals(post1Copy), true);
		
				
				
	}
	
}
