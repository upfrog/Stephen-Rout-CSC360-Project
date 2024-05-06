import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** 
 * Properly testing an implementation of .equals() requires  verifying that at least 5 properties hold:
 * 	1) 	Reflexivity: 	(x.equals(x) == true)
 * 	2) 	Symmetry:		(x.equals(y) == true if and only if (IFF) y.equals(x) == true)
 * 	3) 	Transitivity:	(if x.equals(y) == true and y.equals(z) == true, then x.equals(z) == true)
 *	4)	Consistency:	(multiple invocations of the same statement should return the same result)
 *	5)	Non-nullity:	(x.equals(null) == false, provided that x != null)
 *
 * Furthermore, when overriding .equals(), it is important to also override .hashCode(), which is
 * tested as well. The main things to test with hashCode() are:
 *  1) Consistency:		(two hashes of a given object should be equal, providing that nothing has changed.)
 *  2) Unnamed:			(If x.equals(y), then x.hashCode() must equal y.hashCode())
 *  
 *  Hash collisions are a fact of life, so there is no need to verify that a x.hashCode() != y.hashCode() 
 *  when x.equals(y) is false.
 *  
 *  It might have been more sensible to disperse these tests to the test files which test their respective
 *  classes, but this unified structure made it easier to test efficiently with testEqualityAndHashCode().
 *
 * I imagine that truly rigorous testing would entail a great deal more. In particular, I don't test
 * that .equals() correctly detects every type of difference. This _might_ be a productive addition for the
 * future, but off-hand I think it feels like trying too hard, at least given the of this program. I also make
 * no effort to test the efficacy of my hashing algorithm. A well-optimized hashing algorithm would be 
 * important to making this app scale, but it currently has bigger obstacles to scaling than that!
 */
public class TestEqualsAndHashCode
{	
	@BeforeEach
	void setUp() throws Exception
	{
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
	}

	@Test
	void testUserEquality()
	{
		Structure a = new User("Individual");
		Structure b = ServerHandler.INSTANCE.getUser(a.getUID());
		Structure c = ServerHandler.INSTANCE.getUser(b.getUID());
		Structure d = new User("Individual");
		Structure e = new UserPost();
		testEqualityAndHashCode(a,b,c,d,e);
		a.hashCode();
	}
	
	@Test
	void testUserPostEquality()
	{
		User user = new User("Individual");
		Structure a = user.createUserPost("I'm on Nexus", true);
		Structure b = ServerHandler.INSTANCE.getUserPost(a.getUID());
		Structure c = ServerHandler.INSTANCE.getUserPost(b.getUID());
		Structure d = new UserPost();
		testEqualityAndHashCode(a,b,c,d,user);
	}
	
	@Test
	void testJobPostEquality()
	{
		User user = new User("Individual");
		Structure a = user.createJobPost("Software Developer", "We pay money!");
		Structure b = ServerHandler.INSTANCE.getJobPost(a.getUID());
		Structure c = ServerHandler.INSTANCE.getJobPost(b.getUID());
		Structure d = new JobPost();
		testEqualityAndHashCode(a,b,c,d,user);
	}
	
	
	@Test
	void testCommentEquality()
	{
		User user = new User("Individual");
		UserPost post = user.createUserPost("I'm on Nexus!", true);
		Structure a = user.createComment("Me too!", post);
		Structure b = ServerHandler.INSTANCE.getComment(a.getUID());
		Structure c = ServerHandler.INSTANCE.getComment(b.getUID());
		Structure d = user.createComment("Nexus is great!", post);
		testEqualityAndHashCode(a,b,c,d,user);
	}
	
	@Test
	void testWorkExperienceEquality()
	{
		WorkExperience a = new WorkExperience("fsda", "fff", "4023e", "325r", "fawef");
		WorkExperience b = new WorkExperience("fsda", "fff", "4023e", "325r", "fawef");
		WorkExperience c = new WorkExperience("fsda", "fff", "4023e", "325r", "fawef");
		WorkExperience d = new WorkExperience("fsfasdda", "ffaff", "402afs3e", "32a5r", "fawsef");
		User e = new User("Individual");
		
		//WorkExperiences aren't structures, so I can't use my test helper
		assertEquals(a.equals(a), true); //Reflexivity
		
		assertEquals(a.equals(b), true); //Symmetry - true
		assertEquals(b.equals(a), true);
		
		assertEquals(b.equals(d), false); //Symmetry - false
		assertEquals(d.equals(a), false);
		
		assertEquals(a.equals(b), true); //Transitivity
		assertEquals(b.equals(c), true);
		assertEquals(a.equals(c), true); 
		
		assertEquals(a.equals(b), true); //Extra (probably unnecessary) test for consistency
		assertEquals(a.equals(null), false); //Non-nullity
		assertEquals(a.equals(d), false); //Returning false for comparisons within same object
		assertEquals(a.equals(d), false); //Repeat to test consistency with false results
		assertEquals(a.equals(e), false); //Returning false due to different objects
		
		//Test that when .equals() returns true, hashCode() produces identical results on the two objects.
		int aHash = a.hashCode();
		assertEquals(a.hashCode(), a.hashCode()); 
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(aHash, a.hashCode()); //Extra consistency
		
		//Test that object changes are reflected in .equals()
		a.setStartDate("432423523"); //THIS IS VERY BAD AND SHOULD NEVER HAPPEN - but it's easiest attribute to test
		assertEquals(a.equals(b), false);
		assertEquals(b.equals(a), false);
	}
	
	
	/** A helper method to encapsulate testing .equals()
	 * 
	 * @param a: Some object, inheriting from Structure, of type X
	 * @param b: A copy of a
	 * @param c: A copy of b
	 * @param d: An object of type X, different from a.
	 * @param e: An object of type Y, such that Y inherits from Structure, and Y != X
	 * @return:	 True if all tests pass, false if any tests return false or throw an exception.
	 */
	boolean testEqualityAndHashCode(Structure a, Structure b, Structure c, Structure d, Structure e)
	{
		
		assertEquals(a.equals(a), true); //Reflexivity
		
		assertEquals(a.equals(b), true); //Symmetry - true
		assertEquals(b.equals(a), true);
		
		assertEquals(b.equals(d), false); //Symmetry - false
		assertEquals(d.equals(a), false);
		
		assertEquals(a.equals(b), true); //Transitivity
		assertEquals(b.equals(c), true);
		assertEquals(a.equals(c), true); 
		
		assertEquals(a.equals(b), true); //Extra (probably unnecessary) test for consistency
		assertEquals(a.equals(null), false); //Non-nullity
		assertEquals(a.equals(d), false); //Returning false for comparisons within same object
		assertEquals(a.equals(d), false); //Repeat to test consistency with false results
		assertEquals(a.equals(e), false); //Returning false due to different objects
		
		//Test that when .equals() returns true, hashCode() produces identical results on the two objects.
		int aHash = a.hashCode();
		assertEquals(a.hashCode(), a.hashCode()); 
		assertEquals(a.hashCode(), b.hashCode());
		assertEquals(aHash, a.hashCode()); //Extra consistency
		
		//Test that object changes are reflected in .equals()
		a.setUID("432423523"); //THIS IS VERY BAD AND SHOULD NEVER HAPPEN - but it's easiest attribute to test
		assertEquals(a.equals(b), false);
		assertEquals(b.equals(a), false);
		
		return true;
	}
}
