package view;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

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
import javafx.stage.Stage;
import models.ServerHandler;
import models.User;
import models.ViewTransitionModel;
import views.LoginController;

@ExtendWith(ApplicationExtension.class)
public class LoginTest
{


	@Start
	public void start(Stage stage) throws Exception
	{
		ServerHandler.INSTANCE.clearServer();
		ServerHandler.INSTANCE.configureServer();
		User user = new User("Individual");
		user.setUserName("upfrog");
		user.setPassword("1234");
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
	
	
	@Test
	public void testLogin(FxRobot robot)
	{
		//warningMessage
		assertEquals(robot.lookup("#warningMessage").queryAs(Label.class).getText().equals(""), true);
		assertEquals(robot.lookup("#warningMessage").queryAs(Label.class).getText().equals("f"), false);
		
		//Only username
		robot.clickOn("#usernameField");
		robot.write("upfrog");

		robot.clickOn("#loginButton");
		assertEquals(robot.lookup("#warningMessage").queryAs(Label.class).getText(), 
				"Username or password is wrong");
		
		robot.clickOn("#usernameField");
		robot.eraseText(8);
		
		//Only password
		robot.clickOn("#passwordField");
		robot.write("1234");

		robot.clickOn("#loginButton");
		assertEquals(robot.lookup("#warningMessage").queryAs(Label.class).getText(), 
				"Username or password is wrong");
		robot.eraseText(8);
		
		//Both, wrong username
		robot.clickOn("#usernameField");
		robot.write("upfrogg");

		robot.clickOn("#loginButton");
		robot.write("1234");

		assertEquals(robot.lookup("#warningMessage").queryAs(Label.class).getText(), 
				"Username or password is wrong");
		
		//Both, wrong password
		robot.clickOn("#usernameField");
		robot.eraseText(8);
		robot.write("upfrog");

		robot.clickOn("#loginButton");
		robot.eraseText(8);
		robot.write("1234");
		/*
		assertEquals(robot.lookup("#warningMessage").queryAs(Label.class).getText(), 
				"Username or password is wrong");
		
		//correct
		robot.clickOn("#usernameField");
		robot.eraseText(8);
		robot.write("upfrog");

		robot.clickOn("#passwordField");
		robot.eraseText(8);
		robot.write("1234");
		assertDoesNotThrow(() -> robot.clickOn("#loginButton"));
		*/
	}


}
