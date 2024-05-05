import java.util.ArrayList;

public class SkillReccomender extends JobReccomenderInterface
{
	@Override
	public ArrayList<User> getTargetAudience(JobPost post)
	{
		ArrayList<User> userList = ServerHandler.INSTANCE.getAllUsers();
		ArrayList<User> targetAudience = new ArrayList<User>();
		
		for (User user : userList)
		{
			//User user = ServerHandler.INSTANCE.getUser(UID);
			
			if (post.checkSkillMatch(user.getLC().getList("Skills")))
			{
				targetAudience.add(user);
				/*
				 * Is it better decoupled to have the skill match assessment in here, or in the job post?
				 */
			}
		}		

		return targetAudience;
	}
	
	public SkillReccomender() {}
}