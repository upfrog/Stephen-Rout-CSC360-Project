import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostTest
{
	UserPost testPost;
	String testPostContent = "This is my first comment! I can't wait to get "
			+ "to know my Nexus-buddies!";
	
	User user1;
	Comment testComment;
	Comment testComment2;
	boolean testPostIsPublic = true;

	@BeforeEach
	void setUp() throws Exception
	{
		user1 = new User("Individual");
		testPost = new UserPost(testPostContent, testPostIsPublic, user1);
	}
	
	@Test
	void testConstructorValidation()
	{
		//Too short
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new UserPost("", true, user1));
		assertEquals(e.getMessage(), "Post is too short");
	
		//Too long
		int tooLong = 25001;
		String longString = new String(new char[tooLong]).replace('\0', ' ');
		
		e = assertThrows(IllegalArgumentException.class, 
				() -> new UserPost(longString, true, user1));
		assertEquals(e.getMessage(), "Post is too long");
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
		testPost.increaseLikes(true);
		assertEquals(testPost.getLikes(), 1);
		testPost.increaseLikes(false);
		assertEquals(testPost.getLikes(), 0);
	}

	@Test
	void testGetCreator()
	{
		assertEquals(testPost.getCreator(), user1);
	}
	
	@Test
	void testPublicity()
	{
		assertEquals(testPost.getIsPublic(), testPostIsPublic);
		testPost.toggleIsPublic();
		assertEquals(testPost.getIsPublic(), !testPostIsPublic);
		testPost.toggleIsPublic();
		assertEquals(testPost.getIsPublic(), testPostIsPublic);
	}
	
	@Test
	
	void testComments()
	{
		assertEquals(testPost.getComments().size(), 0);
		testComment = testPost.addComment(user1, "I agree!");

		assertEquals(testPost.getComments().get(0).getContent(), "I agree!");
		testPost.removeComment(testComment);
		assertEquals(testPost.getComments().size(), 0);
	}
	
	@Test
	void testCommentConstructorValidation()
	{
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new Comment(testPost, user1, ""));
		assertEquals(e.getMessage(), "Comment is too short");
	
		//Too long
		int tooLong = 15001;
		String longString = new String(new char[tooLong]).replace('\0', ' ');
		
		e = assertThrows(IllegalArgumentException.class, 
				() -> new Comment(testPost, user1, longString));
		assertEquals(e.getMessage(), "Comment is too long");
	}
}