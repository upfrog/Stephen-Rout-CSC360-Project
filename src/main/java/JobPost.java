import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JobPost extends Post
{
	
	String postTitle;
	@JsonIgnore
	final static List<String> linkTypes = new ArrayList<String>(Arrays.asList("Applicants"));
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
		
		this.content = content;
		this.postTitle = postTitle;
		this.getLC().addLink("Creator", creatorUser.getUID());
	}
	
	public JobPost() {} //empty constructor for Jackson

	private void validateJobPost(String postTitle, String content)
	{
		if (postTitle.length() > maxTitleLength)
		{
			throw new IllegalArgumentException("Post title is too long");
		}
		else if (postTitle.length() < 2)
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
	public Comment addComment(User creatorUser, String content)
	{
		Comment comment = new Comment(this, creatorUser, content);
		getLC().addLink("Comments", comment.getUID());
		ServerHandler.INSTANCE.putJobPost(this); //update the post linkContainer
		return comment;
	}
	
	public void addApplicant(User applicant)
	{
		getLC().addLink("Applicants", applicant.getUID());
		ServerHandler.INSTANCE.putJobPost(this);

	}
	
	/*
	 * Removing an applicant can be initiated from the JobPost, so all of the 
	 * requisite code is here, as opposed to being split between User and JobPost.
	 */
	public void removeApplicant(User applicant)
	{
		//applicantUIDs.remove(applicant.getUID());
		getLC().removeLink("Applicants", applicant.getUID());
		ServerHandler.INSTANCE.putJobPost(this);
		applicant.getLC().removeLink("JobsAppliedFor", this.getUID());
		ServerHandler.INSTANCE.putUser(applicant);
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

	@Override
	public List<String> getLinkTypes()
	{
		List<String> result = new ArrayList<String>();
		result.addAll(linkTypes);
		result.addAll(super.getLinkTypes());
		return result;
	}
}
