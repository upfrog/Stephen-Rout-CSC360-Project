import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This is the top-level class which (almost) all other data classes inherit from.
 * 
 * It's key responsibilities are:
 * 	1) Tracking object creation time and date
 * 	2) Creating and tracking a unique ID for each object
 *  3) Filling each object's LinkContainer
 */
public abstract class Structure
{
	final String UID;
	//String[] linkTypes;
	String creationDateTime;
	//LinkContainer linkContainer;
	

	public Structure()
	{
		this.UID = UUID.randomUUID().toString();
		this.creationDateTime = LocalDateTime.now().toString();
		//this.linkContainer = new LinkContainer();
		
		
	}
	
	/**
	public LinkContainer getLinkContainer()
	{
		return this.linkContainer;
	}
	*/
	
	/**
	 * All objects which inherit from Structure have a LinkContainer, and
	 * an ArrayList called LinkTypes. This method adds each element of 
	 * LinkTypes to the object's LinkContainer.
	 */
	
	/*
	public void populateLinkContainer()
	{
		for (String key : getLinkTypes())
		{
			linkContainer.addLinkList(key);
		}
	}
	*/
	

	
	public String getUID()
	{
		return this.UID;
	}


	public String getCreationDateTime()
	{
		return this.creationDateTime;
	}	
	
	abstract public String[] getLinkTypes();
}
