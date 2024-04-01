/**
 * 
 */

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */
class LinkContainerTest
{
	LinkContainer testLC;
	User testUser;

	@BeforeEach
	void setUp()
	{
		testLC = new LinkContainer();
		testUser = new User("Individual");
	}
	
	@Test
	void testAddLinkListAndGetList()
	{
		//assertThrows(testLC.getList("Followers"));
		Exception exception = assertThrows(RuntimeException.class, () -> testLC.getList("Followers"));
		assertEquals("List type not found.", exception.getMessage());
		testLC.addLinkList("Followers");
		assertFalse(testLC.getList("Followers").equals(null));
	}
	
	@Test
	void testAddLinkAndContains()
	{
		assertFalse(testLC.contains("Followers", testUser));
		testLC.addLinkList("Followers");
		testLC.addLink("Followers", testUser);
		assertTrue(testLC.contains("Followers", testUser));
	}
	
	@Test
	void testGetListLength()
	{
		Exception exception = assertThrows(RuntimeException.class, () -> testLC.getListLength("Followers"));
		assertEquals("List type not found.", exception.getMessage());
		testLC.addLinkList("Followers");
		assertEquals(testLC.getListLength("Followers"), 0);
		testLC.addLink("Followers", testUser);
		assertEquals(testLC.getListLength("Followers"), 1);
	}
	
	@Test
	void testRemoveLink()
	{
		testLC.addLinkList("Followers");
		testLC.addLink("Followers", testUser);
		testLC.removeLink("Followers", testUser);
		assertEquals(testLC.contains("Followers", testUser), false);
	}
}
