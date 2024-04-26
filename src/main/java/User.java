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
	//
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
		this.editorList.add(this.getUID());
		displayName = new Name("");
	}
	
	
	public User() {}
	
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
	
	public JobPost createJobPost(String postTitle, String content)
	{
		JobPost jobPost = new JobPost(postTitle, content, this);
		this.addJobPostUID(jobPost.getUID());
		ServerHandler.INSTANCE.postJobPost(jobPost);
		ServerHandler.INSTANCE.putUser(this);
		return jobPost;
	}
	
	/**
	 * This class posts the new comment to the global comment list, 
	 * while the parentPost's addComment() will add the new comment's UID
	 * to the post's list of comments.
	 * 
	 * @param 	commentBody: 	the text of the comment
	 * @param 	parentPost: 	the Post this comment is replying to
	 */
	public Comment createComment(String commentBody, Post parentPost)
	{
		Comment comment = parentPost.addComment(this, commentBody);
		ServerHandler.INSTANCE.postComment(comment);
		return comment;
	}
	
	public void removeComment(Comment comment)
	{
		Post parentPost;
		
		//This is a hacky solution to the problem that comment's parentPost can be 
		//a JobPost or a UserPost.
		//Given the overhead of sendint a request, this hack makes me feel kind of 
		//dirty
		try
		{
			parentPost = ServerHandler.INSTANCE.getUserPost(comment.getParentPostUID());
		}
		catch (Exception e)
		{
			try
			{
				parentPost = ServerHandler.INSTANCE.getJobPost(comment.getParentPostUID());
			}
			catch (Exception c)
			{
				//If we make it here, then we have a comment with no parent post, which should never happen
				throw c;
			}
		}
		
		parentPost.removeComment(comment.getUID());
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
	
	public String generateNameAsString()
	{
		return this.displayName.getName();
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
	 * a set of add*UID() methods. These are technically unnecessary, but a
	 * bit of tedium here will prevent me from having to constantly perform
	 * a get-modify-set sequence in the rest of my code. 
	 */
	ArrayList<String> userPostUIDs = new ArrayList<String>();
	ArrayList<String> likedUIDs = new ArrayList<String>();
	
	ArrayList<String> jobPostUIDs = new ArrayList<String>();
	ArrayList<String> jobsAppliedForUIDs= new ArrayList<String>();
	
	ArrayList<String> commentUIDs = new ArrayList<String>();
	
	ArrayList<String> followerUIDs = new ArrayList<String>();
	ArrayList<String> followingUIDs = new ArrayList<String>();
	ArrayList<String> blockedUIDs = new ArrayList<String>();

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


	/*
	 * MAY REQUIRE SERVER INTEGRATION
	 */
	public ArrayList<String> getLikedUIDs()
	{
		return likedUIDs;
	}


	public void setLikedUIDs(ArrayList<String> likedUIDs)
	{
		this.likedUIDs = likedUIDs;
	}
	
	public void addLikedUIDs(String UID)
	{
		this.likedUIDs.add(UID);
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


	public ArrayList<String> getFollowerUIDs()
	{
		return followerUIDs;
	}


	public void setFollowerUIDs(ArrayList<String> followerUIDs)
	{
		this.followerUIDs = followerUIDs;
	}
	
	
	public void addFollowerUID(String UID)
	{
		this.followerUIDs.add(UID);
	}


	public ArrayList<String> getFollowingUIDs()
	{
		return followingUIDs;
	}


	public void setFollowingUIDs(ArrayList<String> followingUIDs)
	{
		this.followingUIDs = followingUIDs;
	}
	
	
	public void addFollowingUID(String UID)
	{
		this.followingUIDs.add(UID);
	}


	public ArrayList<String> getBlockedUIDs()
	{
		return blockedUIDs;
	}


	public void setBlockedUIDs(ArrayList<String> blockedUIDs)
	{
		this.blockedUIDs = blockedUIDs;
	}
	
	public void addBlockedUID(String UID)
	{
		this.blockedUIDs.add(UID);
	}

	
}
