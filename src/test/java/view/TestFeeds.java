package view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import com.sun.javafx.css.StyleManager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.JobPost;
import models.Name;
import models.ServerHandler;
import models.User;
import models.UserPost;
import models.ViewTransitionModel;
import views.LoginController;

@ExtendWith(ApplicationExtension.class)
public class TestFeeds
{
	
		User user;
		ArrayList<UserPost> testPosts;
		ArrayList<JobPost> testJobPosts;

		
		@Start
		public void start(Stage stage) throws Exception
		{
			testPosts = new ArrayList<UserPost>();
			testJobPosts = new ArrayList<JobPost>();
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
			
			User user3 = new User("Individual");
			user2.followingToggle(user3);
			
			
			user.editorToggle(user2.getUID());
			user2.followingToggle(user);
			user.followingToggle(user2);
			//ServerHandler.INSTANCE.putUser(user);
			ServerHandler.INSTANCE.putUser(user2);
			ServerHandler.INSTANCE.putUser(user);
			ServerHandler.INSTANCE.putUser(user3);
			
			
			System.out.println(user2.getLC().getList("Following").size());
			user2.createUserPost("Most people don't appreciate the beauty of life", true);
			user2.createJobPost("Secretary", "I need someone to implement my genius ideas! That could be you!");
			testJobPosts.add(user.createJobPost("Software Developers!", "Please please please please apply>"));
			System.out.println(user2.getLC().getList("Following").size());

			
			testPosts.add(user.createUserPost("Nexus is really the future of employment!", true));
			testPosts.add(user.createUserPost("The power of social media remains underappreciated.", true));
			user.likeUnlikePost(testPosts.get(1));
			testPosts.add(user.createUserPost("What people need to understand about tech jobs is "
					+ "that they...", true));
			testPosts.add(user.createUserPost("I should have been a sailer", true));
			System.out.println(user2.getLC().getList("Following").size());
			testPosts.add(user3.createUserPost("I'm on Nexus too!", true));

			
			
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
		
		@Test
		@Order(1)
		public void test(FxRobot robot)
		{
			robot.clickOn("#usernameField");
			//System.out.println(user2.getLC().getList("Following").size());

			
		
			robot.write("downdog");
			robot.clickOn("#passwordField");
			robot.write("4321");
			robot.clickOn("#loginButton");

			int i = 0;
			
			//Test that the feed of posts from those you follow works
			robot.clickOn("#sidebarHomeButton");
			
			
			assertEquals(robot.lookup("#PostLikeCount" + i).queryAs(Text.class).getText(), 
					String.valueOf(testPosts.get(i).getLikes()));
			assertEquals(robot.lookup("#postContent" + i).queryAs(Text.class).getText(), 
					testPosts.get(i).getContent());
			assertEquals(robot.lookup("#postCreationDate" + i).queryAs(Text.class).getText(), 
					testPosts.get(i).getCreationDateTime());
			assertEquals(robot.lookup("#postCommentCount" + i).queryAs(Text.class).getText(), 
					String.valueOf(testPosts.get(i).getLC().getListLength("Comments")));
			assertEquals(robot.lookup("#postViewName" + i).queryAs(Label.class).getText(), 
					user.getDisplayName().getName());
			
			i++;
			assertEquals(robot.lookup("#PostLikeCount" + i).queryAs(Text.class).getText(), 
					String.valueOf(testPosts.get(i).getLikes()));
			assertEquals(robot.lookup("#postContent" + i).queryAs(Text.class).getText(), 
					testPosts.get(i).getContent());
			assertEquals(robot.lookup("#postCreationDate" + i).queryAs(Text.class).getText(), 
					testPosts.get(i).getCreationDateTime());
			assertEquals(robot.lookup("#postCommentCount" + i).queryAs(Text.class).getText(), 
					String.valueOf(testPosts.get(i).getLC().getListLength("Comments")));
			assertEquals(robot.lookup("#postViewName" + i).queryAs(Label.class).getText(), 
					user.getDisplayName().getName());
			
			robot.clickOn("#seeUserProfileButton2");
			robot.clickOn("#profileEditUserButton");
			robot.clickOn("#displayNameField");
			robot.write("Billy");
			
			robot.clickOn("#acceptProfileEditChangesButton");
			robot.clickOn("#sidebarHomeButton");
			robot.clickOn("#seeUserProfileButton2");
			user = user.updatedUser();
			assertEquals(robot.lookup("#profileName").queryAs(Label.class).getText(),
					user.getDisplayName().getName());
			
			robot.clickOn("#sidebarHomeButton");
			

			robot.clickOn("#seeUserProfileButton" + 4);

			//Confirm that we can't edit this user
			assertThrows(Exception.class, () -> robot.clickOn("#profileEditUserButton"));
			
			i = 0;
			robot.clickOn("#showJobFeed");
			assertEquals(robot.lookup("#JobPostLikeCount" + i).queryAs(Text.class).getText(), 
					String.valueOf(testJobPosts.get(i).getLikes()));
			assertEquals(robot.lookup("#JobPostContent" + i).queryAs(Text.class).getText(), 
					testJobPosts.get(i).getContent());
			assertEquals(robot.lookup("#JobPostCreationDate" + i).queryAs(Text.class).getText(), 
					testJobPosts.get(i).getCreationDateTime());
			assertEquals(robot.lookup("#JobPostCommentCount" + i).queryAs(Text.class).getText(), 
					String.valueOf(testJobPosts.get(i).getLC().getListLength("Comments")));
			

			
			
		}
}
