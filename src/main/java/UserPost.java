import java.util.ArrayList;

public class UserPost extends Post
{
	boolean isPublic;
	final String[] linkTypes = {"Creator", "Comments"};

	public UserPost(String content, boolean isPublic, User creatorUser)
	{
		populateLinkContainer();
		linkContainer.addLink("Creator", creatorUser);
		comments = new ArrayList<Comment>();
		this.content = content;
		this.isPublic = isPublic;

	}

	
	public String[] getLinkTypes()
	{
		return linkTypes;
	}
}
