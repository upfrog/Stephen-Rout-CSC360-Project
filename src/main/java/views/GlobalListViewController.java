package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import models.JobPost;
import models.ServerHandler;
import models.User;
import models.UserPost;
import models.ViewTransitionModelInterface;

public class GlobalListViewController
{
	ViewTransitionModelInterface vtm;
	
	public GlobalListViewController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	

    @FXML
    private Button showJobPostsButton;

    @FXML
    private Button showUserPostsButton;

    @FXML
    private Button showUsersButton;

    @FXML
    void showAllUserPosts(ActionEvent event) {
    	feedGrid.getChildren().clear();
		feedGrid.setGridLinesVisible(true);


    	
    	populateUserPosts();
    }

    @FXML
    void showAllUsers(ActionEvent event) {
    	feedGrid.getChildren().clear();
		feedGrid.setGridLinesVisible(true);

    	populatePosts();
    }

    @FXML
    void showJobPosts(ActionEvent event) 
    {
    	feedGrid.getChildren().clear();
		feedGrid.setGridLinesVisible(true);

    	populateJobPosts();
    }
    
    @FXML
	GridPane feedGrid;
	Node feedGridNode = feedGrid;
	
	@FXML
	ScrollPane feed;
	
	
	ArrayList<String[]> postIDs = new ArrayList<String[]>(); 
	private List<User> users;

	public void populatePosts()
	{
		feedGridNode = feedGrid;
		
		feedGridNode.setRotate(180); //setAngle(180);
		feedGrid.setGridLinesVisible(true);
		users = fetchUsers();
		//GridPane feedGrid = new GridPane();
		//feed.setContent(feedGrid);
		for (int i = 0; i < users.size(); i++)
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/FriendBoxView.fxml"));
			try
			{
				Node postBox = loader.load();
				FriendBoxController controller = loader.getController();
				controller.setVTM(this.vtm);
				postIDs.add(controller.setData(users.get(i), i));
				
				

				postBox.setRotate(180);
				feedGrid.add(postBox, 0, i+1); //Without the +1, posts get clustered at the top of the pane
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		feedGrid.setGridLinesVisible(true);
	}
	
	
	private void populateUserPosts()
	{
		ArrayList<UserPost> userPosts = 
				new ArrayList<UserPost>(ServerHandler.INSTANCE.getAllUserPosts());
		
		feedGridNode = feedGrid;
		
		feedGridNode.setRotate(180); //setAngle(180);
		feedGrid.setGridLinesVisible(true);

		for (int i = 0; i < userPosts.size(); i++)
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/PostView.fxml"));
			try
			{
				Node postBox = loader.load();
				UserPostController controller = loader.getController();
				controller.setVTM(this.vtm);
				postIDs.add(controller.setData(userPosts.get(i), i));
				
				

				postBox.setRotate(180);
				feedGrid.add(postBox, 0, i+1); //Without the +1, posts get clustered at the top of the pane
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	private void populateJobPosts()
	{
		ArrayList<JobPost> jobPosts = 
				new ArrayList<JobPost>(ServerHandler.INSTANCE.getAllJobPosts());
		
		feedGridNode = feedGrid;
		
		feedGridNode.setRotate(180); //setAngle(180);
		feedGrid.setGridLinesVisible(true);

		for (int i = 0; i < jobPosts.size(); i++)
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/JobPostView.fxml"));
			try
			{
				Node postBox = loader.load();
				JobPostController controller = loader.getController();
				controller.setVTM(this.vtm);
				postIDs.add(controller.setData(jobPosts.get(i), i));
				
				

				postBox.setRotate(180);
				feedGrid.add(postBox, 0, i+1); //Without the +1, posts get clustered at the top of the pane
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private List<User> fetchUsers()
	{
		return ServerHandler.INSTANCE.getAllUsers();
	}
}
