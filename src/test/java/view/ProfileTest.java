package view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.ScrollPane;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Order;
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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
		testPosts = new ArrayList<UserPost>();
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		//I'd like to stick this in a seperate method,but it breaks when I do :(
		user = new User("Individual");
		user.setUserName("upfrog");
		user.setPassword("1234");
		user.setDisplayName(new Name("Brian Rout"));
		user.setDescription("I'm pretty okay at my job I guess.");
		user.setCurrentRole("SDE XVI");
		user.setWorksAt("Yamaxun");
		testPosts.add(user.createUserPost("Nexus is really the future of employment!", true));
		testPosts.add(user.createUserPost("The power of social media remains underappreciated.", true));
		user.likeUnlikePost(testPosts.get(1));
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
	@Order(1)
	public void setup(FxRobot robot)
	{
		robot.clickOn("#usernameField");
		robot.write("upfrog");
		robot.clickOn("#passwordField");
		robot.write("1234");
		robot.clickOn("#loginButton");
		
		
		int i = 0;
		//This seems to stop working if I move it into another method. Onwards!
		assertEquals(robot.lookup("#PostLikeCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(testPosts.get(i).getLikes()));
		assertEquals(robot.lookup("#postContent" + i).queryAs(Text.class).getText(), 
				testPosts.get(i).getContent());
		assertEquals(robot.lookup("#postCreationDate" + i).queryAs(Text.class).getText(), 
				testPosts.get(i).getCreationDateTime());
		assertEquals(robot.lookup("#postCommentCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(testPosts.get(i).getLC().getListLength("Comments")));
		
		i++; //Everything except the comment count is different
		assertEquals(robot.lookup("#PostLikeCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(testPosts.get(i).getLikes()));
		assertEquals(robot.lookup("#postContent" + i).queryAs(Text.class).getText(), 
				testPosts.get(i).getContent());
		assertEquals(robot.lookup("#postCreationDate" + i).queryAs(Text.class).getText(), 
				testPosts.get(i).getCreationDateTime());
		assertEquals(robot.lookup("#postCommentCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(testPosts.get(i).getLC().getListLength("Comments")));
		i++;
		assertEquals(robot.lookup("#PostLikeCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(testPosts.get(i).getLikes()));
		assertEquals(robot.lookup("#postContent" + i).queryAs(Text.class).getText(), 
				testPosts.get(i).getContent());
		assertEquals(robot.lookup("#postCreationDate" + i).queryAs(Text.class).getText(), 
				testPosts.get(i).getCreationDateTime());
		assertEquals(robot.lookup("#postCommentCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(testPosts.get(i).getLC().getListLength("Comments")));
		
		
		//Test profile values
		assertEquals(robot.lookup("#profileDescription").queryAs(Text.class).getText(),
				user.getDescription());
		assertEquals(robot.lookup("#profileName").queryAs(Label.class).getText(),
				user.getDisplayName().getName());
		assertEquals(robot.lookup("#profileTitleAndCompany").queryAs(Label.class).getText(),
				user.getCurrentRole() + "@" + user.getWorksAt());
		
		//Test making a new post
		robot.clickOn("#sidebarMakePostButton");
		robot.clickOn("#postBody");
		
		robot.write("With new technology, AI generated content will take over most Social Media.");
		
		robot.clickOn("#submitPostButton");
		robot.clickOn("#sidebarProfileButton");
		
		i++;
		assertEquals(robot.lookup("#PostLikeCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(0));
		assertEquals(robot.lookup("#postContent" + i).queryAs(Text.class).getText(), 
				"With new technology, AI generated content will take over most Social Media.");
		assertEquals(robot.lookup("#postCommentCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(0));
		robot.sleep(10000);
	
	}
	
	@Test
	public void testProfile(FxRobot robot)
	{

		/*
		assertEquals(robot.lookup("#postContent").queryAs(Label.class).getText()
				.equals("Nexus is really the future of employment!"), true);
		*/
	}
	
	
	/*
	@Test
	public void testPosts(FxRobot robot)
	{
		//GridPane grid  = ((ScrollPane)feed.getSelectionModel().getSelectedItem().getContent()).getContent();
		ObservableList<Node> posts = feedGrid.getChildren();
		BorderPane bp = ((BorderPane)posts.get(0));
		ObservableList<Node> level2 = bp.get(0);
		VBox box = ((VBox) level2.get(0).getChildren()));
	}
	*/
}
