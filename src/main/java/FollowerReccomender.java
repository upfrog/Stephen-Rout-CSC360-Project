import java.util.ArrayList;

public class FollowerReccomender extends JobReccomenderInterface
{
	@Override
	public ArrayList<User> getTargetAudience(JobPost post)
	{
		String creatorUID = post.getLC().getList("Creator").get(0);
		User creator = ServerHandler.INSTANCE.getUser(creatorUID);
		ArrayList<User> targetAudience = new ArrayList<User>();
		
		for (String UID : creator.getLC().getList("Followers"))
		{
			targetAudience.add(ServerHandler.INSTANCE.getUser(UID));
		}
		return targetAudience;
	}
	
	public FollowerReccomender() {}
}
