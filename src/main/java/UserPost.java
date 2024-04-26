import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;


/*
 * Consider adding a record of who liked a given post
 */
public class UserPost extends Post
{
	private boolean isPublic;
	@JsonIgnore
	final String[] linkTypes = {"Creator", "Comments"};
	@JsonIgnore
	final int maxPostLength = 25000;
	@JsonIgnore
	final int minPostLength = 1;

	public UserPost(String content, boolean isPublic, User creatorUser)
	{
		validateUserPost(content);
		
		//populateLinkContainer();
		//linkContainer.addLink("Creator", creatorUser);
		commentUIDs = new ArrayList<String>();
		this.content = content;
		this.isPublic = isPublic;
	}
	
	
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

	/*
	 * I am overriding this methods to handle the fact that UserPosts,
	 * JobPosts, and Comments are stored in different parts of the server.
	 * 
	 * This could be handled by having one set of post and put methods for 
	 * posts, and using isInstance() to check where to search. Frankly, I think
	 * that might be a better approach, but I'm seeing some claims that it is
	 * more vernacular to simply override the relevant method.
	 * 
	 * Alternatively, the Post class's addComment and increaseLikes methods
	 * could handle this class identification.
	 */
	@Override
	public Comment addComment(User creatorUser, String content)
	{
		Comment comment = new Comment(this, creatorUser, content);
		commentUIDs.add(comment.getUID());
		ServerHandler.INSTANCE.putUserPost(this);
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
	
	
	public String[] getLinkTypes()
	{
		return linkTypes;
	}
}
