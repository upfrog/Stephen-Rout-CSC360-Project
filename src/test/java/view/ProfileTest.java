package view;

import java.awt.ScrollPane;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.sun.javafx.css.StyleManager;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.MiniPost;
import models.Name;
import models.ServerHandler;
import models.User;
import models.UserPost;
import models.ViewTransitionModel;
import views.LoginController;

@ExtendWith(ApplicationExtension.class)
public class ProfileTest
{
	User user;
	ArrayList<UserPost> testPosts;
	@Start
	public void start(Stage stage) throws Exception
	{
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		user = new User("Individual");
		user.setUserName("upfrog");
		user.setPassword("1234");
		user.setDisplayName(new Name("Brian Rout"));
		user.setDescription("I'm pretty okay at my job I guess.");
		user.setCurrentRole("SDE XVI");
		user.setWorksAt("Yamaxun");
		testPosts.add(user.createUserPost("Nexus is really the future of employment!", true));
		testPosts.add(user.createUserPost("The power of social media remains underappreciated.", true));
		testPosts.add(user.createUserPost("What people need to understand about tech jobs is "
				+ "that they...", true));
		ServerHandler.INSTANCE.putUser(user);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/LoginView.fxml"));
		
		try {
			BorderPane view = loader.load();
			LoginController cont = loader.getController();
			ViewTransitionModel vtm = new ViewTransitionModel(view);
			cont.setVTM(vtm);
			
			Scene s = new Scene(view, 1366, 768);
			main.StartNexus.setStylesheet();
			StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("../css/styles.css").toString());

			stage.setScene(s);
			stage.show();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private ScrollPane feed;
	@FXML
	private GridPane feedGrid;
	
	@Test
	public void setup(FxRobot robot)
	{
		robot.clickOn("#usernameField");
		robot.write("upfrog");
		robot.clickOn("#passwordField");
		robot.write("1234");
		robot.clickOn("#loginButton");
	}
	
	@Test
	public void testPosts(FxRobot robot)
	{
		//GridPane grid  = ((ScrollPane)feed.getSelectionModel().getSelectedItem().getContent()).getContent();
		ObservableList<Node> posts = feedGrid.getChildren();
		posts.get(0);
	}
}
