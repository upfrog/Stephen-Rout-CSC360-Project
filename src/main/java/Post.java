/**
 * Contains some foundational logic for comment interaction, such as liking and commenting.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Post extends Structure
{
	String content;
	
	int likes;
	
	//String creatorUID; //in principal, this should be final, but that interferes with Jackson.
	/*
	ArrayList<String> commentUIDs;
	ArrayList<String> likerUIDs;
	*/
	@JsonIgnore
	final static List<String> linkTypes = new ArrayList<String>(Arrays.asList("Comments", "Likers", 
			"Creator"));

	
	
	/*
	 * @param 	increase: 	true for adding a like, false for taking one away
	 */
	public abstract void increaseLikes(boolean increase);
	
	/**
	 * This method is implemented by subclasses to handle the fact that 
	 * UserPosts and JobPosts are stored in different parts of the server.
	 * 
	 * This could be handled by refactoring the ServerHandler class to have one
	 * set of post and put methods for Posts, and using isInstance() to check 
	 * where on the server to search. Alternatively, I could define the method 
	 * once, here in Post, and have Post.addComment() check the type of the parent
	 * post, and choose the ServerHandler method accordingly.
	 * 
	 * Frankly, I think the second approach may be best, but my understanding is
	 * that the Java custom is to prefer overriding methods to checking the type
	 * of their parameters, so I shall follow this custom.
	 * 
	 * @param	commentBody: the text of the new comment
	 * @param	parentPost: the post which the comment will be associated with.
	 */
	public abstract Comment addComment(User creatorUser, String content);
	
	
	public int getLikes()
	{
		return this.likes;
	}
	
	public void setLikes(int likes)
	{
		this.likes = likes;
	}
	
	public String getContent()
	{
		return this.content;
	}
	

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getCreatorUID()
	{
		return getLC().getList("Creator").get(0);
	}

	public List<String> getLinkTypes()
	{
		return linkTypes;
	}
}
