import java.util.ArrayList;

public abstract class JobReccomenderInterface
{
	public abstract ArrayList<User> getTargetAudience(JobPost post);
}
