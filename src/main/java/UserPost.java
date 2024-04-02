import java.util.ArrayList;

public class UserPost extends Post
{
	private boolean isPublic;
	final String[] linkTypes = {"Creator", "Comments"};
	final int maxPostLength = 25000;

	public UserPost(String content, boolean isPublic, User creatorUser)
	{
		validateUserPost(content);
		
		populateLinkContainer();
		linkContainer.addLink("Creator", creatorUser);
		comments = new ArrayList<Comment>();
		this.content = content;
		this.isPublic = isPublic;

	}
	
	private void validateUserPost(String content)
	{
		if (content.length() > maxPostLength)
		{
			throw new SecurityException("Post is too long - description must be " 
					+ maxPostLength + "characters or less");
		}
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
