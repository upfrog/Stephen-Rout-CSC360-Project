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
	
	String user1UID;
	String user2UID;
	User testUser1;
	User testUser2;
	Post user1Post1;
	Comment user1Comment1;

	@BeforeEach
	void setUp() throws Exception
	{
		
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		user1UID = new User("Individual").getUID();
		user2UID = new User("Individual").getUID();
		testUser1 = ServerHandler.INSTANCE.getUser(user1UID);
		User testUser2 = ServerHandler.INSTANCE.getUser(user2UID);
		
		testPost = testUser1.createUserPost(testPostContent, testPostIsPublic);
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
	void testUserPost()
	{
		UserPost user1Post1 = testUser1.createUserPost("I'm on Nexus!", false);
		//assertEquals(testUser1.getUserPostUIDs().contains(user1Post1.getUID()), true);
		assertEquals(testUser1.getLC().contains("UserPosts", user1Post1.getUID()), true);

		assertEquals(ServerHandler.INSTANCE.getUserPost(user1Post1.getUID()).equals(user1Post1), true);
		testUser1.removeUserPost(user1Post1);
		assertEquals(testUser1.getLC().contains("UserPosts", user1Post1.getUID()), false);
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.getUserPost(user1Post1.getUID()));

	}
	@Test
	void testGetContent()
	{
		assertEquals(testPost.getContent(), testPostContent);
	}

	@Test
	void testUserPostLikes()
	{
		assertEquals(testPost.getLikes(), 0);
		testPost.increaseLikes(true);
		assertEquals(testPost.getLikes(), 1);
		testPost = ServerHandler.INSTANCE.getUserPost(testPost.getUID());
		assertEquals(testPost.getLikes(), 1);
		testPost.increaseLikes(false);
		assertEquals(testPost.getLikes(), 0);
	}

	
	@Test
	void testGetCreator()
	{
		assertEquals(testPost.getCreatorUID(), user1UID);
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
	
	@Test
	void testComments()
	{
		//assertEquals(testPost.getCommentUIDs().size(), 0);
		assertEquals(testPost.getLC().getListLength("Comments"), 0);
		Comment testComment = testUser1.createComment("We should connect!", testPost);
		String testCommentUID = testComment.getUID();
		//assertEquals(testPost.getCommentUIDs().size(), 1);
		assertEquals(testPost.getLC().getListLength("Comments"), 1);

		
		assertEquals(ServerHandler.INSTANCE.getComment(testCommentUID).getContent(), 
				testComment.getContent());
		
		assertEquals(ServerHandler.INSTANCE.getComment(testCommentUID).getLikes(), 0);
		testUser1.likeUnlikePost(testComment);
		assertEquals(ServerHandler.INSTANCE.getComment(testCommentUID).getLikes(), 1);
		testUser1.likeUnlikePost(testComment);
		assertEquals(ServerHandler.INSTANCE.getComment(testCommentUID).getLikes(), 0);
		
		testUser1.removeComment(testComment);
		//assertEquals(testUser1.getCommentUIDs().size(), 0);
		assertEquals(testPost.getLC().getListLength("Comments"), 1);

		
		assertThrows( Exception.class, () -> ServerHandler.INSTANCE.getComment(testCommentUID));
		//System.out.println(testPost.getCommentUIDs());
		//testPost = ServerHandler.INSTANCE.getUserPost(testCommentUID);
		//assertEquals(testPost.getCommentUIDs().size(), 0);
	}
	
	@Test
	void testJobPostValidation()
	{
		Exception a = assertThrows(IllegalArgumentException.class,
				() -> testUser1.createJobPost("f", "jffdsaf"));
		assertEquals(a.getMessage(), "Post title is too short");
		
		Exception b = assertThrows(IllegalArgumentException.class, 
				() -> testUser1.createJobPost("Software Engineer", ""));
		assertEquals(b.getMessage(), "Post is too short");
		
		int tooLong = 10001;
		String longString = new String(new char[tooLong]).replace('\0', ' ');
		

		Exception c = assertThrows(IllegalArgumentException.class, 
				() -> testUser1.createJobPost("Software Engineer", longString));
		assertEquals(c.getMessage(), "Post is too long");
		
		Exception d = assertThrows(IllegalArgumentException.class, 
				() -> testUser1.createJobPost(longString, "We pay very well!"));
		assertEquals(d.getMessage(), "Post title is too long");
	}
	
	@Test
	void testJobPost()
	{
		JobPost jobPost = testUser1.createJobPost("Number peasant", testPostContent);
		Comment testComment= testUser1.createComment("Are you still hiring?", jobPost);
		//assertEquals(jobPost.getCommentUIDs().contains(testComment.getUID()), true);
		assertEquals(jobPost.getLC().contains("Comments", testComment.getUID()), true);
		
		testUser1.removeComment(testComment);
		//assertEquals(jobPost.getCommentUIDs().contains(testComment.getUID()), true);
		assertEquals(jobPost.getLC().contains("Comments", testComment.getUID()), true);

		
		jobPost.addApplicant(testUser1);
		jobPost = ServerHandler.INSTANCE.getJobPost(jobPost.getUID());
		//assertEquals(jobPost.getApplicantUIDs().contains(testUser1.getUID()), true);
		assertEquals(jobPost.getLC().contains("Applicants", testUser1.getUID()), true);

		jobPost.removeApplicant(testUser1);
		jobPost = ServerHandler.INSTANCE.getJobPost(jobPost.getUID());
		assertEquals(jobPost.getLC().contains("Applicants", testUser1.getUID()), false);
		
		testUser1.removeJobPost(jobPost);
		//assertEquals(testUser1.getJobPostUIDs().size(), 0);
		assertEquals(testUser1.getLC().getListLength("JobPosts"), 0);
		
		
	}
	
	@Test
	void testUpdateJobPostLikes()
	{
		JobPost jobPost = testUser1.createJobPost("Number peasant", testPostContent);

		assertEquals(jobPost.getLikes(), 0);
		jobPost.increaseLikes(true);
		assertEquals(jobPost.getLikes(), 1);
		jobPost = ServerHandler.INSTANCE.getJobPost(jobPost.getUID());
		assertEquals(jobPost.getLikes(), 1);
		jobPost.increaseLikes(false);
		assertEquals(jobPost.getLikes(), 0);
	}
	

	
	
}