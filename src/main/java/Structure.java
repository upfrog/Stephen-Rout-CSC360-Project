import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Structure
{
	final String UID;
	String[] linkTypes;
	final LocalDateTime creationDateTime;
	LinkContainer linkContainer;
	

	public Structure()
	{
		this.UID = UUID.randomUUID().toString();
		this.creationDateTime = LocalDateTime.now();
		this.linkContainer = new LinkContainer();
		
		
	}
	
	public LinkContainer getLinkContainer()
	{
		return this.linkContainer;
	}
	
	public void populateLinkContainer()
	{
		for (String key : getLinkTypes())
		{
			linkContainer.addLinkList(key);
		}
	}

	public String getUID()
	{
		return this.UID;
	}


	public LocalDateTime getCreationDateTime()
	{
		return this.creationDateTime;
	}	
	
	abstract public String[] getLinkTypes();
}
