package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button SidebarCompaniesButton;

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
    void showCompanies(ActionEvent event) {

    }

    @FXML
    void showFeed(ActionEvent event) {
    	System.out.println("Feed");
    }

    @FXML
    void showFriendPosts(ActionEvent event) {
    	System.out.println("Friends");

    }

    @FXML
    void showJobFeed(ActionEvent event) {
    	System.out.println("Job Feed");

    }

    @FXML
    void showProfile(ActionEvent event) {
    	System.out.println("Profile");

    }

    @FXML
    void showSearch(ActionEvent event) {
    	System.out.println("Search");

    }
}
