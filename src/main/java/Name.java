/**
 * Our naming system is deliberately light on validation, as naming validation can lead
 * to very unpleasant user experiences.
 * 
 * We have adopted the philosophy that chaos is preferable to a new user's first 
 * experience with the program being "You're name is _wrong_".
 * 
 * (for more information, See:
 * https://www.kalzumeus.com/2010/06/17/falsehoods-programmers-believe-about-names/ )
 *	
 * Arguably I am simply gesturing in the direction of a problem, and not actually solving it.
 * But a good naming system is not a current requirement, so this is not my highest priority.
 * 
 * The hope is that users can set their preferred name to whatever they want. When possible,
 * the program will call users by their preferred name.
 */
public class Name
{
	String firstName;
	String lastName;
	String middleName = null;
	String preferredName = null;
	private final int maxNameComponentLength = 150;
	
	public Name(String firstName, String lastName)
	{
		validateName(firstName);
		validateName(lastName);
		
		this.firstName = firstName;
		this.lastName = lastName;
	}

	protected String getFirstName()
	{
		return firstName;
	}

	protected void setFirstName(String firstName)
	{
		validateName(firstName);
		this.firstName = firstName;
	}

	protected String getLastName()
	{
		return lastName;
	}

	protected void setLastName(String lastName)
	{
		validateName(lastName);
		this.lastName = lastName;
	}

	protected String getMiddleName()
	{
		
		return middleName;
	}

	protected void setMiddleName(String middleName)
	{
		//Middle name is optional, so we only test for excess length
		if (middleName.length() > maxNameComponentLength)
		{
			throw new IllegalArgumentException("Sorry, that name is too long for our system");
		}
		
		this.middleName = middleName;
	}

	protected String getPreferredName()
	{
		return preferredName;
	}

	protected void setPreferredName(String preferredName)
	{
		//Special length; a preferred name may consist of several other names stitched together.
		if (preferredName.length() > 300)
		{
			throw new IllegalArgumentException("Sorry, that name is too long for our system");
		}
		this.preferredName = preferredName;
	}
	
	private void validateName(String name)
	{
		if (name.length() > maxNameComponentLength)
		{
			throw new IllegalArgumentException("Sorry, that name is too long for our system");
		}
		if (name.length() < 1)
		{
			throw new IllegalArgumentException("Sorry, that name is too short for our system");
		}
	}	
}
