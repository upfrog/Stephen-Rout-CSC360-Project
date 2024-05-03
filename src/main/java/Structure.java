import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	List<String> linkTypes;
	LinkContainer lc; //linkContainer - abbreviated due to the frequency with which it occurs
	String creationDateTime;
	

	public Structure()
	{
		this.UID = UUID.randomUUID().toString();
		this.creationDateTime = LocalDateTime.now().toString();
		this.lc = new LinkContainer();
		
		
	}
	
	public String getUID()
	{
		return this.UID;
	}
	
	public void setUID(String UID)
	{
		this.UID = UID;
	}
	

	public LinkContainer getLC()
	{
		return lc;
	}

	public void setLc(LinkContainer lc)
	{
		this.lc = lc;
	}

	public String getCreationDateTime()
	{
		return this.creationDateTime;
	}
	
	public void setCreationDateTime(String creationDateTime)
	{
		this.creationDateTime = creationDateTime;
	}
	
	
	public LinkContainer getLinkContainer()
	{
		return this.lc;
	}
	
	/**
	 * All objects which inherit from Structure have a LinkContainer, and
	 * an ArrayList called LinkTypes. This method adds each element of the  
	 * subclass's linkTypes to the object's LinkContainer.
	 */
	public void populateLinkContainer()
	{
		for (String key : getLinkTypes())
		{
			lc.addLinkList(key);
		}
	}
	
	
	abstract public List<String> getLinkTypes();


	
}
