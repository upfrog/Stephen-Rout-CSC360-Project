package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import models.ServerHandler;
import models.UserPost;
import models.ViewTransitionModelInterface;

public class FullPostViewController
{
	ViewTransitionModelInterface vtm;
	public UserPost post;
	
	public FullPostViewController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	public void setPost(UserPost post)
	{
		this.post = post;
	}
	
	Node feedGridNode;
    @FXML
    private GridPane feed;

    private List<UserPost> comments;

	public void populateComments()
	{
		feedGridNode = feed;
		
		feedGridNode.setRotate(180); //setAngle(180);
		feed.setGridLinesVisible(true);
		comments = fetchComments();
		//GridPane feedGrid = new GridPane();
		//feed.setContent(feedGrid);
		for (int i = 0; i < comments.size(); i++)
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/PostView.fxml"));
			try
			{
				Node postBox = loader.load();
				UserPostController userPostController = loader.getController();
				userPostController.setVTM(this.vtm);
				userPostController.setData(comments.get(i), i);
				
				

				postBox.setRotate(180);
				feed.add(postBox, 0, i+1); //Without the +1, posts get clustered at the top of the pane
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private List<UserPost> fetchComments()
	{
		
		ArrayList<UserPost> userPosts = new ArrayList<UserPost>();
		for (String UID : this.post.getLC().getList("Comments"))
		{
			userPosts.add(ServerHandler.INSTANCE.getUserPost(UID));
		}

		return userPosts;
	}
}
