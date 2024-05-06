import java.util.ArrayList;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public interface JobReccomenderInterface
{
	public abstract ArrayList<User> getTargetAudience(JobPost post);
	public abstract JobReccomenderInterface getJobReccomenderInterface();
	public abstract void setJobReccomenderInterface(JobReccomenderInterface rec);

}
