package view_models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.UserPost;

public class FeedModel
{
	ObservableList<UserPost> posts = FXCollections.observableArrayList();
	
	public FeedModel()
	{}
	
	public void addItem(UserPost post)
	{
		posts.add(post);
	}
	
	
	
	/**
	 * @return the items
	 */
	public ObservableList<UserPost> getPosts()
	{
		return posts;
	}



	/**
	 * @param items the items to set
	 */
	public void setItems(ObservableList<UserPost> posts)
	{
		this.posts = posts;
	}

}
