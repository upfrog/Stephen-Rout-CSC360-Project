
public abstract class Entity extends Structure implements Followable, Follower
{
	String entityDescription;
	
	public Entity()
	{
		this.entityDescription = "";
	}
	
	public void editDescription(String description)
	{
		if (description.length() > 4084)
		{
			throw new SecurityException("Description is too long - description must be 4084 characters or less");
		}
		else
		{
			this.entityDescription = description;
		}
	}
	
	public UserPost createPost(String)
	{
		
	}
	
	public JobPost createJobPost(String)
	{
		
	}
}
