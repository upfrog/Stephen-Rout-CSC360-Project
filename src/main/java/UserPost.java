
public class UserPost extends Post
{
	boolean isPublic;
	final String[] linkTypes = {"Creator", "Comments", "Other"};

	public UserPost(String content, boolean isPublic, User creatorUser)
	{
		this.content = content;
		this.isPublic = isPublic;
		populateLinkContainer();
		linkContainer.addLink("Creator", creatorUser);
		//this.linkContainer = new LinkContainer();
	}

	public String[] getLinkTypes()
	{
		return linkTypes;
	}
	
}
