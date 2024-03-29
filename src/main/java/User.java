import java.util.ArrayList;

public class User extends Entity
{
	String[] linkTypes = {"Posts", "Followers", "Following", "Blocked"};
	Name displayName;
	String worksAt;
	ArrayList<WorkExperience> workHistory;
	private boolean isPublic;
	
	public User() 
	{
		isPublic = true;
		populateLinkContainer();
	}
	
	public void toggleIsPublic()
	{
		isPublic = !isPublic;
	}
	
	public boolean getIsPublic()
	{
		return isPublic;
	}
	
	/*
	 * @param commentBody: the text of the comment
	 * @param parentPost: the Post this comment is replying to
	 */
	private void createComment(String commentBody, Post parentPost)
	{
		//make post
	}
	
	/*
	 * @param post: the Post to be liked
	 */
	private void likePost(Post post)
	{
		//like post
	}
	
	@Override
	public String[] getLinkTypes()
	{
		return this.linkTypes;
	}
	
	@Override
	public void followerChange(User user)
	{
		if (getLinkContainer().contains("Following", user))
		{
			getLinkContainer().removeLink("Following", user);
		}
		else
		{
			getLinkContainer().addLink("Following", user);
		}
	}

	//THESE METHODS ARE UNNECESARRY FOR SPRINT 1
	@Override
	public void push(Post post)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}

}
