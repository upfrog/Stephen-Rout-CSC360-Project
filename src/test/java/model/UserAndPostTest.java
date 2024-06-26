package model;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import models.*;

class UserAndPostTest
{
	String user1UID;
	String user2UID;
	User testUser1;
	User testUser2;
	Post user1Post1;
	Comment user1Comment1;
	
	@BeforeEach
	void setUp() throws Exception
	{
		//Implicitly tests clearServer and configureServer
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		user1UID = new User("Individual").getUID();
		user2UID = new User("Individual").getUID();
		testUser1 = ServerHandler.INSTANCE.getUser(user1UID);
		testUser2 = ServerHandler.INSTANCE.getUser(user2UID);
	}
	
	
	
	@Test
	void testGetAllUsers()
	{
		ArrayList<User>  userList = ServerHandler.INSTANCE.getAllUsers();
		assertEquals(userList.size(), 2);
		assertEquals(userList.contains(testUser1), true);
		assertEquals(userList.contains(testUser2), true);
	}
	
	@Test
	void testGetPutDelete()
	{
	
		//User
		assertDoesNotThrow(() -> ServerHandler.INSTANCE.getUser(user1UID));
		User user1 = ServerHandler.INSTANCE.getUser(user1UID);
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.postUser(user1));
		user1.editDescription("A number peasant.");
		ServerHandler.INSTANCE.putUser(user1);
		User user2 = ServerHandler.INSTANCE.getUser(user1UID);
		assertEquals(user2.getDescription(), "A number peasant.");
		ServerHandler.INSTANCE.deleteUser(user1.getUID());
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.getUser(user1UID));
		
		
		//UserPost
		String user1Post1UID = user1.createUserPost("content1", true).getUID();
		assertDoesNotThrow(() -> ServerHandler.INSTANCE.getUserPost(user1Post1UID));
		UserPost user1Post1 = ServerHandler.INSTANCE.getUserPost(user1Post1UID);
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.postUserPost(user1Post1));
		user1Post1.setContent("content2");
		ServerHandler.INSTANCE.putUserPost(user1Post1);
		UserPost user1Post1Copy = ServerHandler.INSTANCE.getUserPost(user1Post1UID);
		assertEquals(user1Post1.getContent(), user1Post1Copy.getContent());
		ServerHandler.INSTANCE.deleteUserPost(user1Post1UID);
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.getUserPost(user1Post1UID));	
		
		
		
		
		
		//Comment setup
		user2 = new User("Individual");
		User user3 = new User("Individual");
		UserPost user3Post = user3.createUserPost("Lets be friends", true);
		String commentUID = user2.createComment("Great idea!", user3Post).getUID();
		
		
		
		//Comment
		assertDoesNotThrow(() -> ServerHandler.INSTANCE.getComment(commentUID));
		Comment testComment = ServerHandler.INSTANCE.getComment(commentUID);
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.postComment(testComment));
		testComment.setContent("content2");
		ServerHandler.INSTANCE.putComment(testComment);
		Comment testCommentCopy = ServerHandler.INSTANCE.getComment(commentUID);
		assertEquals(testCommentCopy.getContent(), testComment.getContent());
		ServerHandler.INSTANCE.deleteComment(commentUID);
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.getComment(commentUID));	
	}
	
	@Test
	void testGetAllUser()
	{
		System.out.println("===============================");
		User user1 = ServerHandler.INSTANCE.getUser(testUser1.getUID());
		//JobPost
		user1.setReccomender(new FollowerReccomender());
		String user1JobPost1UID = user1.createJobPost("Engineer", "content1").getUID();
		assertDoesNotThrow(() -> ServerHandler.INSTANCE.getJobPost(user1JobPost1UID));
		JobPost user1JobPost1 = ServerHandler.INSTANCE.getJobPost(user1JobPost1UID);
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.postJobPost(user1JobPost1));
		user1JobPost1.setContent("content2");
		ServerHandler.INSTANCE.putJobPost(user1JobPost1);
		JobPost user1JobPost1Copy = ServerHandler.INSTANCE.getJobPost(user1JobPost1UID);
		assertEquals(user1JobPost1.getContent(), user1JobPost1Copy.getContent());
		ServerHandler.INSTANCE.deleteJobPost(user1JobPost1UID);
		assertThrows(Exception.class, () -> ServerHandler.INSTANCE.getJobPost(user1JobPost1UID));	
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
		assertEquals(testUser1.getIsPublic(), true);
	}
	
	@Test
	void testToggleIsPublic()
	{
		assertEquals(testUser1.getIsPublic(), true);
		testUser1.toggleIsPublic();
		assertEquals(testUser1.getIsPublic(), false);
		ServerHandler.INSTANCE.putUser(testUser1);
		testUser1 = ServerHandler.INSTANCE.getUser(user1UID);
		assertEquals(testUser1.getIsPublic(), false);
		testUser1.toggleIsPublic();
		assertEquals(testUser1.getIsPublic(), true);
	}
	 
	
	
	/*
	 * Need to test that changes effect user A's following list, and
	 * user B's follower list
	 */
	
	
	@Test
	void testFollowerChange()
	{
		User user1 = ServerHandler.INSTANCE.getUser(user1UID);
		User user2 = ServerHandler.INSTANCE.getUser(user2UID);

		assertEquals(user1.getFollowingUIDs().contains(user2UID), false);
		assertEquals(user2.getFollowerUIDs().contains(user1UID), false);
		
		user1.followingToggle(user2);
		assertEquals(user1.getFollowingUIDs().contains(user2UID), true);
		assertEquals(user2.getFollowerUIDs().contains(user1UID), true);
		assertEquals(user1.getFollowerUIDs().contains(user2UID), false);
		assertEquals(user2.getFollowingUIDs().contains(user1UID), false);

		
		user1.followingToggle(user2);
		assertEquals(user1.getFollowingUIDs().contains(user2UID), false);
		assertEquals(user2.getFollowerUIDs().contains(user1UID), false);
	}
	
	@Test
	void testPostFeed()
	{
		User user1 = ServerHandler.INSTANCE.getUser(user1UID);
		User user2 = ServerHandler.INSTANCE.getUser(user2UID);
		user1.followingToggle(user2);
		assertEquals(user1.getFollowingUIDs().contains(user2UID), true);

		UserPost post1 = user2.createUserPost("Lalalala", true);

		user1 = user1.updatedUser();
		user2 = user2.updatedUser();
		assertEquals(user1.getLC().getList("PostFeed").contains(post1.getUID()), true);
		assertEquals(user2.getLC().getList("PostFeed").contains(post1.getUID()), false);

	}
	
	
	@Test
	void testDescription()
	{
		assertEquals(testUser1.getDescription(), "This user has not written a description yet");
		String testUser1Description = "I'm a software developer at Oracle!";
		testUser1.editDescription(testUser1Description);
		assertEquals(testUser1.getDescription(), testUser1Description);
		
		//Concisely creates a string long enough to trigger the excess length exception
		String testUser1DescriptionLong = new String(new char[4090]).replace('\0', ' ');
		Exception e = assertThrows(SecurityException.class, 
				() -> testUser1.editDescription(testUser1DescriptionLong));
		assertEquals(e.getMessage(), "Description is too long - description must be 4084 characters or less");
		
		
	}
	
	
	
	@Test
	void testEntityEditorValidation()
	{
		assertEquals(testUser1.hasAsEditor(testUser1), true);
		
		testUser1.editorToggle(user2UID);
		assertEquals(testUser1.hasAsEditor(testUser2), true);
		testUser1.editorToggle(testUser2);
		assertEquals(testUser1.hasAsEditor(testUser2), false);
		
		assertThrows(IllegalArgumentException.class, 
				() -> testUser1.editorToggle(testUser1.getUID()));
	}
	
	
	
	

	
	
	@Test
	void testCreateJobPost()
	{
		user1Post1 = testUser1.createJobPost("Software Developer", "We pay money!");
		//assertEquals(testUser1.getJobPostUIDs().contains(user1Post1.getUID()), true);
		assertEquals(testUser1.getLC().contains("JobPosts", user1Post1.getUID()), true);
		
		assertEquals(ServerHandler.INSTANCE.getJobPost(user1Post1.getUID()).getUID(), user1Post1.getUID());
	}

	@Test
	void testWorkExperience()
	{
		WorkExperience job = testUser1.addWorkExperience("1/15/2021", "3/03/2024", "Amazon", "SDE3", "Wrote some code and stuff");
		assertEquals(testUser1.getWorkHistory().contains(job), true);
		//ServerHandler.INSTANCE.putUser(testUser1);
		//testUser1 = ServerHandler.INSTANCE.getUser(testUser1.getUID());
		testUser1.removeWorkExperience(job);
		assertEquals(testUser1.getWorkHistory().contains(job), false);
	}
	
	@Test
	void testBlock()
	{
		assertEquals(testUser1.hasBlocked(testUser2), false);
		testUser1.blockedToggle(testUser2);
		assertEquals(testUser1.hasBlocked(testUser2), true);
		testUser1.blockedToggle(testUser2);
		assertEquals(testUser1.hasBlocked(testUser2), false);
	}
	
	
	
	@Test
	void testJobReccomender()
	{
		
		
		testUser2.followingToggle(testUser1);
		User testUser3 = new User("Individual");
		User testUser4 = new User("Individual");
		
		testUser3.followingToggle(testUser1);
		
		
		JobPost jp1 = testUser1.createJobPost("Software Developer", "Write Code");
		
		//Check that the two followers have the post, and the non-follower does not
		testUser3 = testUser3.updatedUser();
		assertEquals(testUser3.getLC().getList("ReccomendedJobs").contains(jp1.getUID()), true);
		testUser2 = testUser2.updatedUser();
		assertEquals(testUser2.getLC().getList("ReccomendedJobs").contains(jp1.getUID()), true);
		testUser4 = testUser4.updatedUser();
		assertEquals(testUser4.getLC().getList("ReccomendedJobs").contains(jp1.getUID()), false);


		//Check pushing job posts to all users
		testUser1.setReccomender(new UniversalReccomender());
		JobPost jp2 = testUser1.createJobPost("Mechanical Engineer", "Rotate Shapes");
		testUser3 = testUser3.updatedUser();
		assertEquals(testUser3.getLC().getList("ReccomendedJobs").contains(jp2.getUID()), true);
		testUser2 = testUser2.updatedUser();
		assertEquals(testUser2.getLC().getList("ReccomendedJobs").contains(jp2.getUID()), true);
		testUser4 = testUser4.updatedUser();
		assertEquals(testUser4.getLC().getList("ReccomendedJobs").contains(jp2.getUID()), true);
		
		
		
		
		//Check pushing posts to users with sufficient skill
		testUser1.setReccomender(new SkillReccomender());
		
		
		testUser1.setReccomender(new SkillReccomender());

		testUser2.getLC().addLink("Skills", "Coding");
		testUser2.pushUser();
		testUser3.getLC().addLink("Skills", "Coding");
		testUser3.getLC().addLink("Skills", "Go");
		testUser3.pushUser();

		ArrayList<String> skills = new ArrayList<String>();
		skills.add("Go");
		skills.add("Coding");
		skills.add("Design");
		
		JobPost jp3 = testUser1.createJobPost("Expert Go Developr", "We'll pay too much!", skills, 0.6);

		testUser3 = testUser3.updatedUser();
		assertEquals(testUser3.getLC().getList("ReccomendedJobs").contains(jp3.getUID()), true);
		testUser2 = testUser2.updatedUser();
		assertEquals(testUser2.getLC().getList("ReccomendedJobs").contains(jp3.getUID()), false);
	}
	
	@Test
	void testReccomendedJobs()
	{
		testUser2.followingToggle(testUser1);
		
		//Test the method for getting UIDs from the recommendation queue
		JobPost testjob1 = testUser1.createJobPost("Software Developer", "Long distance is fine");
		testUser2 = testUser2.updatedUser();
		assertEquals(testUser2.getReccomendedJobUID(), testjob1);
		assertEquals(testUser2.getReccomendedJobUID(), null);
		
		//Test rejecting a job
		JobPost testjob2 = testUser1.createJobPost("Software Developer", "Long distance is fine");
		testUser2 = testUser2.updatedUser();
		assertEquals(testjob2.getLC().getList("Applicants").contains(testUser2.getUID()), false);
		testUser2.processJobRec(false);
		testjob2 = ServerHandler.INSTANCE.getJobPost(testjob2.getUID());
		assertEquals(testjob2.getLC().getList("Applicants").contains(testUser2.getUID()), false);

		//Test accepting a job
		JobPost testjob3 = testUser1.createJobPost("Software Developer", "Long distance is fine");
		testUser2 = testUser2.updatedUser();
		testUser2.processJobRec(true);
		testjob3 = ServerHandler.INSTANCE.getJobPost(testjob3.getUID());
		assertEquals(testjob3.getLC().getList("Applicants").contains(testUser2.getUID()), true);
		//Test that program fails gracefully if there are no jobs in the queue
		assertDoesNotThrow(() -> testUser2.processJobRec(true));
	}
	
	
	
}
