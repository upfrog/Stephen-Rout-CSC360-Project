package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import models.ServerHandler;
import models.User;
import models.UserPost;
import models.ViewTransitionModelInterface;

public class ListOfFriendsFeedController
{
	ViewTransitionModelInterface vtm;
	
	public ListOfFriendsFeedController()
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
	private List<User> friends; 


	public void populatePosts()
	{
		feedGridNode = feedGrid;
		//Horrible, horrible, hacky way to not solve the problem I have with duplicating links
		HashMap<String, String> friendMap = new HashMap<String, String>();
		
		feedGridNode.setRotate(180); //setAngle(180);
		feedGrid.setGridLinesVisible(true);
		friends = fetchUsers();
		//GridPane feedGrid = new GridPane();
		//feed.setContent(feedGrid);
		for (int i = 0; i < friends.size(); i++)
		{
			if (friendMap.get(friends.get(i).getUID()) == null)
			{
				
			
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("../views/FriendBoxView.fxml"));
				try
				{
					Node postBox = loader.load();
					FriendBoxController friendBoxController = loader.getController();
					friendBoxController.setVTM(this.vtm);
					postIDs.add(friendBoxController.setData(friends.get(i), i));
					
					
	
					postBox.setRotate(180);
					feedGrid.add(postBox, 0, i+1); //Without the +1, posts get clustered at the top of the pane
					
					
					friendMap.put(friends.get(i).getUID(),"Y");
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	private List<User> fetchUsers()
	{
		//ArrayList<MiniPost> posts = new ArrayList<MiniPost>();
		
		ArrayList<User> userPosts = new ArrayList<User>();
		User user = vtm.getUser();
		for (String UID : user.getLC().getList("Following"))
		{
			userPosts.add(ServerHandler.INSTANCE.getUser(UID));
		}

		return userPosts;
	}
}
