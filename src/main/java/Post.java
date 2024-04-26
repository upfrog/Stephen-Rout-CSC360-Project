/**
 * Contains some foundational logic for comment interaction, such as liking and commenting.
 */

import java.util.ArrayList;

public abstract class Post extends Structure
{
	String content;
	int likes;
	ArrayList<String> commentUIDs;
	ArrayList<String> likerUIDs;
	
	
	/*
	 * @param 	increase: 	true for adding a like, false for taking one away
	 */
	public abstract void increaseLikes(boolean increase);

	
	public int getLikes()
	{
		return this.likes;
	}
	
	public String getContent()
	{
		return this.content;
	}
	
	
	public Comment addComment(User creatorUser, String content)
	{
		Comment comment = new Comment(this, creatorUser, content);
		commentUIDs.add(comment.getUID());
		return comment;
	}
	
	public void removeComment(String UID)
	{
		this.commentUIDs.remove(UID);
	}
	
	/**
	 * MUST BE UPDATED FOR SPRINT 2
	 */
	public ArrayList<String> getComments()
	{
		return commentUIDs;
	}
	
	public Structure getCreator()
	{
		return null;
		//return this.linkContainer.getList("Creator").get(0);
	}

}
