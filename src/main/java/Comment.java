/*
 * As stands, comments cannot reply to other comments
 * 
 */
public class Comment extends Post
{
	final String[] linkTypes = {"Creator", "Parent"};
	
	public Comment(Post parentPost, User creatorUser, String content) 
	{
		populateLinkContainer();
		linkContainer.addLink("Creator", creatorUser);
		linkContainer.addLink("Parent", parentPost);
		this.content = content;
		

	};

	@Override
	public String[] getLinkTypes()
	{
		return linkTypes;
	}
}
