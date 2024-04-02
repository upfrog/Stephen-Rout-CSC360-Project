import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NameTest
{
	Name name;
	String tooLong;
	
	@BeforeEach
	void setUp() throws Exception
	{
		name = new Name("Inigo", "Montoya");
		tooLong = new String(new char[350]).replace('\0', ' ');
	}

	@Test
	void testGetFirstName()
	{
		assertEquals(name.getFirstName(), "Inigo");
	}

	@Test
	void testGetLastName()
	{
		assertEquals(name.getLastName(), "Montoya");
	}
	
	@Test
	void testGetMiddleName()
	{
		assertEquals(name.getMiddleName(), null);
	}
	
	@Test
	void testGetPreferredName()
	{
		assertEquals(name.getPreferredName(), null);
	}
	
	@Test
	void testSetFirstName()
	{
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> name.setFirstName(tooLong));
		assertEquals(e.getMessage(), "Sorry, that name is too long for our system");
		e = assertThrows(IllegalArgumentException.class,
				() -> name.setFirstName(""));
		assertEquals(e.getMessage(), "Sorry, that name is too short for our system");
		
		name.setFirstName("Peter");
		assertEquals(name.getFirstName(), "Peter");
	}


	@Test
	void testSetLastName()
	{
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> name.setLastName(tooLong));
		assertEquals(e.getMessage(), "Sorry, that name is too long for our system");
		e = assertThrows(IllegalArgumentException.class,
				() -> name.setLastName(""));
		assertEquals(e.getMessage(), "Sorry, that name is too short for our system");
	
		name.setLastName("Pan");
		assertEquals(name.getLastName(), "Pan");
	}



	@Test
	void testSetMiddleName()
	{
		assertEquals(name.getMiddleName(), null);
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> name.setMiddleName(tooLong));
		assertEquals(e.getMessage(), "Sorry, that name is too long for our system");
	
		name.setMiddleName("Robert");
		assertEquals(name.getMiddleName(), "Robert");
	}


	@Test
	void testSetPreferredName()
	{
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> name.setPreferredName(tooLong));
		assertEquals(e.getMessage(), "Sorry, that name is too long for our system");
		
		name.setPreferredName("A Dashing Scoundrel");
		assertEquals(name.getPreferredName(), "A Dashing Scoundrel");
	}

}
