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

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
		
		
		
		User user2 = new User("Individual");
		user2.setUserName("downdog");
		user2.setPassword("4321");
		user2.setDisplayName(new Name("Inigo Montoya"));
		user2.setDescription("Leveraging Shareholder Synergy To Revolutiona A New Paradigmatic Revolution.");
		user2.setCurrentRole("Though Leader");
		user2.setWorksAt("Unemployed");
		
		user2.followingToggle(user);
		user.followingToggle(user2);
		//ServerHandler.INSTANCE.putUser(user);
		ServerHandler.INSTANCE.putUser(user2);
		ServerHandler.INSTANCE.putUser(user);
		
		System.out.println(user2.getLC().getList("Following").size());
		user2.createUserPost("Most people don't appreciate the beauty of life", true);
		user2.createJobPost("Secretary", "I need someone to implement my genius ideas! That could be you!");
		user.createJobPost("Software Developers!", "Please please please please apply>");
		System.out.println(user2.getLC().getList("Following").size());

		
		testPosts.add(user.createUserPost("Nexus is really the future of employment!", true));
		testPosts.add(user.createUserPost("The power of social media remains underappreciated.", true));
		user.likeUnlikePost(testPosts.get(1));
		testPosts.add(user.createUserPost("What people need to understand about tech jobs is "
				+ "that they...", true));
		System.out.println(user2.getLC().getList("Following").size());

		
		
		//For convenience
		
		
		

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
		System.out.println(user2.getLC().getList("Following").size());


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
		//System.out.println(user2.getLC().getList("Following").size());

		
	
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
		
		robot.write("Technology!");
		
		robot.clickOn("#submitPostButton");
		robot.clickOn("#sidebarProfileButton");
		
		i++;
		assertEquals(robot.lookup("#PostLikeCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(0));
		assertEquals(robot.lookup("#postContent" + i).queryAs(Text.class).getText(), 
				"Technology!");
		assertEquals(robot.lookup("#postCommentCount" + i).queryAs(Text.class).getText(), 
				String.valueOf(0));
		
		
		//Test editing the user
		robot.clickOn("#profileEditUserButton");
		robot.clickOn("#displayNameField");
		robot.write("Billy");
		
		robot.clickOn("#currentJobTitleField");
		robot.write("Monk");
		
		robot.clickOn("#currentCompanyField");
		robot.write("One of the churhces");
		
		robot.clickOn("#skillArea");
		robot.write("python java management math");
		
		robot.clickOn("#profileDescriptionArea");
		robot.write("I got tired of computers.");
		
		robot.clickOn("#acceptProfileEditChangesButton");
		robot.sleep(1000);
		user = user.updatedUser();
		assertEquals(robot.lookup("#profileDescription").queryAs(Text.class).getText(),
				user.getDescription());
		assertEquals(robot.lookup("#profileName").queryAs(Label.class).getText(),
				user.getDisplayName().getName());
		assertEquals(robot.lookup("#profileTitleAndCompany").queryAs(Label.class).getText(),
				user.getCurrentRole() + "@" + user.getWorksAt());
		assertEquals(user.getLC().getList("Skills").size(), 4);
		assertEquals(user.getLC().getList("Skills").contains("python"), true);
		assertEquals(user.getLC().getList("Skills").contains("java"), true);
		assertEquals(user.getLC().getList("Skills").contains("management"), true);
		assertEquals(user.getLC().getList("Skills").contains("math"), true);

		
		//Check post feed


		
	}
	
	/*
	@Test
	public void testProfile(FxRobot robot)
	{

		
		assertEquals(robot.lookup("#postContent").queryAs(Label.class).getText()
				.equals("Nexus is really the future of employment!"), true);
		
	}
	*/
	
	
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
