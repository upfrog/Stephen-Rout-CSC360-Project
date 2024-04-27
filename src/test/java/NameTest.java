import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NameTest
{
	
	Name name;
	
	@BeforeEach
	void setUp() throws Exception
	{
		name = new Name("Joe");
		
		/*
		String[] nameList = {"Inigo", "Montoya", "Joe"};
		Boolean[] useList = {true, true, false};
		name = new Name(nameList, useList);
		tooLong = new String(new char[350]).replace('\0', ' ');
		
		*/
	}
	
	@Test
	void testName()
	{
		assertEquals(name.getName(), "Joe");
		
		Exception a = assertThrows(IllegalArgumentException.class,
				() -> new Name(""));
		assertEquals(a.getMessage(), "Name is too short");
		
		int tooLong = 101;
		String longString = new String(new char[tooLong]).replace('\0', ' ');
		
		Exception b = assertThrows(IllegalArgumentException.class, 
				() -> new Name(longString));
		assertEquals(b.getMessage(), "Name is too long");
	}
	
	
	
	/*
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
	*/

}
