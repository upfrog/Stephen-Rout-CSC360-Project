import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Consider adding a record of who liked a given post
 */
public class UserPost extends Post
{
	private boolean isPublic;
	@JsonIgnore
	final int maxPostLength = 25000;
	@JsonIgnore
	final int minPostLength = 1;

	public UserPost(String content, boolean isPublic, User creatorUser)
	{
		validateUserPost(content);
		commentUIDs = new ArrayList<String>();
		this.content = content;
		this.isPublic = isPublic;
	}
	
	public UserPost() {} //Empty constructor for Jackson
	
	/**
	 * Checks that post is neither too long nor too short
	 * 
	 * @param 	content:	The content to be posted.
	 */
	private void validateUserPost(String content)
	{
		if (content.length() > maxPostLength)
		{
			throw new IllegalArgumentException("Post is too long");
		}
		else if (content.length() < 1)
		{
			throw new IllegalArgumentException("Post is too short");
		}
	}


	@Override
	public Comment addComment(User creatorUser, String content)
	{
		Comment comment = new Comment(this, creatorUser, content);
		commentUIDs.add(comment.getUID());
		ServerHandler.INSTANCE.putUserPost(this); //update the post's list of comments
		return comment;
	}
	

	@Override
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
		ServerHandler.INSTANCE.putUserPost(this);
	}
	
	/*
	 * This will be called from a dedicated edit page, so it does not need to
	 * immediately put it's updated value to the server.
	 */
	public void toggleIsPublic()
	{
		isPublic = !isPublic;
	}
	
	
	public boolean getIsPublic()
	{
		return this.isPublic;
	}
	
	public void setIsPublic(boolean publicity)
	{
		isPublic = publicity;
	}
}
