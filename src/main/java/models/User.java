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
package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;



public class User extends Entity
{
	private Name displayName = new Name();
	private String userName = "Default";
	@JsonIgnore
	private static List<String> linkTypes = new ArrayList<String>(Arrays.asList("UserPosts", "JobPosts", 
			"Comments", "Blocked", "LikedPosts", "JobsAppliedFor", "Skills", "ReccomendedJobs", "PostFeed"));
	private String userType = "";
	private String worksAt = "";
	private String currentRole = "";
	private List<WorkExperience> workHistory = new ArrayList<WorkExperience>();
	private boolean isPublic = false;
	private String password = "";
	//socially responsible default value
	@JsonIgnore
	public JobReccomenderInterface reccomender = new FollowerReccomender(); 
	
	public User(String userType) 
	{		
		//validateUserType(userType);
		
		populateLinkContainer();
		isPublic = true;
		workHistory = new ArrayList<WorkExperience>();
		this.userType = userType; //Will determine how the profile page is formatted
		this.editorUIDs.add(this.getUID());
		displayName = new Name("Default");
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
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName()
	{
		return this.userName;
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
	
	
	public String getPassword()
	{
		return password;
	}


	public void setPassword(String password)
	{
		this.password = password;
	}

	

	public String getCurrentRole()
	{
		return currentRole;
	}


	public void setCurrentRole(String currentRole)
	{
		this.currentRole = currentRole;
	}


	/*
	 * Remeber that you still need to integrate the feed for this. 
	 */
	public UserPost createUserPost(String content, boolean isPublic)
	{
		UserPost post = new UserPost(content, isPublic, this);
		getLC().addLink("UserPosts", post.getUID());
		pushPost(post);
		ServerHandler.INSTANCE.postUserPost(post);
		ServerHandler.INSTANCE.putUser(this);
		
		return post;
	}
	
	public void removeUserPost(UserPost userPost)
	{
		getLC().removeLink("UserPosts", userPost.getUID());
		ServerHandler.INSTANCE.deleteUserPost(userPost.getUID());
		ServerHandler.INSTANCE.putUser(this);
	}
	
	public JobPost createJobPost(String postTitle, String content)
	{
		JobPost jobPost = new JobPost(postTitle, content, this);
		getLC().addLink("JobPosts", jobPost.getUID());
		
		ArrayList<User> targetAudience = getTargetAudience(jobPost);
		jobPost.pushJobPost(targetAudience);
		ServerHandler.INSTANCE.postJobPost(jobPost);
		ServerHandler.INSTANCE.putUser(this);
		return jobPost;
	}
	
	public JobPost createJobPost(String postTitle, String content, 
			ArrayList<String> desiredSkills, double desiredSkillMatchPercentage)
	{
		JobPost jobPost = new JobPost(postTitle, content, this, desiredSkills, desiredSkillMatchPercentage);
		getLC().addLink("JobPosts", jobPost.getUID());
		
		ArrayList<User> targetAudience = getTargetAudience(jobPost);
		jobPost.pushJobPost(targetAudience);
		ServerHandler.INSTANCE.postJobPost(jobPost);
		ServerHandler.INSTANCE.putUser(this);
		return jobPost;
	}
	
	
	/*
	public void reccomendJobPost(JobPost jobPost)
	{
		ArrayList<User> targetAudience = jobPost.getTargetAudience();
		
		for (User user : targetAudience)
		{
			user.getLC().addLink("ReccomendedJobs", jobPost.getUID());
			ServerHandler.INSTANCE.putUser(user);
		}
	}
	*/
	
	public ArrayList<User> getTargetAudience(JobPost jobPost)
	{
		return reccomender.getTargetAudience(jobPost);
	}
	
	public void setReccomender(JobReccomenderInterface reccomender)
	{
		this.reccomender = reccomender;
	}
	
	
	public JobReccomenderInterface getReccomender()
	{
		return reccomender;
	}
	
	
	public void removeJobPost(JobPost jobPost)
	{
		getLC().removeLink("JobPosts", jobPost.getUID());
		ServerHandler.INSTANCE.deleteJobPost(jobPost.getUID());
		ServerHandler.INSTANCE.putUser(this);
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
		getLC().addLink("Comments", comment.getUID());
		ServerHandler.INSTANCE.putUser(this);
		return comment;
	}
	
	
	/*
	 * This method handles modifying the user's LinkContainer and syncing the results,
	 * while the JobPost's addApplicant() method will update the JobPost's LinkContainer
	 * with the new applicant and sync it to the server.
	 */
	public void applyForJobPost(JobPost jobPost)
	{
		jobPost.addApplicant(this);
		getLC().addLink("JobsAppliedFor", jobPost.getUID());
		ServerHandler.INSTANCE.putUser(this);
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
			parentPost.getLC().removeLink("Comments", comment.getUID());	
		}
		catch (Exception e)
		{
			try
			{
				parentPost = ServerHandler.INSTANCE.getJobPost(comment.getParentPostUID());
				parentPost.getLC().removeLink("Comments", comment.getUID());	
			}
			catch (Exception c)
			{
				//If we make it here, then we have a comment with no parent post, which should never happen
				throw c;
			}
		}
		ServerHandler.INSTANCE.deleteComment(comment.getUID());
		getLC().removeLink("Comments", comment.getUID());
		ServerHandler.INSTANCE.putUser(this);
	}
	
	/**
	 * This method handles the updating the User's LinkContainer to reflect
	 * the like action, and then updating the user's data on the Server. The post's increaseLikes()
	 * method handles updating the post's data on the server.
	 * 
	 * @param 	post: the Post to be liked
	 * 
	 */
	public void likeUnlikePost(Post post)
	{
		if (getLC().getList("LikedPosts").contains(post.getUID()))
		{
			getLC().removeLink("LikedPosts", post.getUID());
			post.increaseLikes(false);
		}
		else
		{
			getLC().addLink("LikedPosts", post.getUID());
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

	public List<WorkExperience> getWorkHistory()
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
		JobPost rec =  getReccomendedJobUID();
		if (rec == null)
		{			
		}
		else if (applyForJob)
		{
			rec.addApplicant(this);
			ServerHandler.INSTANCE.putJobPost(rec);
		}
		//Show the job - this will be for Sprint 3!
		

		
	}
	

	//THESE METHODS ARE UNNECESARRY FOR SPRINT 1
	

	


	
	
	public User updatedUser()
	{
		return ServerHandler.INSTANCE.getUser(this.getUID());
	}
	
	public void pushUser()
	{
		ServerHandler.INSTANCE.putUser(this);
	}


	@JsonIgnore
	public JobPost getReccomendedJobUID()
	{
		List<String> recs = getLC().getList("ReccomendedJobs");
		if (recs.size() > 0)
		{
			String UID = recs.get(0);
			JobPost post = ServerHandler.INSTANCE.getJobPost(UID);
			recs.remove(0);
			getLC().removeLink("ReccomendedJobs", UID);
			pushUser();
			return post;
		}
		else
		{
			return null;
		}
	}


	/*
	 * Condenses the link types of the this object, and all it's superclasses.
	 * 
	 * There isn't very much benefit to this approach now, but it is a more
	 * scaleable solution for the future.
	 */
	@JsonIgnore
	public List<String> getLinkTypes()
	{
		List<String> result = new ArrayList<String>();
		result.addAll(linkTypes);
		result.addAll(super.getLinkTypes());
		return result;
	}
	
	
	@Override
	public int hashCode()
	{
		int c = 31;
		int result = universalHash();
		result = c * result + displayName.hashCode();
		result = c * result + userName.hashCode();
		result = c * result + userType.hashCode();
		result = c * result + worksAt.hashCode();
		result = c * result + workHistory.hashCode();
		result = c * result + Boolean.hashCode(isPublic);
		
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		
		return universalComparison(this, other)
				&& this.displayName.equals(other.displayName)
				&& this.userName.equals(other.userName)
				&& this.userType.equals(other.userType) 
				&& this.worksAt.equals(other.worksAt)
				&& this.workHistory.equals(other.workHistory)
				&& this.isPublic == other.isPublic;
	}



}
