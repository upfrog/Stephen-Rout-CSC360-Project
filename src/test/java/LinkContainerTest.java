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
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception
	{
		testLC = new LinkContainer();
		testUser = new User();
	}
	
	@Test
	void testAddLinkListAndGetList()
	{
		//assertThrows(testLC.getList("Followers"));
		assertTrue(testLC.getList("Followers") == null);
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
		assertEquals(testLC.getListLength("Followers"), -1);
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
