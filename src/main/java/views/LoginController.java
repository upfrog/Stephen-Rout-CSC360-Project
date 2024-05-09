package views;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
		System.out.println("Clicked login!");
		vtm.showProfileView();
	}
	
	/*
	 * This security is, I should note, so bad that it makes me want to write on the floor in aspirant 
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
}
