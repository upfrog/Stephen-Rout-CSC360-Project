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
	void testCreatePost()
	{
		user1Post1 = user1.createPost("I'm on Nexus!", false);
		assertEquals(user1.getLinkContainer().getList("Posts").get(0), user1Post1);
	}
	
	@Test
	void testCreateComment()
	{
		user1Post1 = user1.createPost("I'm on Nexus!", false);
		user1Comment1 = user1.createComment("Me too! Wanna network?", user1Post1);
		assertEquals(user1Post1.getComments().get(0), user1Comment1);
	}
	
	@Test
	void testLikeMethods()
	{
		user1Post1 = user1.createPost("I'm on Nexus!", false);
		/*
		//Update the likes directly through the Post class
		assertEquals(user1Post1.getLikes(), 0);
		user1Post1.updateLikes(true);
		assertEquals(user1Post1.getLikes(), 1);
		user1Post1.updateLikes(false);
		assertEquals(user1Post1.getLikes(), 0);
		*/
		
		//Update the likes via the User class
		user1.likeUnlikePost(user1Post1);
		assertEquals(user1Post1.getLikes(), 1);
		user1.likeUnlikePost(user1Post1);
		assertEquals(user1Post1.getLikes(), 0);
		user1.likeUnlikePost(user1Post1);
		assertEquals(user1Post1.getLikes(), 1);
		
		
	}
	



	
}
