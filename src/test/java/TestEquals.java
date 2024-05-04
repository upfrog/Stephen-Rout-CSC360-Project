import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestEquals
{
	



	
	@BeforeEach
	void setUp() throws Exception
	{
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		/*
		String[] nameList = {"Inigo", "Montoya", "Joe"};
		Boolean[] useList = {true, true, false};
		name = new Name(nameList, useList);
		tooLong = new String(new char[350]).replace('\0', ' ');
		
		*/
	}
	
	/*
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
	*/
	
	/*
	 * Properly testing an implentation of .equals() requires testing verifying that at least 5
	 * properties hold:
	 * 	1) 	Reflexivity: 	(x.equals(x) == true)
	 * 	2) 	Symmetry:		(x.equals(y) == true if and only if (IFF) y.equals(x) == true)
	 * 	3) 	Transitivity:	(if x.equals(y) == true and y.equals(z) == true, then x.equals(z) == true
	 *	4)	Consistency:	(multiple invocations of the same statement should return the same result)
	 *	5)	Non-null:		(x.equals(null) == false, provided that x != null)
	 *
	 * I imagine that truly rigorous testing would entail a great deal more.
	 */
	@SuppressWarnings("unlikely-arg-type") //I am intentionally checking equality between different classes
	@Test
	void testUserEquality()
	{
		User a = new User("Individual");
		User b = ServerHandler.INSTANCE.getUser(a.getUID());
		User c = ServerHandler.INSTANCE.getUser(b.getUID());
		User d = new User("Individual");
		UserPost e = a.createUserPost("I'm on Nexus!", true);
		
		//reflexivity
		assertEquals(a.equals(a), true);
		
		//Symmetry
		assertEquals(a.equals(b), true);
		assertEquals(b.equals(a), true);
		
		//Transitivity
		assertEquals(a.equals(b), true);
		assertEquals(b.equals(c), true);
		assertEquals(a.equals(c), true);
		
		//Consistency has been inherently tested for
		
		//Non-null
		assertEquals(a.equals(null), false);
		
		//Returning false within same object
		assertEquals(a.equals(d), false);
		
		//Returning false due to different objects
		assertEquals(a.equals(e), false);
		
		
	}
}
