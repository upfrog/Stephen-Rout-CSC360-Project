package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import models.ServerHandler;
import models.User;
import models.UserPost;
import models.ViewTransitionModelInterface;

public class FriendFeedController
{
	ViewTransitionModelInterface vtm;
	
	public FriendFeedController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	Node feedGridNode;
	@FXML
	GridPane feedGrid;
	
	ArrayList<String[]> postIDs = new ArrayList<String[]>(); 
	private List<UserPost> posts;

	public void populatePosts()
	{
		feedGridNode = feedGrid;
		
		feedGridNode.setRotate(180); //setAngle(180);
		feedGrid.setGridLinesVisible(true);
		posts = fetchPosts();
		//GridPane feedGrid = new GridPane();
		//feed.setContent(feedGrid);
		for (int i = 0; i < posts.size(); i++)
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/PostView.fxml"));
			try
			{
				Node postBox = loader.load();
				UserPostController userPostController = loader.getController();
				userPostController.setVTM(this.vtm);
				postIDs.add(userPostController.setData(posts.get(i), i));
				
				

				postBox.setRotate(180);
				feedGrid.add(postBox, 0, i+1); //Without the +1, posts get clustered at the top of the pane
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	private List<UserPost> fetchPosts()
	{
		//ArrayList<MiniPost> posts = new ArrayList<MiniPost>();
		
		ArrayList<UserPost> userPosts = new ArrayList<UserPost>();
		User user = vtm.getUser();
		for (String UID : user.getLC().getList("PostFeed"))
		{
			userPosts.add(ServerHandler.INSTANCE.getUserPost(UID));
		}

		return userPosts;
	}
}
