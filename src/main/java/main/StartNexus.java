package main;

import com.sun.javafx.css.StyleManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.ViewTransitionModel;
import views.LoginController;

public class StartNexus extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		
		
		
		//Start by making a model
		
		//Create an object to load FXML files
		FXMLLoader loader = new FXMLLoader();
		
		//Set the file to be loaded
		loader.setLocation((getClass().getResource("../views/LoginView.fxml")));
		
		//Grab the entire view from the loader
		BorderPane view = loader.load();
		
		//loader.getController() checks the associated fxml file for a controller.
		//Because we know what the page is, we know what type of controller we need.
		LoginController controller = loader.getController();
		
		//Put our view and model into a ViewTransitionalModel.
		ViewTransitionModel vtm = new ViewTransitionModel(view); 
		
		
		//Why do we need to set VTM as the model here, but in the VTM class we 
		//set the model itself as the model?
		controller.setVTM(vtm);
		//vtm.showLoginView();
		
		
		//Page 257 for more CSS tips
		Scene s = new Scene(view);
		//s.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
		setUserAgentStylesheet(STYLESHEET_MODENA);
		StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("../css/styles.css").toString());
		primaryStage.setScene(s);
		primaryStage.show();
		
		
		/*
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/LoginView.fxml"));
		//loader.setLocation(getClass().getResource("/main.fxml"));

		 //URL cssURL = getClass().getResource("/src/main/resources/css/styles.css");
		 //scene.getStylesheets().add(cssURL.toExternalForm());
		
		BorderPane view = loader.load();
		
		
		
		FXMLLoader loader2 = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/LoginView.fxml"));
		LoginController cont = loader2.getController();
		ViewTransitionModel vm = new ViewTransitionModel(view);
		cont.setVTM(vm);
		//vm.showLogInView();
		Parent root = FXMLLoader.load(getClass().getResource("../views/LoginView.fxml"));

		Scene s = new Scene(root);
		s.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

		primaryStage.setScene(s);
		
		primaryStage.show();
		
		*/
		
		/*
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Views/LoginView.fxml"));
		BorderPane view = loader.load();
		//loader.setLocation(getClass().getResource("/main.fxml"));

		 //URL cssURL = getClass().getResource("/src/main/resources/css/styles.css");
		 //scene.getStylesheets().add(cssURL.toExternalForm());
		
		
		
		System.out.println("Login clicked");
		LoginController cont = loader.getController();
		ViewTransitionModel vtm = new ViewTransitionModel(view);
		cont.setVTM(vtm);
		vtm.showLoginView();
		
		Scene s = new Scene(view);
		s.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

		primaryStage.setScene(s);
		
		primaryStage.show();
		*/
		
		
/*
		//Trying to do it my way: 
		
		BorderPane view = FXMLLoader.load(getClass().getResource("../views/LoginView.fxml"));
		ViewTransitionModel vtm = new ViewTransitionModel(view);
		
		Scene s = new Scene(view);
		
		
		s.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
		FXMLLoader loader2 = new FXMLLoader();
		loader2.setLocation(getClass().getResource("../views/LoginView.fxml"));
		LoginController cont = loader2.getController();
		
		cont.setVTM(new ViewTransitionModel(view));
		
		primaryStage.setTitle("Nexus");
		primaryStage.setScene(s);
		primaryStage.show();
		*/
		
		/*
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/LoginView.fxml"));

		Parent root = loader.load();
		*/
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
