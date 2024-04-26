import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JobPost extends Post
{
	
	String postTitle;
	ArrayList<String> applicantUIDs;
	@JsonIgnore
	final String[] linkTypes = {"Creator", "Comments", "Applicants"};
	
	
	@JsonIgnore
	final int maxPostLength = 10000;
	@JsonIgnore
	final int minPostLength = 1;
	@JsonIgnore
	final int maxTitleLength = 128;
	@JsonIgnore
	final int minTitleLength = 5;
	
	public JobPost(String postTitle, String content, User creatorUser)
	{
		validateJobPost(postTitle, content);
		
		//populateLinkContainer();
		//linkContainer.addLink("Creator", creatorUser);
		this.content = content;
		this.postTitle = postTitle;
		this.applicantUIDs = new ArrayList<String>();
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
	
	public void addApplicant(User newApplicant)
	{
		applicantUIDs.add(newApplicant.getUID());
	}
	
	public void removeApplicant(User applicant)
	{
		applicantUIDs.remove(applicant.getUID());
		//linkContainer.removeLink("Applicants", applicant);
	}
	
	public ArrayList<String> getApplicantUIDs()
	{
		return this.applicantUIDs;
	}
	
	public void setApplicantUIDs(ArrayList<String> applicantUIDs)
	{
		this.applicantUIDs = applicantUIDs;
	}
	
	@Override
	public void increaseLikes(boolean increase)
	{
		if (increase)
		{
			likes++;
		}
		else
		{
			likes--;
		}
		ServerHandler.INSTANCE.putJobPost(this);
	}
}
