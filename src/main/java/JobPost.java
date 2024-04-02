import java.util.ArrayList;

public class JobPost extends Post
{
	final String[] linkTypes = {"Creator", "Comments", "Applicants"};
	final String postTitle;
	final int maxPostLength = 10000;
	final int minPostLength = 1;
	final int maxTitleLength = 128;
	final int minTitleLength = 5;
	
	public JobPost(String postTitle, String content, User creatorUser)
	{
		validateJobPost(postTitle, content);
		
		populateLinkContainer();
		linkContainer.addLink("Creator", creatorUser);
		this.content = content;
		this.postTitle = postTitle;
	}

	private void validateJobPost(String postTitle, String content)
	{
		if (postTitle.length() > maxTitleLength)
		{
			throw new IllegalArgumentException("Post title is too long");
		}
		else if (postTitle.length() < 5)
		{
			throw new IllegalArgumentException("Post title is too short");
		}
		else if (content.length() > maxPostLength)
		{
			throw new IllegalArgumentException("Post is too long");
		}
		else if (content.length() < 1)
		{
			throw new IllegalArgumentException("Post is too short");
		}
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
	
	public void removeApplicant(User applicant)
	{
		
		linkContainer.removeLink("Applicants", applicant);
	}
	
	public ArrayList<Structure> getApplicants()
	{
		return linkContainer.getList("Applicants");
	}
}
