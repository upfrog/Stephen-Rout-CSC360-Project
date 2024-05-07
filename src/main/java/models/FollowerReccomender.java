package models;
import java.util.ArrayList;
import java.util.List;

public class FollowerReccomender implements JobReccomenderInterface
{
	@Override
	public ArrayList<User> getTargetAudience(JobPost post)
	{
		String creatorUID = post.getLC().getList("Creator").get(0);
		User creator = ServerHandler.INSTANCE.getUser(creatorUID);
		List<String> followers = creator.getLC().getList("Followers");
		ArrayList<User> targetAudience = new ArrayList<User>();
		
		for (String UID : followers)
		{
			targetAudience.add(ServerHandler.INSTANCE.getUser(UID));
		}
		return targetAudience;
	}
	
	public FollowerReccomender() {}

	@Override
	public JobReccomenderInterface getJobReccomenderInterface()
	{
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void setJobReccomenderInterface(JobReccomenderInterface rec)
	{
		// TODO Auto-generated method stub
		
	}
}
