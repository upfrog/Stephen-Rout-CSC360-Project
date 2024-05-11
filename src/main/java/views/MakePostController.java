package views;


import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.FollowerReccomender;
import models.SkillReccomender;
import models.UniversalReccomender;
import models.User;
import models.ViewTransitionModelInterface;

public class MakePostController
{
	ViewTransitionModelInterface vtm;
	
	public MakePostController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}

    @FXML
    private TextField MakePostTitleField;

    @FXML
    private Button cancelPostButton;

    @FXML
    private RadioButton isJobPostRadioButton;

    @FXML
    private RadioButton isPrivateRadioButton;

    @FXML
    private TextArea makePostSkillsField;
    
    @FXML
    private TextField requriedSkillPercentage;

    @FXML
    private TextArea postBody;

    @FXML
    private ChoiceBox<String> postMethodChoice;

    public void populatePostMethodChoiceBox()
    {
    	postMethodChoice.setItems(FXCollections.observableArrayList (
    			"Skill Match", "Universal", "Follwers"));
    }
    
    
    @FXML
    private Button submitPostButton;

    @FXML
    void onClickCancelPost(ActionEvent event) 
    {

    }
    
    @FXML
    void onClickPost(ActionEvent action)
    {
    	User user = vtm.getUser();
    	
    	if (isJobPostRadioButton.isSelected() == true)
    	{
    		jobPostHelper(user);
    	}
    	else
    	{
    		userPostHelper(user);
    	}
    }

    private void userPostHelper(User user)
    {
    	user.createUserPost(
    			postBody.getText(), 
    			isPrivateRadioButton.isSelected());
    }
    
    
    private void jobPostHelper(User user)
    {
    	//This covers the case that the user does not enter anything, or that they choose Follower 
    	//Follower it the default, as it is the least-spammy approach
    	if (postMethodChoice.getValue() == null ||
    			postMethodChoice.getValue().equals("Followers"))
    	{
    		user.setReccomender(new FollowerReccomender());
    	}
    	else if (postMethodChoice.getValue().equals("Skill Match"))
    	{
    		user.setReccomender(new SkillReccomender());
    	}
    	else if (postMethodChoice.getValue().equals("Universal"))
    	{
    		user.setReccomender(new UniversalReccomender());
    	}
    	else //Catch-all for weird results
    	{
    		user.setReccomender(new FollowerReccomender());
    	}
    	
    	//ArrayList<String> desiredSkills= new ArrayList<String>();
    	ArrayList<String> desiredSkills=  
    			new ArrayList<String>(Arrays.asList(makePostSkillsField.getText().split(" ", 0)));
    	
    	System.out.println(desiredSkills);
    	
    	user.createJobPost(
    			MakePostTitleField.getText(),
    			postBody.getText(),
    			desiredSkills,
    			Double.parseDouble(requriedSkillPercentage.getText()));
    			
    	System.out.println(user.getReccomender().toString());

    }
    
}
