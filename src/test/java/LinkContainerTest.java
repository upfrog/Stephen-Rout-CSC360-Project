import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LinkContainerTest
{
	
	User user1 = new User();
	
	@BeforeEach
	void setUp() throws Exception
	{
		User user1 = new User("Individual");
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		
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
		UserPost post1 = user1.createUserPost("I am on Nexus, wow!", true);
		UserPost post1Copy = ServerHandler.INSTANCE.getUserPost(post1.getUID());
		
		assertEquals(post1.equals(post1Copy), true);
				
				
	}
	
}
