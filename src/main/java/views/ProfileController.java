package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import models.User;
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

    }

    @FXML
    void followUser(ActionEvent event) {

    }

	
}



