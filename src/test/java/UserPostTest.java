import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPostTest
{
	UserPost testPost;
	String testPostContent = "This is my first comment! I can't wait to get "
			+ "to know my Nexus-buddies!";
	
	User testUser;
	boolean testPostIsPublic = true;

	@BeforeEach
	void setUp() throws Exception
	{
		testUser = new User();
		testPost = new UserPost(testPostContent, testPostIsPublic, testUser);
	}
	
	@Test
	void testGetContent()
	{
		assertEquals(testPost.getContent(), testPostContent);
	}
	
	@Test
	void testGetLike()
	{
		assertEquals(testPost.getLikes(), 0);
	}
	
	@Test
	void testUpdateLikes()
	{
		testPost.updateLikes(true);
		assertEquals(testPost.getLikes(), 1);
		testPost.updateLikes(false);
		assertEquals(testPost.getLikes(), 0);
	}

	@Test
	void testGetCreator()
	{
		assertEquals(testPost.getCreator(), testUser);
	}

}
