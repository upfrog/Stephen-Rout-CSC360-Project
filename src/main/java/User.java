import java.util.ArrayList;

public class User extends Entity
{

	Name displayName;
	String worksAt;
	ArrayList<WorkExperience> workHistory;
	private boolean privacy;
	
	public User() 
	{
		// TODO Auto-generated constructor stub
	}
	
	private void changePrivacy()
	{
		privacy = !privacy;
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
	public void followerChange(User user)
	{
		// TODO Auto-generated method stub
		
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
