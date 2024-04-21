import java.util.ArrayList;

public abstract class Entity extends Structure implements Followable, Follower
{
	
	String entityDescription;
	ArrayList<User> editorList = new ArrayList<User>();
	
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
	
	public void followerChange(User user)
	{
		/*
		if (getLinkContainer().contains("Following", user))
		{
			getLinkContainer().removeLink("Following", user);
		}
		else
		{
			getLinkContainer().addLink("Following", user);
		}
		*/
	}
	
	public boolean hasAsEditor(User editor)
	{
		return editorList.contains(editor);
	}
	
	public void toggleEditor(User editor)
	{
		if (editorList.contains(editor))
		{
			if (editorList.size() <= 1)
			{ 
				throw new IllegalArgumentException("A user cannot have less than 1 editor");
			}
			else
			{
				editorList.remove(editor);
			}
		}
		else
		{
			editorList.add(editor);
		}
	}
	
	public void populateLinkContainer(LinkContainer linkContainer) {}
}