import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class User extends Entity
{
	private String[] linkTypes = {"UserPosts", "JobPosts", "Followers", "Following",
									"Blocked", "Liked", "JobsAppliedFor"};
	private Name displayName;
	private String[] validUserTypes = {"Individual", "Organization"};
	private String userType; //options are "Individual" or "Organization"
	private String worksAt;
	private ArrayList<WorkExperience> workHistory;
	private boolean isPublic;
	
	
	public User(String userType) 
	{
		//This is hacky, but I don't feel like doing it better right now		
		populateLinkContainer();
		//Sytem.out.println(linkContainer.getList("))
		isPublic = true;
		this.userType = userType;	
		
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
		getLinkContainer().addLink("UserPosts", post);
		return post;
	}
	
	
	public JobPost createJobPost(String postTitle, String content, User creatorUser)
	{
		JobPost jobPost = new JobPost(postTitle, content, creatorUser);
		getLinkContainer().addLink("JobPosts", jobPost);
		return jobPost;
	}
	
	
	
	/*
	 * @param commentBody: the text of the comment
	 * @param parentPost: the Post this comment is replying to
	 */
	public Comment createComment(String commentBody, Post parentPost)
	{
		return parentPost.addComment(this, commentBody);
	}
	
	/*
	 * @param post: the Post to be liked
	 */
	public void likeUnlikePost(Post post)
	{
		if (getLinkContainer().contains("Liked", post))
		{
			getLinkContainer().removeLink("Liked", post);
			post.updateLikes(false);
		}
		else
		{
			getLinkContainer().addLink("Liked", post);
			post.updateLikes(true);
		}
	}
	
	
	@Override
	public String[] getLinkTypes()
	{
		return this.linkTypes;
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


	public void removeWorkExperience(WorkExperience workExperience)
	{
		workHistory.remove(workExperience);
	}

	public void addWorkExperience(WorkExperience workExperience)
	{
		workHistory.add(workExperience);
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
}
