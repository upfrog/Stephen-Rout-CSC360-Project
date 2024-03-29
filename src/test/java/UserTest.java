import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest
{
	User testUser;
	@BeforeEach
	void setUp() throws Exception
	{
		testUser = new User();
	}

	@Test
	void testGetIsPublic()
	{
		assertEquals(testUser.getIsPublic(), true);
	}
	
	@Test
	void testToggleIsPublic()
	{
		assertEquals(testUser.getIsPublic(), true);
		testUser.toggleIsPublic();
		assertEquals(testUser.getIsPublic(), false);
		testUser.toggleIsPublic();
		assertEquals(testUser.getIsPublic(), true);
	}

	@Test
	void testFollowerChange()
	{
		fail("Not yet implemented");
	}

	@Test
	void testEditDescription()
	{
		fail("Not yet implemented");
	}
}
