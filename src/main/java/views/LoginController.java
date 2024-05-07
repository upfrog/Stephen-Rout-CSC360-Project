package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
}
