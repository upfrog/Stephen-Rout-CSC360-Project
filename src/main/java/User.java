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
 * 
 * Most of the user's attributes are modified from their profile page. Changes
 * made will be pushed to the server when the user saves the changes and exits
 * the settings menu, meaning that methods interacting with these attributes do
 * not need to directly interact with the server. However, some methods may be
 * called from a wider variety of circumstances, and so need to contain their
 * own system for propagating their effect.
 */

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class User extends Entity
{
	@JsonIgnore
	private Name displayName;
	
	private String userType;
	private String worksAt;
	private ArrayList<WorkExperience> workHistory;
	private boolean isPublic;
	//socially responsible default value
	JobReccomenderInterface reccomender = new FollowerReccomender(); 
	
	public User(String userType) 
	{		
		validateUserType(userType);
		
		//populateLinkContainer();
		isPublic = true;
		workHistory = new ArrayList<WorkExperience>();
		this.userType = userType; //Will determine how the profile page is formatted
		this.editorUIDs.add(this.getUID());
		displayName = new Name("Default Name");
		ServerHandler.INSTANCE.postUser(this);
	}
	
	
	public User() {} //Empty constructor for Jackson
	
	/**
	 * Checks that the inputted userType is valid.
	 *  
	 * @return	Whether or not that type of user is on the validUserTypes array.
	 */
	private boolean validateUserType(String userType)
	{
		final String[] validUserTypes = {"Individual", "Organization"}; //for validation
		
		for (String validUserType : validUserTypes)
		{
			if (userType.equals(validUserType))
			{
				return true;
			}
		}
		throw new IllegalArgumentException("Invalid user type");
	}
	
	/*
	 * Modified in the Edit Profile Page
	 */
	public void toggleIsPublic()
	{
		isPublic = !isPublic;
	}
	
	public boolean getIsPublic()
	{
		return isPublic;
	}
	
	/*
	 * Only included for compatibility with Jackson
	 * 
	 * I could make do with only this method to edit publicity, but that would
	 * involve putting more complexity into the edit profile controller.
	 */
	public void setIsPublic(boolean publicity)
	{
		this.isPublic = publicity;
	}
	
	
	/*
	 * Remeber that you still need to integrate the feed for this. 
	 */
	public UserPost createUserPost(String content, boolean isPublic)
	{
		UserPost post = new UserPost(content, isPublic, this);
		this.addUserPostUID(post.getUID());
		ServerHandler.INSTANCE.postUserPost(post);
		ServerHandler.INSTANCE.putUser(this);
		return post;
	}
	
	public void removeUserPost(UserPost userPost)
	{
		removeUserPostUID(userPost.getUID());
		ServerHandler.INSTANCE.deleteUserPost(userPost.getUID());
	}
	
	public JobPost createJobPost(String postTitle, String content)
	{
		JobPost jobPost = new JobPost(postTitle, content, this);
		this.addJobPostUID(jobPost.getUID());
		ServerHandler.INSTANCE.postJobPost(jobPost);
		ServerHandler.INSTANCE.putUser(this);
		return jobPost;
	}
	
	public void reccomendJobPost(JobPost jobPost,
			String mostValuedSkill)
	{
		ArrayList<String> targetAudience = reccomender.getTargetAudience(followerUIDs, mostValuedSkill);
		
		for (String UID : targetAudience)
		{
			User targetUser = ServerHandler.INSTANCE.getUser(UID);
			targetUser.addReccomendedJobUID(jobPost.getUID());
			ServerHandler.INSTANCE.putUser(targetUser);
		}
	}
	
	public void removeJobPost(JobPost jobPost)
	{
		removeJobPostUID(jobPost.getUID());
		ServerHandler.INSTANCE.deleteJobPost(jobPost.getUID());
	}
	
	public void setReccomender(JobReccomenderInterface reccomender)
	{
		this.reccomender = reccomender;
	}
	
	
	public JobReccomenderInterface getReccomender()
	{
		return reccomender;
	}
	
	
	
	/*
	 * This is the only correct entry point for making a comment. 
	 * 
	 * User.createComment calls parentPost.addComment, which itself calls Comment.
	 * 
	 * Comment instantiates the new comment object, validates it, and adds it
	 * to the global comment list.
	 * 
	 * addComent adds the comment to the parentPost's comment list
	 * 
	 * createComment adds the new comment to the user's comment list, and returns
	 * the comment - just in case it's useful.
	 * 
	 * @param	commentBody: the text of the new comment
	 * @param	parentPost: the post which the comment will be associated with.
	 * 
	 */
	public Comment createComment(String commentBody, Post parentPost)
	{
		Comment comment = parentPost.addComment(this, commentBody);
		addCommentUID(comment.getUID());
		ServerHandler.INSTANCE.putUser(this);
		return comment;
	}
	


	/*
	 * Removes comments from each of the three places they are stored:
	 * 1) the parent post of the comment
	 * 2) the global comment list
	 * 3) the user who wrote the comment
	 */
	public void removeComment(Comment comment)
	{
		Post parentPost;
		/*
		 * Gets the parent post, and deletes the comment UID from it's list
		 * 
		 * This is a hacky solution to the fact that comment's parentPost can be 
		 * a JobPost or a UserPost.
		 */
		try
		{
			parentPost = ServerHandler.INSTANCE.getUserPost(comment.getParentPostUID());
			parentPost.removeCommentUID(comment.getUID());	
		}
		catch (Exception e)
		{
			try
			{
				parentPost = ServerHandler.INSTANCE.getJobPost(comment.getParentPostUID());
				parentPost.removeCommentUID(comment.getUID());
			}
			catch (Exception c)
			{
				//If we make it here, then we have a comment with no parent post, which should never happen
				throw c;
			}
		}
		ServerHandler.INSTANCE.deleteComment(comment.getUID());
		removeCommentUID(comment.getUID());
		ServerHandler.INSTANCE.putUser(this);
	}
	
	/**
	 * @param 	post: the Post to be liked
	 */
	public void likeUnlikePost(Post post)
	{
		if (getLikedUIDs().contains(post.getUID()))
		{
			removeLikedUID(post.getUID());
			post.increaseLikes(false);
		}
		else
		{
			addLikedUID(post.getUID());
			post.increaseLikes(true);
		}
		ServerHandler.INSTANCE.putUser(this);
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
	
	public void setUserType(String type)
	{
		this.userType = type;
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
	
	public void setWorkHistory(ArrayList<WorkExperience> workHistory)
	{
		this.workHistory= workHistory;
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
	

	/*
	 * This method, in conjuncture with getReccomendedJobUID(), lets the
	 * user see reccomended jobs, and per their preference, apply for them,
	 * or dismiss them.
	 * 
	 * Due to my current lack of an interface, the method takes the user's
	 * decision as a parameter - despite the fact that they haven't seen
	 * the job post yet! This will be more sensible in the future, but for now,
	 * the basic structure is in place.
	 */
	public void processJobRec(boolean applyForJob)
	{
		JobPost rec = getReccomendedJobUID();
		if (rec == null)
		{
			System.out.println("There are no reccomended jobs");
		}
		
		//Show the job - this will be for Sprint 3!
		
		if (applyForJob)
		{
			rec.addApplicant(this);
		}
		
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
	
	
	/*
	 * Mass declaration for the user's lists of links, and their associated 
	 * methods.
	 * 
	 * The getters and setters are necessary for Jackson. I have also added
	 * a set of add*UID() and remove*UID() methods. These are technically 
	 * unnecessary, and I'm not really sure if they're beneficial; but there 
	 * are a few cases where they are handy, and I wanted to commit to either
	 * interacting with these lists directly, or through a helper method. I
	 * didn't want to mix techniques.
	 */
	ArrayList<String> userPostUIDs = new ArrayList<String>();
	ArrayList<String> likedUIDs = new ArrayList<String>();
	
	ArrayList<String> jobPostUIDs = new ArrayList<String>();
	ArrayList<String> jobsAppliedForUIDs= new ArrayList<String>();
	
	ArrayList<String> commentUIDs = new ArrayList<String>();
	
	ArrayList<String> skills = new ArrayList<String>();
	ArrayList<String> reccomendedJobUIDs = new ArrayList<String>();

	

	public ArrayList<String> getUserPostUIDs()
	{
		return userPostUIDs;
	}


	public void setUserPostUIDs(ArrayList<String> userPostUIDs)
	{
		this.userPostUIDs = userPostUIDs;
	}
	
	
	public void addUserPostUID(String UID)
	{
		this.userPostUIDs.add(UID);
	}

	public void removeUserPostUID(String UID)
	{
		userPostUIDs.remove(UID);
	}

	public ArrayList<String> getLikedUIDs()
	{
		return likedUIDs;
	}


	public void setLikedUIDs(ArrayList<String> likedUIDs)
	{
		this.likedUIDs = likedUIDs;
	}
	
	public void addLikedUID(String UID)
	{
		this.likedUIDs.add(UID);
	}
	
	public void removeLikedUID(String UID)
	{
		likedUIDs.remove(UID);
	}


	public ArrayList<String> getJobPostUIDs()
	{
		return jobPostUIDs;
	}


	public void setJobPostUIDs(ArrayList<String> jobPostUIDs)
	{
		this.jobPostUIDs = jobPostUIDs;
	}
	
	
	public void addJobPostUID(String UID)
	{
		this.jobPostUIDs.add(UID);
	}
	
	public void removeJobPostUID(String UID)
	{
		jobPostUIDs.remove(UID);
	}


	public ArrayList<String> getJobsAppliedForUIDs()
	{
		return jobsAppliedForUIDs;
	}


	public void setJobsAppliedForUIDs(ArrayList<String> jobsAppliedForUIDs)
	{
		this.jobsAppliedForUIDs = jobsAppliedForUIDs;
	}
	
	
	public void addJobAppliedForUID(String UID)
	{
		this.jobsAppliedForUIDs.add(UID);
	}

	public void removeJobAppliedForUID(String UID)
	{
		jobsAppliedForUIDs.remove(UID);
	}

	public ArrayList<String> getCommentUIDs()
	{
		return commentUIDs;
	}


	public void setCommentUIDs(ArrayList<String> commentUIDs)
	{
		this.commentUIDs = commentUIDs;
	}
	
	
	public void addCommentUID(String UID)
	{
		this.commentUIDs.add(UID);
	}
	
	public void removeCommentUID(String UID)
	{
		commentUIDs.remove(UID);
	}


	public ArrayList<String> getSkills()
	{
		return skills;
	}

	public void setSkills(ArrayList<String> skills)
	{
		this.skills = skills;
	}

	public void addSkill(String skill)
	{
		this.skills.add(skill);
	}
	
	public boolean hasSkill(String skill)
	{
		return skills.contains(skill);
	}


	public ArrayList<String> getReccomendedJobUIDs()
	{
		return reccomendedJobUIDs;
	}


	public void setReccomendedJobUIDs(ArrayList<String> reccomendedJobUIDs)
	{
		this.reccomendedJobUIDs = reccomendedJobUIDs;
	}
	
	public void addReccomendedJobUID(String UID)
	{
		this.reccomendedJobUIDs.add(UID);
	}
	
	public JobPost getReccomendedJobUID()
	{
		if (reccomendedJobUIDs.size() > 0)
		{
			JobPost post = ServerHandler.INSTANCE.getJobPost(reccomendedJobUIDs.get(0));
			reccomendedJobUIDs.remove(0);
			return post;
		}
		else
		{
			return null;
		}
	}
	
}
