/*
 * This is an abstract class because it should never be directly
 * instantiated
 * 
 */

import java.util.ArrayList;

public abstract class Post extends Structure
{
	String content;
	int likes;
	ArrayList<Comment> comments;
	
	/*
	 * @param increment: true for adding a like, false for taking one away
	 */
	public void updateLikes(boolean increase)
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
		//USE THIS FOR SPRINT 2
		//linkContainer.getList("Comments").add(comment);
		comments.add(comment);
		return comment;
	}
	
	/*
	 * 
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
