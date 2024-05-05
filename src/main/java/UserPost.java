import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * Consider adding a record of who liked a given post
 */
public class UserPost extends Post
{
	private boolean isPublic;
	@JsonIgnore
	final static int maxPostLength = 25000;
	@JsonIgnore
	final static int minPostLength = 1;
	
	final static List<String> linkTypes = new ArrayList<String>(Arrays.asList("Comments", "Likers", 
			"Creator"));

	public UserPost(String content, boolean isPublic, User creatorUser)
	{
		validateUserPost(content);
		this.content = content;
		this.isPublic = isPublic;
		populateLinkContainer();
		this.getLC().addLink("Creator", creatorUser.getUID());

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
		this.getLC().addLink("Comments", comment.getUID());
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
	
	@Override
	public List<String> getLinkTypes()
	{
		List<String> result = new ArrayList<String>();
		result.addAll(linkTypes);
		
		return result;
	}

	@Override
	public int hashCode()
	{
		int c = 31;
		int result = universalHash();
		result = c * result + content.hashCode();
		result = c * result + Boolean.hashCode(isPublic);
		result = c * result + likes;
		
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof UserPost))
			return false;
		UserPost other = (UserPost) obj;
		return universalComparison(this, other)
				&& this.content.equals(other.content)
				&& this.likes == other.likes
				&& this.isPublic == other.isPublic;
	}
	
}
