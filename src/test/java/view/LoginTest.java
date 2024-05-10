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
	}
	
	@Test
	public void testCreateUser()
	{
		
	}
	/*
	
	private void enterNum1(FxRobot robot, String amt)
	{
		robot.clickOn("#num1Field");
		robot.write(amt);
	}
	
	private void enterNum2(FxRobot robot, String amt)
	{
		robot.clickOn("#num2Field");
		robot.write(amt);
	}
	
	private void checkResult(FxRobot robot, String bal)
	{
		assertEquals(robot.lookup("#resultLabel").queryAs(Label.class).getText(), (bal));
	
	}
	
	private void checkAddNum(FxRobot robot, String change1, String change2, String after)
	{
		enterNum1(robot, change1);
		enterNum2(robot, change2);
		robot.clickOn("#addButton");
		checkResult(robot, after);
		checkHasOp(robot, change1, change2, after, " + ");
	}
	
	private void checkSubtractNum(FxRobot robot, String change1, String change2, String after)
	{
		enterNum1(robot, change1);
		enterNum2(robot, change2);
		robot.clickOn("#subtractButton");
		checkResult(robot, after);
		assertEquals(checkHasOp(robot, change1, change2, after, " - "), true);
	}
	
	private void checkMultiplyNum(FxRobot robot, String change1, String change2, String after)
	{
		enterNum1(robot, change1);
		enterNum2(robot, change2);
		robot.clickOn("#multiplyButton");
		checkResult(robot, after);
		
		assertEquals(checkHasOp(robot, change1, change2, after, " * "), true);
	}
	
	private void checkDivideNum(FxRobot robot, String change1, String change2, String after)
	{
		enterNum1(robot, change1);
		enterNum2(robot, change2);
		robot.clickOn("#divideButton");
		checkResult(robot, after);
		checkHasOp(robot, change1, change2, after, " / ");
	}
	
	
	@Test
	public void testAdd(FxRobot robot)
	{
		testOperationListSize(robot, 0);
		checkAddNum(robot, "0.15", "0.15", "0.3");
		checkAddNum(robot, "0.30", "-0.45", "-0.15");
		checkAddNum(robot, "3", "1.07", "4.07");
		checkAddNum(robot, "44", "333", "377");
		testOperationListSize(robot, 4);
	}
	
	
	@Test
	public void testSubtract(FxRobot robot)
	{
		testOperationListSize(robot, 0);
		checkSubtractNum(robot, "5", "2", "3");
		checkSubtractNum(robot, "5", "9", "-4");
		checkSubtractNum(robot, "0.25", "0.07", "0.18");
		testOperationListSize(robot, 3);
	}
	
	@Test
	public void testMultiply(FxRobot robot)
	{
		testOperationListSize(robot, 0);
		checkMultiplyNum(robot, "2", "3", "6");
		checkMultiplyNum(robot, "0.16", "0.5", "0.08");
		checkMultiplyNum(robot, "-3", "4", "-12"); //causes exception, but it still works
		checkMultiplyNum(robot, "-3", "-4", "12");
		testOperationListSize(robot, 4);
	}
	
	@Test
	public void testDivide(FxRobot robot)
	{
		testOperationListSize(robot, 0);
		checkDivideNum(robot, "12", "4", "3");
		checkDivideNum(robot, "-12", "4", "-3");
		checkDivideNum(robot, "4", "0.5", "8");
		checkDivideNum(robot, "0.5", "0.5", "1");
		checkDivideNum(robot, "-3", "-3", "1");
		testOperationListSize(robot, 5);
	}
	*/
	/*
	 * This is sort of sloppy, but in the end, the type conversions are so much of a 
	 * pain that this is easiest.
	 */
	/*
	public boolean checkHasOp(FxRobot robot, String change1, String change2, String after, String op)
	{
		ListView<Operation> ops = getOperations(robot);
	
		//The number conversions in these tests will be the death of me.
		Operation newOperation = new Operation(Double.valueOf(change1),
				op,
				Double.valueOf(change2),
				Double.valueOf(after));
		
		for (Operation opperation : ops.getItems())
		{
			if (newOperation.equals(opperation))
			{
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	ListView<Operation> getOperations(FxRobot robot)
	{
		return ((ListView<Operation>) robot.lookup("#operationHistoryTable")
				.queryAll().iterator().next());
	
	}
	
	public void testOperationListSize(FxRobot robot, int intendedSize)
	{
		ListView<Operation> ops = getOperations(robot);
		
		assertEquals(ops.getItems().size(), intendedSize);
	}
	
	*/


}
