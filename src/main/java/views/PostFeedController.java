package views;

import java.util.ArrayList;

import models.Post;
import models.UserPost;

public class PostFeedController
{
	ArrayList<Post> posts;
	
	public void setModel(ArrayList<UserPost> posts)
	{
		posts.setCellFactory(new Callback<ListView<User>>, ListCell<UserPost>>()
				{
					@Override
					public ListCell<UserPost> call(ListView<UserPost> param)
					{
						return new UserPostCell(param);
					}
				}
	}
}
