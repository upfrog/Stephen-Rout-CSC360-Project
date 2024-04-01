
public abstract class Entity extends Structure implements Followable, Follower
{
	String entityDescription;
	
	public Entity()
	{
		this.entityDescription = "This user has not written a description yet";
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
	

	
	
	public String getDescription()
	{
		return entityDescription;
	}
	
	/*public JobPost createJobPost(String)
	{
		
	}*/
	
	public void populateLinkContainer(LinkContainer linkContainer)
	{}
	
	abstract public Post createPost(String content, boolean isPublic);
}


/*
 * TODO:
 * 1) Figure out how to properly use JUnit, and do that. Test before you keep building.
 * 2) See note on getCreator in Post
 * 
 * 
 */
