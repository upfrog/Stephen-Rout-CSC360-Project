import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * As stands, comments cannot reply to other comments
 * 
 */
public class Comment extends Post
{
	
	@JsonIgnore
	int maxCommentLength = 15000;
	
	@JsonIgnore
	final static List<String> linkTypes = new ArrayList<String>(Arrays.asList("ParentPost"));
	
	public Comment(Post parentPost, User creatorUser, String content) 
	{
		validateComment(content);
		getLC().addLink("Creator", creatorUser.getUID());
		getLC().addLink("ParentPost", parentPost.getUID());
		this.content = content;
		
		ServerHandler.INSTANCE.postComment(this); //post the comment to the global list
	}
	
	public Comment() {} //For Jackson compatibility
	
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

	
	public String getCreatorUID()
	{
		return getLC().getList("Creator").get(0);
	}


	public String getParentPostUID()
	{
		return getLC().getList("ParentPost").get(0);
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

	
	@Override
	public List<String> getLinkTypes()
	{
		List<String> result = new ArrayList<String>();
		result.addAll(linkTypes);
		result.addAll(super.getLinkTypes());
		return result;
	}
	
	/*
	 * A pointless method to satisfy the requirements of inheritance.
	 * 
	 * It's bad to keep this, and I hope to excise it eventually.
	 */
	@Override
	public Comment addComment(User creatorUser, String content)
	{
		// TODO Auto-generated method stub
		return null;
	}		
}
