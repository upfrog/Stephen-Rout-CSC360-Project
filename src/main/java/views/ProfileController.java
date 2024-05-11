package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.MiniPost;
import models.ServerHandler;
import models.User;
import models.UserPost;
import models.ViewTransitionModelInterface;

public class ProfileController
{

	ViewTransitionModelInterface vtm;
	User user;
	
	public ProfileController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	


	
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public void populatePage()
	{
		System.out.println("populating!");
		profileDescription.setText("I'm a developer (of software!) doing the sorts of things that developers (of software) like to do!");
		User user = vtm.getUser();
		profileDescription.setText(user.getDescription());
		profileName.setText(user.getDisplayName().getName());
		profileTitleAndCompany.setText(user.getCurrentRole() + "@" + user.getWorksAt());
		
	}
	
    @FXML
    private ListView<?> contentList;

    @FXML
    private Text profileDescription;

    @FXML
    private Button profileEditUserButton;

    @FXML
    private Button profileFollowUserButton;

    @FXML
    private Label profileName;

    @FXML
    private Label profileTitleAndCompany;

    @FXML
    void editUser(ActionEvent event) {
    	vtm.showEditProfileView();

    }

    @FXML
    void followUser(ActionEvent event) {

    }
    
    @FXML
	private ScrollPane feed;
    @FXML
    private GridPane feedGrid;
    @FXML
    private Node feedGridNode;
	private List<UserPost> posts;
	
	public void initialize()
	{
		//populateImages();
		
	}
    
	ArrayList<String[]> postIDs = new ArrayList<String[]>(); 
	
	/*
	 * By default, this solution causes posts to be made in chronological order
	 */
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
	
	
	
	/*
	public void populateImages()
	{
		homeImage.setImage(new Image("img/home.png", true));
    	companiesImage.setImage(new Image("img/companies.png", true));
	}
	*/
    
/* WORKS!
    @FXML
	private ScrollPane feed;
	private List<MiniPost> posts;
	
	public void initialize()
	{
		posts = fetchPosts();
		GridPane feedGrid = new GridPane();
		feed.setContent(feedGrid);
		for (int i = 0; i < posts.size(); i++)
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/PostView.fxml"));
			try
			{
				Node postBox = loader.load();
				MiniPostController miniController = loader.getController();
				miniController.setData(posts.get(i));
				
				feedGrid.add(postBox, 0, i);
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	*/

	private List<UserPost> fetchPosts()
	{
		//ArrayList<MiniPost> posts = new ArrayList<MiniPost>();
		
		ArrayList<UserPost> userPosts = new ArrayList<UserPost>();
		User user = vtm.getUser();
		for (String UID : user.getLC().getList("UserPosts"))
		{
			userPosts.add(ServerHandler.INSTANCE.getUserPost(UID));
		}
		
		/*
		posts.add(new MiniPost("I'm having an amazing time on Nexus!"));
		posts.add(new MiniPost("Lorum ipsum"));
		posts.add(new MiniPost("It is a truth universally acknowledge that a single man in want of"));
		posts.add(new MiniPost("Anyone hiring? I need money to pay my rent!"));
		posts.add(new MiniPost("Step right up for induction into the cult of the Eleysian mysteries!"));
		posts.add(new MiniPost("I'm having an amazing time on Nexus!"));
		posts.add(new MiniPost("I'm having an amazing time on Nexus!"));
		posts.add(new MiniPost("Lorum ipsum"));
		posts.add(new MiniPost("It is a truth universally acknowledge that a single man in want of"));
		posts.add(new MiniPost("Anyone hiring? I need money to pay my rent!"));
		posts.add(new MiniPost("Step right up for induction into the cult of the Eleysian mysteries!"));
		posts.add(new MiniPost("'It is a truth universally acknowledged, that a single man in possession of a good fortune, must be in want of a wife'.\r\n"
				+ "\r\n"
				+ "No-one in 1813 who read that opening sentence of Jane Austen's second novel would have imagined that it was destined to become one of the most famous first lines in literary history. This first edition featured in our display '200 years of \"Pride and Prejudice\": From Austen to zombies', which , which ran at the National Library of Scotland from 10 July to 15 September 2013."));
		*/
		return userPosts;
	}
	
}



