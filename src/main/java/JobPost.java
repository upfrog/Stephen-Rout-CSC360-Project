import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JobPost extends Post
{
	ArrayList<String> desiredSkills = new ArrayList<String>(); //Optional; not all jobPosts will use this.
	String postTitle = "default";
	double desiredSkillMatchPercentage = 0.0; //Optional
	//@JsonIgnore
	//JobReccomenderInterface reccomender = new FollowerReccomender();
	@JsonIgnore
	final static List<String> linkTypes = new ArrayList<String>(Arrays.asList("Comments", "Likers", 
			"Creator", "Applicants"));
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
		populateLinkContainer();
		this.getLC().addLink("Creator", creatorUser.getUID());
	}
	
	public JobPost(String postTitle, String content, User creatorUser, ArrayList<String> desiredSkills, 
			double desiredSkillMatchPercentage)
	{
		validateJobPost(postTitle, content);
		
		this.content = content;
		this.postTitle = postTitle;
		populateLinkContainer();
		this.getLC().addLink("Creator", creatorUser.getUID());
		this.desiredSkills = desiredSkills;
		this.desiredSkillMatchPercentage = desiredSkillMatchPercentage;
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
	
	
	
	public String getPostTitle()
	{
		return postTitle;
	}

	public void setPostTitle(String postTitle)
	{
		this.postTitle = postTitle;
	}
	
	public ArrayList<String> getDesiredSkills()
	{
		return desiredSkills;
	}

	public void setDesiredSkills(ArrayList<String> desiredSkills)
	{
		this.desiredSkills = desiredSkills;
	}

	

	public double getDesiredSkillMatchPercentage()
	{
		return desiredSkillMatchPercentage;
	}

	public void setDesiredSkillMatchPercentage(double desiredSkillMatchPercentage)
	{
		this.desiredSkillMatchPercentage = desiredSkillMatchPercentage;
	}
	
	public void pushJobPost(ArrayList<User> targetAudience)
	{
		//ArrayList<User> targetAudience = this.getTargetAudience();
		
		for (User user : targetAudience)
		{
			user.getLC().addLink("ReccomendedJobs", this.getUID());
			ServerHandler.INSTANCE.putUser(user);
		}
	}
	


	/**
	 * Checks the compatibility of a user for this job, based on the overlap between the User's skills, and
	 * the JobPosts's desired skills.
	 * 
	 * In the future, this seems to be fertile ground for expanding further with composition.
	 * 
	 * As stands, a JobPost has a JobReccomender, which uses a strategy pattern to vary the implementation 
	 * depending on the user's choices. A similar technique could be applied for filtering out users once
	 * they have been selected as part of the target audience.
	 * 
	 * @param 	skills: A string list of skills which a given user has
	 * @return 	true if the user has a sufficient percentage of the skills listen in desiredSkills,
	 * 			false if not.
	 */
	public boolean checkSkillMatch(List<String> skills)
	{
		float matchedSkills = 0;
		float maxSkills = (float) desiredSkills.size();
		for (String skill : skills)
		{
			if (this.desiredSkills.contains(skill))
			{
				 matchedSkills++;
			}
		}
		return ((float) matchedSkills/maxSkills) >= this.desiredSkillMatchPercentage;
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
		//result.addAll(super.getLinkTypes());
		return result;
	}
	
	@Override
	public int hashCode()
	{
		int c = 31;
		int result = universalHash();
		result = c * result + content.hashCode();
		result = c * result + postTitle.hashCode();
		result = c * result + likes;
		
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof JobPost))
			return false;
		JobPost other = (JobPost) obj;
		return universalComparison(this, other)
				&& this.content.equals(other.content)
				&& this.postTitle.equals(other.postTitle)
				&& this.likes == other.likes;
	}
}
