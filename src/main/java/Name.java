
public class Name
{


	String firstName;
	String lastName;
	String middleName = null;
	String preferredName = null;
	
	public Name(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/*
	 * Consider having the setters return a boolean so you can do error handling?
	 */
	protected String getFirstName()
	{
		return firstName;
	}

	protected void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	protected String getLastName()
	{
		return lastName;
	}

	protected void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	protected String getMiddleName()
	{
		return middleName;
	}

	protected void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	protected String getPreferredName()
	{
		return preferredName;
	}

	protected void setPreferredName(String preferredName)
	{
		this.preferredName = preferredName;
	}
	
	
	
}
