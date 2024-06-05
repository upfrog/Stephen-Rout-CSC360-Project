package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Name;
import models.ServerHandler;
import models.User;
import models.ViewTransitionModelInterface;

public class CreateNewUserController
{
	ViewTransitionModelInterface vtm;
	
	public CreateNewUserController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	
    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField displaynameField;

    @FXML
    private TextField passwordField;

    @FXML
    private CheckBox publicityToggle;

    @FXML
    private TextField usernameField;
    
    @FXML
    private Button cancelCreateAccountButton;

    @FXML
    private Button createAccountButton;
    
    @FXML
    private TextField companyField;
    
    @FXML
    private Label errorMessage;
    
    @FXML
    private TextField titleField;

    @FXML
    void onClickCancelCreateAccountButton(ActionEvent event) 
    {
    	vtm.showLoginView();
    }

    @FXML
    void onClickCreateAccountButton(ActionEvent event)
    {
    	if (validateNewAccountData() == true)
    	{
    		try
    		{
				User user = new User("Individual");
				
				user.setUserName(usernameField.getText());
				user.setPassword(passwordField.getText());
				user.setDisplayName(new Name(displaynameField.getText()));
				user.setDescription(descriptionField.getText());
				user.setCurrentRole(titleField.getText());
				user.setWorksAt(companyField.getText());
				ServerHandler.INSTANCE.putUser(user);
				
				
		    	vtm.setUser(user);
		    	vtm.showProfileView();
    		}
    		//Two identical catches, due to my own inconsistency in exception throwing.
    		catch (IllegalArgumentException e)
    		{
    			errorMessage.setText(e.getMessage());
    		}
    		catch (SecurityException e)
    		{
    			errorMessage.setText(e.getMessage());
    		}
    	}
    	
    }
    
    /*
     * Locally checks that all inputs are valid.
     * 
     * This is not necesarry in the current implementation, as object creation and validation
     * already happens locally. That said, if the different parts of the program are de-coupled
     * in the future, it may be handy to have this here.
     * 
     * There is also very little validation at work here. 
     */
    private boolean validateNewAccountData()
    {
    	if (passwordField.getText().length() < 3)
    	{
    		errorMessage.setText("Password must be at least 4 characters");
    		return false;
    	}
    	return true;
    }
    
    
    
    
    

}
