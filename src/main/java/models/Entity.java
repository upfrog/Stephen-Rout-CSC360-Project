package models;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Entity extends Structure implements Followable, Follower
{
	
	String entityDescription;
	@JsonIgnore
	List<String> linkTypes = new ArrayList<String>(Arrays.asList(
			"Followers", "Following"));
	
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
		ServerHandler.INSTANCE.putUser(this);
	}
	
	public String getDescription()
	{
		return entityDescription;
	}
	
	public void setDescription(String description)
	{
		entityDescription = description;
	}
	
	
	/*
	 * Invoked when the calling user (this) wants to follow or unfollow
	 * user (parameter). Also calls followerToggle, to tell the followed/
	 * unfollowed user to add/remove this from their list of followers
	 */
	public void followingToggle(Entity user)
	{	
		if (!user.hasBlocked(this))
		{
			if (getFollowingUIDs().contains(user.getUID()))
			{
				removeFollowingUID(user.getUID());
				user.followerToggle(this);
			}
			else
			{
				addFollowingUID(user.getUID());
				user.followerToggle(this);
			}
			ServerHandler.INSTANCE.putUser(this);
		}

	}
	
	/*
	 * Invoked when the calling user (this) wants to add or remove a
	 * user (parameter) from their followerlist, and by extension,
	 * unsubscribe user from receiving posts.
	 */
	@Override
	public void followerToggle(Entity user)
	{
		if (getFollowerUIDs().contains(user.getUID()))
		{
			removeFollowerUID(user.getUID());
		}
		else
		{
			addFollowerUID(user.getUID());
		}
		ServerHandler.INSTANCE.putUser(this);
	}
	
	/*
	public void followerToggle(User user)
	{
		if (getFollowerUIDs().contains(user.getUID()))
		{
			removeFollowerUID(user.getUID());
		}
		else
		{
			addFollowerUID(user.getUID());
		}
		ServerHandler.INSTANCE.putUser(this);
	}
	*/
	
	/*
	ArrayList<String> followerUIDs = new ArrayList<String>();
	ArrayList<String> followingUIDs = new ArrayList<String>();
	*/
	
	
	
	/*
	 * This is how I control who is and is not able to edit a page.
	 */
	ArrayList<String> editorUIDs = new ArrayList<String>();
	
	public ArrayList<String> getEditorUIDs()
	{
		return editorUIDs;
	}
	
	public void setEditorUID(ArrayList<String> editorList)
	{
		this.editorUIDs = editorList;
	}
	
	
	@Override
	public void pushPost(Post post)
	{
		ArrayList<String> followerUIDs = new ArrayList<String>(this.getFollowerUIDs());
		System.out.println(followerUIDs);
		for (String followerUID : followerUIDs)
		{
			User user = ServerHandler.INSTANCE.getUser(followerUID);
			user.recievePost(post);
			ServerHandler.INSTANCE.putUser(user);
		}
	}
	
	@Override
	public void recievePost(Post post)
	{
		this.getLC().addLink("PostFeed", post.getUID());
	}
	
	
	/*
	 * To be honest, I'm not entirely sure why I made editor methods
	 * that take the editor UID, and ones that take the editor object.
	 */
	public boolean hasAsEditor(User user)
	{
		return hasAsEditor(user.getUID());
	}
	
	public boolean hasAsEditor(String editorUID)
	{
		return getEditorUIDs().contains(editorUID);
	}
	
	public void editorToggle(String editorUID)
	{
		if (editorUIDs.contains(editorUID))
		{
			if (editorUIDs.size() <= 1)
			{ 
				throw new IllegalArgumentException("A user cannot have less than 1 editor");
			}
			else
			{
				editorUIDs.remove(editorUID);
			}
		}
		else
		{
			editorUIDs.add(editorUID);
		}
	}
	
	public void editorToggle(User user)
	{
		editorToggle(user.getUID());
	}
	
	public List<String> getFollowingUIDs()
	{
		return this.getLC().getList("Following");
	}
	
	public void addFollowingUID(String UID)
	{
		//this.followingUIDs.add(UID);
		while (getLC().getList("Following").contains(UID))
		{
			getLC().removeLink("Following", UID);
		}
		getLC().addLink("Following", UID);
		ServerHandler.INSTANCE.putUser(this);
	}

	public void removeFollowingUID(String UID)
	{
		getLC().removeLink("Following", UID);
		ServerHandler.INSTANCE.putUser(this);
	}
	
	
	//Follow*er* methods
	@JsonIgnore
	public List<String> getFollowerUIDs()
	{
		return this.getLC().getList("Followers");
	}
	
	
	public void addFollowerUID(String UID)
	{
		getLC().addLink("Followers", UID);
		ServerHandler.INSTANCE.putUser(this);
	}
	
	public void removeFollowerUID(String UID)
	{
		getLC().removeLink("Followers", UID);
		ServerHandler.INSTANCE.putUser(this);

	}
	
	/*
	 * This is how I handle view permissions. It's a blacklist,
	 * so only users blocked by a given user are restricted from
	 * viewing their posts.
	 */
	ArrayList<String> blockedUIDs = new ArrayList<String>();
	
	public boolean hasBlocked(Entity user)
	{
		return getBlockedUIDs().contains(user.getUID());
	}
	
	public void blockedToggle(Entity user)
	{
		if (getBlockedUIDs().contains(user.getUID()))
		{
			removeBlockedUID(user.getUID());
		}
		else
		{
			addBlockedUID(user.getUID());
		}
	}
	
	public ArrayList<String> getBlockedUIDs()
	{
		return blockedUIDs;
	}

	public void setBlockedUIDs(ArrayList<String> blockedUIDs)
	{
		this.blockedUIDs = blockedUIDs;
	}
	
	public void addBlockedUID(String UID)
	{
		this.blockedUIDs.add(UID);
	}
	
	public void removeBlockedUID(String UID)
	{
		this.blockedUIDs.remove(UID);
	}
	
	@JsonIgnore
	public List<String> getLinkTypes()
	{
		return linkTypes;
	}
	
	
}