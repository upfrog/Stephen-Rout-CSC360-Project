/*
 * As stands, comments cannot reply to other comments
 * 
 */
public class Comment extends Post
{
	final String[] linkTypes = {"Creator", "Parent"};
	int maxCommentLength = 15000;
	
	public Comment(Post parentPost, User creatorUser, String content) 
	{
		validateComment(content);
		//populateLinkContainer();
		//linkContainer.addLink("Creator", creatorUser);
		//linkContainer.addLink("Parent", parentPost);
		this.content = content;
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
}
