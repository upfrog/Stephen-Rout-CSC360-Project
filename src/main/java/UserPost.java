import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	public UserPost(String content, boolean isPublic, User creatorUser)
	{
		validateUserPost(content);
		this.content = content;
		this.isPublic = isPublic;
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
		result.addAll(super.getLinkTypes());
		return result;
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(getUID(), isPublic, content, likes);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof UserPost))
			return false;
		UserPost other = (UserPost) obj;
		return this.getContent().equals(other.getContent())
				&& this.getCreationDateTime().equals(other.getCreationDateTime())
				&& this.getLC().equals(other.getLC())
				&& this.getLikes() == other.getLikes()
				&& this.getUID() == other.getUID()
				&& getIsPublic() == other.getIsPublic();
	}
	
}
