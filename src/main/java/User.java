/**
 * User represents all entities the have profile pages, including companies.
 * 
 * In the original design, we planned to have separate classes for companies
 * and individuals. However, as our design evolved the space between these
 * two entities shrank until it seemed indefensible to maintain the split.
 * 
 * None the less, User still extends Entity, meaning that we can extend 
 * to new types of entities in the future.
 * 
 * Some of the method and class division between User and Entity was
 * chosen with the assumption that Users and Companies would be different
 * objects, and that Companies would have non-trivially constrained
 * behavior. These assumptions now seem less reasonable, so there are some
 * methods and attributes which may be moved to Entity in the future.
 */

import java.util.ArrayList;


public class User extends Entity
{
	private Name displayName;
	private final String[] validUserTypes = {"Individual", "Organization"}; //for validation
	private String userType;
	private String worksAt;
	private ArrayList<WorkExperience> workHistory;
	private boolean isPublic;
	
	public User(String userType) 
	{		
		validateUserType(userType);
		
		//populateLinkContainer();
		isPublic = true;
		workHistory = new ArrayList<WorkExperience>();
		this.userType = userType; //Will determine how the profile page is formatted
		this.editorList.add(this);
	}
	

	


	
	/**
	 * Checks that the inputted userType is valid.
	 *  
	 * @return	Whether or not that type of user is on the validUserTypes array.
	 */
	private boolean validateUserType(String userType)
	{
		for (String validUserType : validUserTypes)
		{
			if (userType.equals(validUserType))
			{
				return true;
			}
		}
		throw new IllegalArgumentException("Invalid user type");
	}
	
	public void toggleIsPublic()
	{
		isPublic = !isPublic;
	}
	
	public boolean getIsPublic()
	{
		return isPublic;
	}
	
	public UserPost createUserPost(String content, boolean isPublic)
	{
		UserPost post = new UserPost(content, isPublic, this);
		//getLinkContainer().addLink("UserPosts", post);
		return post;
	}
	
	public JobPost createJobPost(String postTitle, String content, User creatorUser)
	{
		JobPost jobPost = new JobPost(postTitle, content, creatorUser);
		//getLinkContainer().addLink("JobPosts", jobPost);
		return jobPost;
	}
	
	/**
	 * @param 	commentBody: 	the text of the comment
	 * @param 	parentPost: 	the Post this comment is replying to
	 */
	public Comment createComment(String commentBody, Post parentPost)
	{
		Comment comment = parentPost.addComment(this, commentBody);
		//getLinkContainer().addLink("Comments", comment);
		return comment;
	}
	
	public void removeComment(Comment comment, Post parentPost)
	{
		parentPost.removeComment(comment);
		//getLinkContainer().getList("Comments").remove(comment);
	}
	
	/**
	 * @param 	post: the Post to be liked
	 */
	public void likeUnlikePost(Post post)
	{
		/*
		if (getLinkContainer().contains("Liked", post))
		{
			getLinkContainer().removeLink("Liked", post);
			post.increaseLikes(false);
		}
		else
		{
			getLinkContainer().addLink("Liked", post);
			post.increaseLikes(true);
		}
		*/
	}
	
	
	
	@Override
	public String[] getLinkTypes()
	{
		return null;
	}
	
	public Name getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(Name displayName)
	{
		this.displayName = displayName;
	}

	public String getUserType()
	{
		return userType;
	}
	
	public String getWorksAt()
	{
		return worksAt;
	}

	public void setWorksAt(String worksAt)
	{
		this.worksAt = worksAt;
	}

	public ArrayList<WorkExperience> getWorkHistory()
	{
		return workHistory;
	}

	/*
	 * @param 	workExperience: 	the WorkExperience object to be removed
	 */
	public void removeWorkExperience(WorkExperience workExperience)
	{
		workHistory.remove(workExperience);
	}

	/**
	 * @param 	startDate: 		the date the user began working this job.
	 * @param 	endDate: 		the date the user stopped working this job.
	 * @param  	companyName: 	the name of the company.
	 * @param 	jobTitle: 		the user's title at the job.
	 * @param 	description: 	any additional information the user wishes to provide.
	 */
	public WorkExperience addWorkExperience(String startDate, String endDate, 
			String companyName, String jobTitle, String description)
	{
		WorkExperience job = new WorkExperience(startDate, endDate, companyName,
				jobTitle, description);
		workHistory.add(job);
		return job;
	}
	
	//THESE METHODS ARE UNNECESARRY FOR SPRINT 1
	
	@Override
	public void push(Post post)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
	
	
	//Link Types
	ArrayList<UserPost> userPosts = new ArrayList<UserPost>();
	ArrayList<UserPost> liked = new ArrayList<UserPost>();
	
	ArrayList<JobPost> jobPosts = new ArrayList<JobPost>();
	ArrayList<JobPost> jobsAppliedFor= new ArrayList<JobPost>();
	
	ArrayList<Comment> comments = new ArrayList<Comment>();
	
	ArrayList<User> followers = new ArrayList<User>();
	ArrayList<User> followering = new ArrayList<User>();
	ArrayList<User> blocked = new ArrayList<User>();

	public ArrayList<UserPost> getUserPosts()
	{
		return userPosts;
	}

	public void setUserPosts(ArrayList<UserPost> userPosts)
	{
		this.userPosts = userPosts;
	}

	public ArrayList<UserPost> getLiked()
	{
		return liked;
	}

	public void setLiked(ArrayList<UserPost> liked)
	{
		this.liked = liked;
	}

	public ArrayList<JobPost> getJobPosts()
	{
		return jobPosts;
	}

	public void setJobPosts(ArrayList<JobPost> jobPosts)
	{
		this.jobPosts = jobPosts;
	}

	public ArrayList<JobPost> getJobsAppliedFor()
	{
		return jobsAppliedFor;
	}

	public void setJobsAppliedFor(ArrayList<JobPost> jobsAppliedFor)
	{
		this.jobsAppliedFor = jobsAppliedFor;
	}

	public ArrayList<Comment> getComments()
	{
		return comments;
	}

	public void setComments(ArrayList<Comment> comments)
	{
		this.comments = comments;
	}

	public ArrayList<User> getFollowers()
	{
		return followers;
	}

	public void setFollowers(ArrayList<User> followers)
	{
		this.followers = followers;
	}

	public ArrayList<User> getFollowering()
	{
		return followering;
	}

	public void setFollowering(ArrayList<User> followering)
	{
		this.followering = followering;
	}

	public ArrayList<User> getBlocked()
	{
		return blocked;
	}

	public void setBlocked(ArrayList<User> blocked)
	{
		this.blocked = blocked;
	}	
}
