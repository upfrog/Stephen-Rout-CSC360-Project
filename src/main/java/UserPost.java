import java.util.ArrayList;


/*
 * Consider adding a record of who liked a given post
 */
public class UserPost extends Post
{
	private boolean isPublic;
	final String[] linkTypes = {"Creator", "Comments"};
	final int maxPostLength = 25000;
	final int minPostLength = 1;

	public UserPost(String content, boolean isPublic, User creatorUser)
	{
		validateUserPost(content);
		
		//populateLinkContainer();
		//linkContainer.addLink("Creator", creatorUser);
		comments = new ArrayList<Comment>();
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

	
	public void toggleIsPublic()
	{
		isPublic = !isPublic;
	}
	
	
	public boolean getIsPublic()
	{
		return this.isPublic;
	}
	
	
	public String[] getLinkTypes()
	{
		return linkTypes;
	}
}
