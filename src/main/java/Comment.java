import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * As stands, comments cannot reply to other comments
 * 
 */
public class Comment extends Post
{
	final String[] linkTypes = {"Creator", "Parent"};
	
	@JsonIgnore
	int maxCommentLength = 15000;
	String creatorUID;
	String parentPostUID;
	
	public Comment(Post parentPost, User creatorUser, String content) 
	{
		validateComment(content);
		creatorUID = creatorUser.getUID();
		parentPostUID = parentPost.getUID();
		this.content = content;
		
		ServerHandler.INSTANCE.postComment(this);
	}
	
	/**
	 * Checks that comment is neither too long nor too short
	 * 
	 * @param 	content:	The content to be posted.
	 */
	private void validateComment(String content)
	{
		if (content.length() > maxCommentLength)
		{
			throw new IllegalArgumentException("Comment is too long");
		}
		else if (content.length() < 1)
		{
			throw new IllegalArgumentException("Comment is too short");
		}
	}

	@Override
	public String[] getLinkTypes()
	{
		return linkTypes;
	}

	public String getCreatorUID()
	{
		return creatorUID;
	}

	public void setCreatorUID(String creatorUID)
	{
		this.creatorUID = creatorUID;
	}

	public String getParentPostUID()
	{
		return parentPostUID;
	}

	public void setParentPostUID(String parentPostUID)
	{
		this.parentPostUID = parentPostUID;
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
		ServerHandler.INSTANCE.putComment(this);
	}
	
}
