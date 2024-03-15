
public abstract class Post extends Structure
{
	String content;
	int likes;
	
	public Post()
	{
		// TODO Auto-generated constructor stub
	}

	/*
	 * @param increment: true for adding a like, false for taking one away
	 */
	public void updateLikes(boolean increment)
	{
		if (increment)
		{
			likes++;
		}
		else
		{
			likes--;
		}
	}
	
	public Structure getCreator()
	{
		return this.linkContainer.getList("Creator").get(0);
	}
}
