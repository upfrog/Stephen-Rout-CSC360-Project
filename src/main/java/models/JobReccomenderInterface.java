package models;
import java.util.ArrayList;

public interface JobReccomenderInterface
{
	public abstract ArrayList<User> getTargetAudience(JobPost post);
	public abstract JobReccomenderInterface getJobReccomenderInterface();
	public abstract void setJobReccomenderInterface(JobReccomenderInterface rec);

}
