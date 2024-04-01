
public class JobPost extends Post
{
	final String[] linkTypes = {"Creator", "Comments", "Applicants"};

	public JobPost(String content, User creatorUser)
	{
		populateLinkContainer();
		linkContainer.addLink("Creator", creatorUser);
		this.content = content;
	}

	@Override
	public String[] getLinkTypes()
	{
		return linkTypes;
	}
	
	public void addApplicant(User applicant)
	{
		linkContainer.addLink("Applicants", applicant);
	}

}
