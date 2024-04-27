import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This is the top-level class which (almost) all other data classes inherit from.
 * 
 * It had much more to do before I refactored the code to interface with the REST
 * server. For now, it just handles creating Universally Unique IDs (which I
 * abbreviate to UID) and the creation time for objects.
 */
public abstract class Structure
{
	String UID;
	String creationDateTime;
	

	public Structure()
	{
		this.UID = UUID.randomUUID().toString();
		this.creationDateTime = LocalDateTime.now().toString();
		//this.linkContainer = new LinkContainer();
		
		
	}
	
	public String getUID()
	{
		return this.UID;
	}
	
	public void setUID(String UID)
	{
		this.UID = UID;
	}

	public String getCreationDateTime()
	{
		return this.creationDateTime;
	}
	
	public void setCreationDateTime(String creationDateTime)
	{
		this.creationDateTime = creationDateTime;
	}
}
