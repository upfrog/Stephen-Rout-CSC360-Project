package views;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ChoiceBox<?> postMethodChoice;

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
    	
    	
    	System.out.println(user.getDisplayName().getName());
    	
    }

}
