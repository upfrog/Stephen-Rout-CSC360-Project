package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.ViewTransitionModelInterface;

public class SidebarController
{
	ViewTransitionModelInterface vtm;
	
	public SidebarController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	


    @FXML
    private Button sidebarFriendPostsButton;

    @FXML
    private Button sidebarHomeButton;

    @FXML
    private Button sidebarJobPostsButton;

    @FXML
    private Button sidebarProfileButton;

    @FXML
    private Button sidebarSearchButton;
    
    @FXML 
    private Button sidebarMakePostButton;
    /*
    @FXML
    private Button sidebarLogOutButton;
    
    @FXML
    private ImageView logOutImage;
    */
    @FXML
    private ImageView homeImage;
    

    @FXML
    private ImageView friendsImage;

    @FXML
    private ImageView jobRecsImage;

    @FXML
    private ImageView profileImage;
    
    @FXML
    private ImageView globalListsImage;
    
    @FXML
    private ImageView makePostImage;
    
    
    
    
    public void populateImages()
    {
    	homeImage.setImage(new Image("img/home.png", true));
    	//companiesImage.setImage(new Image("img/companies.png", true));
    	friendsImage.setImage(new Image("img/group.png", true));
    	jobRecsImage.setImage(new Image("img/jobs.png", true));
    	profileImage.setImage(new Image("img/account_circle.png", true));
    	globalListsImage.setImage(new Image("img/search.png", true));
    	makePostImage.setImage(new Image("img/add.png"));
    	//logOutImage.setImage(new Image("img/logout.png"));

    }
    
    
 

    @FXML
    void showFeed(ActionEvent event) {
    	System.out.println("Feed");
    	vtm.showFeed();
    }

    @FXML
    void showFriendPosts(ActionEvent event) {
    	System.out.println("Friends");
    	vtm.showListOfFriendsView();

    }

    @FXML
    void showJobFeed(ActionEvent event) {
    	System.out.println("Job Feed");
    	vtm.showJobFeedView();

    }

    @FXML
    void showProfile(ActionEvent event) {
    	vtm.showProfileView();

    }

    @FXML
    void showSearch(ActionEvent event) {
    	System.out.println("Search");
    	vtm.showGlobalLists();

    }
    
    @FXML
    void makePost(ActionEvent event) {
    	vtm.showMakePostView();
    	//System.out.println("making post!");

    }
    
    /*
    @FXML
    void logOut(ActionEvent event) {
    	
    	vtm.showLoginView();
    }
	*/
}
