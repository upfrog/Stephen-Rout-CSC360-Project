import java.util.ArrayList;

public class UniversalReccomender extends JobReccomenderInterface
{

	@Override
	public ArrayList<User> getTargetAudience(JobPost post)
	{
		return new ArrayList<User>(ServerHandler.INSTANCE.getAllUsers());
	}
	
	public UniversalReccomender() {}

}
