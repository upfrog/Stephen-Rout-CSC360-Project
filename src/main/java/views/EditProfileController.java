package views;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.Name;
import models.ServerHandler;
import models.User;
import models.ViewTransitionModelInterface;


public class EditProfileController
{
	ViewTransitionModelInterface vtm;
	public ViewTransitionModelInterface returnVTM;
	
	
	public EditProfileController(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	public EditProfileController()
	{}
	
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
    private TextArea skillArea;

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
    	skillArea.setText(getSkillString(user));
    	//ArrayList<User> editors = new ArrayList<User>();
    			
    	
    	
    	for (String editorUID : user.getEditorUIDs())
    	{
    		User editor = ServerHandler.INSTANCE.getUser(editorUID);
    		String entry = editor.getDisplayName().getName() + " - " + editor.getUID();
    		editorList.getItems().add(entry);
    		//System.out.println(editor.getDisplayName().getName());
    	}
    }
    
    @FXML
    void acceptChanges(ActionEvent event) 
    {
    	User user = vtm.getUser();
    	user.setWorksAt(currentCompanyField.getText());
    	user.setCurrentRole(currentJobTitleField.getText());
    	user.setDescription(profileDescriptionArea.getText());
    	user.setDisplayName(new Name(displayNameField.getText()));
    	user.setIsPublic(!publicityRadioButton.isSelected());
    	
    	
    	ObservableList<String> editors = editorList.getItems();
    	ArrayList<String> editorUIDs = new ArrayList<String>();
    	
    	for (String editor : editors)
    	{
    		editorUIDs.add(editor.split(" - ", 0)[1]);
    		
    	}
    	user.setEditorUID(editorUIDs);
    	

    	ArrayList<String> skills =  
    			new ArrayList<String>(Arrays.asList(skillArea.getText().split(" ", 0)));
    	
    	user.getLC().setList("Skills", skills);
    	ServerHandler.INSTANCE.putUser(user);
    	if (this.returnVTM != null)
    	{
    		this.returnVTM.showProfileView();
    	}
    	else
    	{
    		this.vtm.showProfileView();
    	}
    }

    @FXML
    void submitEditorAdd(ActionEvent event) {
    	
    	if (addEditorField.getText() != null || !addEditorField.getText().equals(""))
    	{
    		User newEditor = ServerHandler.INSTANCE.getUser(addEditorField.getText());
        	editorList.getItems().add(newEditor.getDisplayName().getName() + " - " + newEditor.getUID());
    	}
    	
    	

    	
    	/*
    	  for (String editorUID : user.getEditorUIDs())
    	{
    		User editor = ServerHandler.INSTANCE.getUser(editorUID);
    		String entry = editor.getDisplayName().getName() + " - " + editor.getUID();
    		editorList.getItems().add(entry);
    		//System.out.println(editor.getDisplayName().getName());
    	}
    	
    	
    	    		try
    		{
    			editors.add(addEditorField.getText());
    			user.setEditorUID(editors);
    			
    		} catch (Exception e)
    		{
    			e.printStackTrace();
    		}
    		
    		
    		    		try
    		{
    			editors.remove(addEditorField.getText());
    			user.setEditorUID(editors);
    			
    		} catch (Exception e)
    		{
    			e.printStackTrace();
    		}
    		\		User user = vtm.getUser();
		ArrayList<String> editors = user.getEditorUIDs();
    	
    	 */
    	
    	
    	
    	

		
    	
    	
    	
    }
    
    private String getSkillString(User user)
    {
    	ArrayList<String> skillList = new ArrayList<String>(user.getLC().getList("Skills"));
    	String result = "";
    	
    	for (int i = 0; i < skillList.size(); i++)
    	{
    		result = result + skillList.get(i) + " ";
    	}
    	
    	return result;
    }
    
    @FXML
    void submitEditorDelete(ActionEvent event) {
    	if (removeEditorField.getText() != null || !removeEditorField.getText().equals(""))
    	{
    		User e = ServerHandler.INSTANCE.getUser(removeEditorField.getText());
        	editorList.getItems().remove(e.getDisplayName().getName() + " - " + e.getUID());
    	}
    }
    
    
    @FXML
    void cancelProfileEdit(ActionEvent event)
    {
    	System.out.println(this.vtm);
    	if (this.returnVTM != null)
    	{
    		this.returnVTM.showProfileView();
    	}
    	else
    	{
    		this.vtm.showProfileView();
    	}
    }

}