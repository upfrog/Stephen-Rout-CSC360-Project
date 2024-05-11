package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.ServerHandler;
import models.User;
import models.ViewTransitionModelInterface;

public class FriendBoxController
{
	ViewTransitionModelInterface vtm;
	String UID;
	
	//String postCreatorUID;
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	public String[] setData(User user, int i)
	{
		this.UID = user.getUID();
		friendName.setText(user.getDisplayName().getName());
		friendName.setId(friendName.getId() + i);
		
		return new String[1];

	}
	

    @FXML
    private Label friendName;

    @FXML
    private Button unfollowFriendButton;

    @FXML
    void unfollowFriend(ActionEvent event) 
    {
    	User userToUnfollow = ServerHandler.INSTANCE.getUser(UID);
    	User thisUser = vtm.getUser();
    	thisUser.followerToggle(userToUnfollow);
    	while (thisUser.getLC().getList("Following").contains(UID))
    	{
    		thisUser.getLC().removeLink("Following", UID);
    	}
    	ServerHandler.INSTANCE.putUser(thisUser);
    	while (userToUnfollow.getLC().getList("Followers").contains(thisUser.getUID()))
    	{
    		userToUnfollow.getLC().removeLink("Followers", thisUser.getUID());
    	}
    	ServerHandler.INSTANCE.putUser(userToUnfollow);
    }

	
}
