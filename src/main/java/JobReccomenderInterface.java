import java.util.ArrayList;

public abstract class JobReccomenderInterface
{
	public abstract ArrayList<String> getTargetAudience(ArrayList<String> followerList, 
			String mostValuedSkill);
}
