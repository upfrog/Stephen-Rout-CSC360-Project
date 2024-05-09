package views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import models.UserPost;

public class UserPostCellView extends ListCell<UserPost>
{
	PostController cont;
	Node node;
	
	public UserPostCell(ListView<UserPost> view)
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../views/PostView.fxml"));
		
		try {
			node = loader.load();
			
			cont = loader.getController();
			cont.setModel(this);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		this.setGraphic(node);
	}
	
	protected void updatePost(UserPost post)
	{
		if (empty || item == null)
		{
			setGraphc(null);
			setText(null);
		}
		else
		{
			cont.setModel(this);
			setText(post.getContent());
		}
	}
}
