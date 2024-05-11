package views;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.ServerHandler;
import models.User;
import models.ViewTransitionModelInterface;

public class EditProfileController
{
	ViewTransitionModelInterface vtm;
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	public ViewTransitionModelInterface getVTM()
	{
		return this.vtm;
	}
	
    @FXML
    private Button acceptProfileEditChangesButton;

    @FXML
    private TextField addEditorField;

    @FXML
    private TextField currentCompanyField;

    @FXML
    private TextField currentJobTitleField;

    @FXML
    private TextField displayNameField;

    @FXML
    private ListView<String> editorList;

    @FXML
    private TextArea profileDescriptionArea;

    @FXML
    private RadioButton publicityRadioButton;

    @FXML
    private TextField removeEditorField;

    @FXML
    private Button submitEditorChangeButton;
    
    @FXML
    private Button cancelProfileEdit;

    public void populateExistingData()
    {
    	User user = vtm.getUser();
    	currentCompanyField.setText(user.getWorksAt());
    	currentJobTitleField.setText(user.getCurrentRole());
    	profileDescriptionArea.setText(user.getDescription());
    	displayNameField.setText(user.getDisplayName().getName());
    	publicityRadioButton.setSelected(user.getIsPublic());
    	
    	ArrayList<User> editors = new ArrayList<User>();
    			
    	
    	
    	for (String editorUID : user.getEditorUIDs())
    	{
    		User editor = ServerHandler.INSTANCE.getUser(editorUID);
    		editorList.getItems().add(editor.getDisplayName().getName());
    		System.out.println(editor.getDisplayName().getName());
    	}
    }
    
    @FXML
    void acceptChanges(ActionEvent event) 
    {
    	vtm.showProfileView();
    }

    @FXML
    void submitEditorChange(ActionEvent event) {

    }
    
    @FXML
    void cancelProfileEdit(ActionEvent event)
    {
    	vtm.showProfileView();
    }

}