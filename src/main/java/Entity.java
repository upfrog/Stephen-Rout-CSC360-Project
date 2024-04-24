import java.util.ArrayList;

public abstract class Entity extends Structure implements Followable, Follower
{
	
	String entityDescription;
	ArrayList<String> editorList = new ArrayList<String>();
	
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
	
	public void setDescription(String description)
	{
		entityDescription = description;
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
	
	public ArrayList<String> getEditorList()
	{
		return editorList;
	}
	
	public void setEditorList(ArrayList<String> editorList)
	{
		this.editorList = editorList;
	}
	
	public boolean hasAsEditor(User editor)
	{
		return editorList.contains(editor);
	}
	
	/*
	 * NEED REFINING
	 */
	public boolean hasAsEditor(String editorUID)
	{
		return editorList.contains(editorUID);
	}
	
	public void toggleEditor(String editorUID)
	{
		if (editorList.contains(editorUID))
		{
			if (editorList.size() <= 1)
			{ 
				throw new IllegalArgumentException("A user cannot have less than 1 editor");
			}
			else
			{
				editorList.remove(editorUID);
			}
		}
		else
		{
			editorList.add(editorUID);
		}
	}
	
	public void toggleEditor(User editor)
	{
		toggleEditor(editor.getUID());
	}
	
	public void populateLinkContainer(LinkContainer linkContainer) {}
}