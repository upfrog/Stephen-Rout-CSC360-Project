import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Structure
{

	public abstract class Entity
	{

	}


	final String UID;
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


	public String getUID()
	{
		return this.UID;
	}


	public LocalDateTime getCreationDateTime()
	{
		return this.creationDateTime;
	}	
}
