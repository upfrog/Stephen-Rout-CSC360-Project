import java.util.ArrayList;

public class SkillReccomender implements JobReccomenderInterface
{
	public ArrayList<User> getTargetAudience(JobPost post)
	{
		ArrayList<User> userList = ServerHandler.INSTANCE.getAllUsers();
		ArrayList<User> targetAudience = new ArrayList<User>();
		
		for (User user : userList)
		{			
			if (post.checkSkillMatch(user.getLC().getList("Skills")))
			{
				targetAudience.add(user);
			}
		}		

		return targetAudience;
	}
	
	public SkillReccomender() {}

	public JobReccomenderInterface getJobReccomenderInterface()
	{
		// TODO Auto-generated method stub
		return this;
	}

	public void setJobReccomenderInterface(JobReccomenderInterface rec)
	{
		// TODO Auto-generated method stub
		
	}
}