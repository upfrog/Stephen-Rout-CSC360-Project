import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostTest
{
	UserPost testPost;
	String testPostContent = "This is my first comment! I can't wait to get "
			+ "to know my Nexus-buddies!";
	
	User testUser;
	Post testComment;
	Comment testComment2;
	boolean testPostIsPublic = true;

	@BeforeEach
	void setUp() throws Exception
	{
		testUser = new User("Individual");
		testPost = new UserPost(testPostContent, testPostIsPublic, testUser);
		//testComment = new UserPost("I agree!", true, testUser);
		//testComment2 = new Comment()
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
		assertEquals(testPost.getLikes(), 0);
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
	
	@Test
	
	void testGetComments()
	{
		assertEquals(testPost.getComments().size(), 0);
		testPost.addComment(testUser, "I agree!");

		//Comment comment = Comment(testPost.getComments().get(0));
		assertEquals(testPost.getComments().get(0).getContent(), "I agree!");
	}
}

/*
 * 
 * TODO:
 * -Figure out how to get the content of a comment (and a post?) given that they are both
 * structures, which don't all have content.
 * -Keep testing!
 * 
 */
