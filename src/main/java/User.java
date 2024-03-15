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
	
	private void createComment(String commentBody)
	{
		//make post
	}
	
	private void likePost(Post post)
	{
		//like post
	}

}
