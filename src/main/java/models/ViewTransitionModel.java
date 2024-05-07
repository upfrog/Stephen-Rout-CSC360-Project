package models;

import java.io.IOException;

import models.User;
import views.ProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/*
 * Stores methods to transition between views
 */
public class ViewTransitionModel implements ViewTransitionModelInterface
{
	Stage stage;
	BorderPane view;
	
	public ViewTransitionModel(Stage stage)
	{
		this.stage = stage;
	}
	
	public ViewTransitionModel(BorderPane view)
	{
		this.view = view;
	}

	@Override
	public void showProfileView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass()
				.getResource("../views/ProfileView.fxml"));
		try
		{
			BorderPane view = loader.load();
			this.view.setCenter(view);
			ProfileController cont = loader.getController();
			User user = new User("Individual");
			cont.setVTM(this);
			cont.setUser(user);
			cont.populatePage();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showLoginView()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass()
				.getResource("../Views/LoginView.fxml"));
		try
		{
			BorderPane view = loader.load();
			this.view.setCenter(view);
			/*
			ProfileController cont = loader.getController();
			User user = new User("Individual");
			cont.setVTM(this);
			cont.setUser(user);
			cont.populatePage();
			*/
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	My work
	public void showProfileView()
	{
		ProfileController profileController = new ProfileController();
		profileController.setUser(new User("Individual"));
		profileController.setVTM(this);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/ProfileView.fxml"));
		
		try
		{
			Node view = loader.load();
			
		}
		
		
	}
	*/
}
