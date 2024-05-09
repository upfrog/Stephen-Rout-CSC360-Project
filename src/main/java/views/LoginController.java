package views;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.ServerHandler;
import models.User;
import models.ViewTransitionModelInterface;

public class LoginController
{
	ViewTransitionModelInterface vtm;
	
	public LoginController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}

	@FXML
	public void onClickLogin(ActionEvent action)
	{
		warningMessage.setText("");


		try
		{
			User user = authenticateUser(usernameField.getText(), passwordField.getText());
			vtm.showProfileView();
		} 
		catch (SecurityException e)
		{
			warningMessage.setText("Username or password is wrong");
		}
		
		//vtm.showProfileView();
	}
	
	/*
	 * This security is, I should note, so bad that it makes me want to writhe on the floor in aspirant 
	 * professional agony.
	 */
	private User authenticateUser(String username, String password) throws SecurityException
	{
		ArrayList<User> users = ServerHandler.INSTANCE.getAllUsers();
		for (User user : users)
		{
			if (user.getUserName().equals(username) && user.getPassword().equals(password))
			{
				return user;
			}
		}
		
		throw new SecurityException("Username or password is incorrect");
	}
	
    @FXML
    private Label warningMessage;
	
    @FXML
    private Button loginButton;

    @FXML
    private Button loginMakeNewAccountButton;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;


    @FXML
    void onMakeNewAccount(ActionEvent event) {
		warningMessage.setText("");

    }
}
