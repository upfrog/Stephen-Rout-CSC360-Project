import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAndPostTest
{
	User user1;
	User user2;
	Post user1Post1;
	Comment user1Comment1;
	
	@BeforeEach
	void setUp() throws Exception
	{
		user1 = new User("Individual");
		user2 = new User("Individual");
	}
	
	@Test
	void testConstructorValidation()
	{
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new User("foo"));
		assertEquals(e.getMessage(), "Invalid user type");
		
		assertDoesNotThrow(() -> new User("Individual"));
		assertDoesNotThrow(() -> new User("Organization"));
	}

	@Test
	void testGetIsPublic()
	{
		assertEquals(user1.getIsPublic(), true);
	}
	
	@Test
	void testToggleIsPublic()
	{
		assertEquals(user1.getIsPublic(), true);
		user1.toggleIsPublic();
		assertEquals(user1.getIsPublic(), false);
		user1.toggleIsPublic();
		assertEquals(user1.getIsPublic(), true);
	}

	@Test
	void testFollowerChange()
	{
		assertEquals(user1.getLinkContainer().contains("Following", user2), false);
		user1.followerChange(user2);
		assertEquals(user1.getLinkContainer().contains("Following", user2), true);
		user1.followerChange(user2);
		assertEquals(user1.getLinkContainer().contains("Following", user2), false);
	}

	@Test
	void testDescription()
	{
		assertEquals(user1.getDescription(), "This user has not written a description yet");
		String testUser1Description = "I'm a software developer at Oracle!";
		user1.editDescription(testUser1Description);
		assertEquals(user1.getDescription(), testUser1Description);
		
		//Concisely creates a string long enough to trigger the excess length exception
		String testUser1DescriptionLong = new String(new char[4090]).replace('\0', ' ');
		Exception e = assertThrows(SecurityException.class, 
				() -> user1.editDescription(testUser1DescriptionLong));
		assertEquals(e.getMessage(), "Description is too long - description must be 4084 characters or less");
	}
	
	@Test
	void testEntityEditorValidation()
	{
		assertEquals(user1.hasAsEditor(user1), true);
		user1.toggleEditor(user2);
		assertEquals(user1.hasAsEditor(user2), true);
		user1.toggleEditor(user2);
		assertEquals(user1.hasAsEditor(user2), false);
		
		assertThrows(IllegalArgumentException.class, 
				() -> user1.toggleEditor(user1));
		
		
	}
	@Test
	void testUserCreatePost()
	{
		user1Post1 = user1.createUserPost("I'm on Nexus!", false);
		assertEquals(user1.getLinkContainer().getList("UserPosts").get(0), user1Post1);
	}
	
	@Test
	void testCreateJobPost()
	{
		user1Post1 = user1.createJobPost("Software Developer", "We pay money!", user1);
		assertEquals(user1.getLinkContainer().getList("JobPosts").contains(user1Post1), true);
	}
	
	@Test
	void testComment()
	{
		user1Post1 = user1.createUserPost("I'm on Nexus!", false);
		user1Comment1 = user1.createComment("Me too! Wanna network?", user1Post1);
		
		//Test the post and user containers.
		assertEquals(user1Post1.getComments().contains(user1Comment1), true);  
		assertEquals(user1.getLinkContainer().getList("Comments").contains(user1Comment1), true);
		
		user1.removeComment(user1Comment1, user1Post1);
		assertEquals(user1.getLinkContainer().getList("Comments").contains(user1Comment1), false);
		assertEquals(user1Post1.getComments().contains(user1Comment1), false); 
	}
	
	@Test
	void testLikeMethods()
	{
		user1Post1 = user1.createUserPost("I'm on Nexus!", false);
		user1.likeUnlikePost(user1Post1);
		assertEquals(user1Post1.getLikes(), 1);
		user1.likeUnlikePost(user1Post1);
		assertEquals(user1Post1.getLikes(), 0);
		user1.likeUnlikePost(user1Post1);
		assertEquals(user1Post1.getLikes(), 1);
	}
	
	@Test
	void testWorkExperience()
	{
		WorkExperience job = user1.addWorkExperience("1/15/2021", "3/03/2024", "Amazon", "SDE3", "Wrote some code and stuff");
		assertEquals(user1.getWorkHistory().contains(job), true);
		user1.removeWorkExperience(job);
		assertEquals(user1.getWorkHistory().contains(job), false);
	}
	
	@Test
	void testBlock()
	{
		assertEquals(user1.hasBlocked(user2), false);
		user1.blockedToggle(user2);
		assertEquals(user1.hasBlocked(user2), true);
		user1.blockedToggle(user2);
		assertEquals(user1.hasBlocked(user2), false);
	}
}
