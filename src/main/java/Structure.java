import java.time.LocalDateTime;

public abstract class Structure
{
	final int UID;
	final LocalDateTime creationDateTime;
	LinkContainer links;
	

	public Structure()
	{
		this.UID = 3;
		this.creationDateTime = LocalDateTime.now();
		this.links = new LinkContainer();
	}

}
