/**
 * Contains some foundational logic for comment interaction, such as liking and commenting.
 */

import java.util.ArrayList;

public abstract class Post extends Structure
{
	String content;
	int likes;
	ArrayList<Comment> comments;
	
	/*
	 * @param 	increase: 	true for adding a like, false for taking one away
	 */
	public void increaseLikes(boolean increase)
	{
		if (increase)
		{
			likes++;
		}
		else
		{
			likes--;
		}
	}
	
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
		comments.add(comment);
		return comment;
	}
	
	public void removeComment(Comment comment)
	{
		this.comments.remove(comment);
	}
	
	/**
	 * MUST BE UPDATED FOR SPRINT 2
	 */
	public ArrayList<Comment> getComments()
	{
		return comments;
	}
	
	public Structure getCreator()
	{
		return this.linkContainer.getList("Creator").get(0);
	}

}
