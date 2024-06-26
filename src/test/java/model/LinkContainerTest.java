package model;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import models.*;

public class LinkContainerTest
{
	
	User user1 = new User();
	LinkContainer lc = new LinkContainer();
	
	@BeforeEach
	void setUp() throws Exception
	{
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		lc = new LinkContainer();
	}
	
	@Test
	public void testMethods()
	{
		String key = "SecretToHappiness";
		String value = "Health";
		assertThrows(RuntimeException.class, () -> lc.getList(key));
		assertThrows(RuntimeException.class, () -> lc.getListLength(key));
		lc.addLink(key, value);
		
		lc.addLinkList(key);
		assertEquals(lc.getList(key).equals(new ArrayList<String>()), true);
		assertEquals(lc.getListLength(key), 0);
		assertEquals(lc.contains(key, value), false);
		lc.addLinkList(key);
		
		lc.addLink(key, value);
		assertEquals(lc.getListLength(key), 1);
		assertEquals(lc.getList(key).get(0), value);
		assertEquals(lc.contains(key, value), true);
		
		lc.addLink(key, value);
		assertEquals(lc.getListLength(key), 1);

		lc.removeLink(key, value);
		assertEquals(lc.getListLength(key), 0);
		assertEquals(lc.getList(key).equals(new ArrayList<String>()), true);
		assertEquals(lc.contains(key, value), false);
		lc.removeLink(key, value);
	}
}
