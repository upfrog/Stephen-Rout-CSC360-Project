import java.util.ArrayList;

public class TestUser
{
	String displayName;
	ArrayList<String> posts;
	String description;
	int pastJobs;
	TestUser firstFollower;
	
	/**
	 * @param displayName
	 * @param posts
	 * @param description
	 * @param pastJobs
	 */
	protected TestUser(String displayName, String description, int pastJobs)
	{
		super();
		this.displayName = displayName;
		this.description = description;
		this.pastJobs = pastJobs;
	}
	
	protected TestUser() {}
	
	
	protected void addPost(String content)
	{
		this.posts.add(content);
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public ArrayList<String> getPosts()
	{
		return posts;
	}

	public void setPosts(ArrayList<String> posts)
	{
		this.posts = posts;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getPastJobs()
	{
		return pastJobs;
	}

	public void setPastJobs(int pastJobs)
	{
		this.pastJobs = pastJobs;
	}

	public TestUser getFirstFollower()
	{
		return firstFollower;
	}

	public void setFirstFollower(TestUser firstFollower)
	{
		this.firstFollower = firstFollower;
	}
	

	

}
