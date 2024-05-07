package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartNexus extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		
		Parent root = FXMLLoader.load(getClass().getResource("../views/LoginView.fxml"));
		Scene s = new Scene(root);
		s.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());

		
		primaryStage.setTitle("Nexus");
		primaryStage.setScene(s);
		primaryStage.show();
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
