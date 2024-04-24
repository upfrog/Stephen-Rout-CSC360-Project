import java.util.ArrayList;

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
 * The current system is designed to accommodate multi-part names, with the expectation that 
 * a user may or may not want to display all parts.
 */
public class Name
{
	ArrayList<String> nameComponents;
	private static final int maxNameComponentLength = 150;
	ArrayList<Boolean> prefferedNameComponents;
	
	
	/*The first of three constructors in the Name class. This is intended to be the most commonly-used one,
	 * and it represents my hopes to ultimately create a not-horrible name system.
	 * 
	 * This is incomplete. Potential future improvements include:
	 * 1) Set name dividers on a case-by-case basis.
	 * 2) Refactor validateName so that it encapsulates most of this
	 */
	public Name(ArrayList<String> newNameComponents, ArrayList<Boolean> newPrefferedNameComponents)
	{
		
		for (String name : newNameComponents)
		{
			validateName(name);
		}
		if (newPrefferedNameComponents.size() == newNameComponents.size())
		{
			nameComponents = new ArrayList<String>();
			prefferedNameComponents = new ArrayList<Boolean>();
			
			nameComponents = newNameComponents;
			prefferedNameComponents = newPrefferedNameComponents;
		}	
		else
		{
			throw new IllegalArgumentException("Every part of the name must have it's \"Display?\" attribute set");
		}
	}
	
	/*
	 * This constructor is mostly for testing purposes, since it makes it easier to create a name.
	 */
	public Name(String name)
	{
		nameComponents = new ArrayList<String>();
		prefferedNameComponents = new ArrayList<Boolean>();
		
		nameComponents.add(name);
		prefferedNameComponents.add(true);
	}
	

	/*
	 * This constructor only exists because Jackson needs it.
	 */
	public Name() {}

	
	/*
	 * Assembles a name based on the user's preferences.
	 */
	public String getName()
	{
		String name = "";
		for (int i = 0; i < nameComponents.size(); i++)
		{
			if (prefferedNameComponents.get(i).booleanValue() == true)
					name += nameComponents.get(i);
		}
		return name;
	}
	
	/*
	 * Only checks for length
	 */
	private void validateName(String name)
	{
		if (name.length() > maxNameComponentLength)
		{
			throw new IllegalArgumentException("Sorry, the name " + name + " is too long for our system");
		}
		if (name.length() < 1)
		{
			throw new IllegalArgumentException("Sorry, that name " + name + "is too short for our system");
		}
	}

	
	//Getters and setters - mandatory for Jackson
	
	public ArrayList<String> getNameComponents()
	{
		return nameComponents;
	}

	public void setNameComponents(ArrayList<String> nameComponents)
	{
		this.nameComponents = nameComponents;
	}

	public ArrayList<Boolean> getPrefferedNameComponents()
	{
		return prefferedNameComponents;
	}

	public void setPrefferedNameComponents(ArrayList<Boolean> prefferedNameComponents)
	{
		this.prefferedNameComponents = prefferedNameComponents;
	}
}
