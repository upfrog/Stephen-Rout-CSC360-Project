import java.util.ArrayList;

public class SkillReccomender extends JobReccomenderInterface
{
	@Override
	public ArrayList<String> getTargetAudience(ArrayList<String> followerList, String mostValuedSkill)
	{
		ArrayList<String> targetAudience = new ArrayList<String>();
		for (String UID : followerList)
		{
			User user = ServerHandler.INSTANCE.getUser(UID);
			
			if (user.hasSkill(mostValuedSkill))
			{
				targetAudience.add(UID);
			}
		}	
		return targetAudience;
	}
	
	public SkillReccomender() {}
}