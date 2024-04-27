import java.util.ArrayList;

public class FollowerReccomender extends JobReccomenderInterface
{
	/*
	 * This method isn't very useful, but it lets me be consistent about how
	 * companies send out job posts
	 */
	@Override
	public ArrayList<String> getTargetAudience(ArrayList<String> followerList, String mostValuedSkill)
	{
		return followerList;
	}
	
	public FollowerReccomender() {}
}
