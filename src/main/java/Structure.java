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
	
	/*
	 * A handy way to encapsulate some comparison operations which will be necessary for 
	 * all instances where I override .equals().
	 * 
	 * I'm not sure how much I should generalize this pattern. In principle, I could 
	 * do this for all objects, which would leave the last part of my .equals() as, at most
	 * three function calls. But I suspect that by that point, I would have used the motif
	 * past usefulness. This method is useful because it applies to every single object in
	 * the model - and if all is at is should be, it is more than enough to distinguish 
	 * two structures.  
	 */
	public boolean universalComparison(Structure a, Structure b)
	{
		return a.UID.equals(b.UID)
				&& a.creationDateTime.equals(b.creationDateTime)
				&& a.lc.equals(b.lc);
	}
	
	/*
	 * between this and universalComparison, I seem to be asymptotically approaching just
	 * implementing .equals() and .hashCode() for all my objects. So why am I not just doing
	 * that? Answer: I think doing so would be unneeded and unproductive. Structure (and it's
	 * immediate descendants) are never directly instantiated.
	 */
	public int universalHash()
	{
		int result = UID.hashCode();
		result = 31 * result + creationDateTime.hashCode();
		result = 31 * result + lc.hashCode();
		
		return result;
	}
	
	abstract public List<String> getLinkTypes();


	
}
