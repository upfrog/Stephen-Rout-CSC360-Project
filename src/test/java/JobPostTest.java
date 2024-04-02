import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JobPostTest
{
	
	User user1;
	JobPost post1;
	JobPost post2;
	
	@BeforeEach
	void setUp() throws Exception
	{
		User user1 = new User("Bob");
	}

	@Test
	void testConstuctorValidation()
	{
		//Too short
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new JobPost("", "We want you!", user1));
		assertEquals(e.getMessage(), "Post title is too short");
		e = assertThrows(IllegalArgumentException.class, 
				() -> new JobPost("Software Developer", "", user1));
		assertEquals(e.getMessage(), "Post is too short");
	
		//Too long
		int tooLong = 10001;
		String longString = new String(new char[tooLong]).replace('\0', ' ');
		
		e = assertThrows(IllegalArgumentException.class, 
				() -> new JobPost(longString, "We want you!", user1));
		assertEquals(e.getMessage(), "Post title is too long");
		e = assertThrows(IllegalArgumentException.class, 
				() -> new JobPost("Software Developer", longString, user1));
		assertEquals(e.getMessage(), "Post is too long");
		
		//post2 = new JobPost("Software Developer", "We want you!", user1);
		assertDoesNotThrow(() -> new JobPost("Software Developer", "We want you!", user1));
	}
	
	@Test
	void testAddApplicant()
	{
		JobPost post1 = new JobPost("Software Developer Wanted!", "We pay good!", user1);
		assertEquals(post1.getApplicants().contains(user1), false);
		post1.addApplicant(user1);
		assertEquals(post1.getApplicants().contains(user1), true);
		post1.removeApplicant(user1);
		assertEquals(post1.getApplicants().contains(user1), false);
	}

}
