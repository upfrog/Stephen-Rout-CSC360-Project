import java.util.ArrayList;

public class SkillReccomender extends JobReccomenderInterface
{
	@Override
	public ArrayList<String> getTargetAudience(ArrayList<String> followerList, String mostValuedSkill)
	{
		ArrayList<String> userList = ServerHandler.INSTANCE.getAllUsers();
		ArrayList<String> targetAudience = new ArrayList<String>();
		for (String UID : userList)
		{
			User user = ServerHandler.INSTANCE.getUser(UID);
			
			if (user.hasSkill(mostValuedSkill))
			{
				targetAudience.add(UID);
			}
		}	
		System.out.println(targetAudience);

		return targetAudience;
	}
	
	public SkillReccomender() {}
}