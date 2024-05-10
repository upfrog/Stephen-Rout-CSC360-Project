package models;

import java.util.ArrayList;

public class MiniPost
{
	int likes;
	String content;
	ArrayList<String> comments;
	
	public MiniPost(String content)
	{
		this.content = content;
		likes = 0;
		comments = new ArrayList<String>();
	}

	public int getLikes()
	{
		return likes;
	}

	public void setLikes(int likes)
	{
		this.likes = likes;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public ArrayList<String> getComments()
	{
		return comments;
	}

	public void setComments(ArrayList<String> comments)
	{
		this.comments = comments;
	}
	
}
