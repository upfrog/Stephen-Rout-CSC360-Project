import java.util.ArrayList;

public class UniversalReccomender extends JobReccomenderInterface
{

	@Override
	public ArrayList<String> getTargetAudience(ArrayList<String> followerList, String mostValuedSkill)
	{
		return new ArrayList<String>(); //ServerHandler.INSTANCE.getAllUsers();
	}
	
	public UniversalReccomender() {}

}
