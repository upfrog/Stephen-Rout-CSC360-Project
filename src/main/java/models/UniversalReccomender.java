package models;
import java.util.ArrayList;

public class UniversalReccomender implements JobReccomenderInterface
{

	public ArrayList<User> getTargetAudience(JobPost post)
	{
		return new ArrayList<User>(ServerHandler.INSTANCE.getAllUsers());
	}
	
	public UniversalReccomender() {}

	public JobReccomenderInterface getJobReccomenderInterface()
	{
		return this;
	}

	public void setJobReccomenderInterface(JobReccomenderInterface rec)
	{
		// TODO Auto-generated method stub
		
	}

}
