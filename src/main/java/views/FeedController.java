package views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import models.MiniPost;

/*
 * Experiment V3 - trying to avoid doing all that list-view fodlerall
 */
public class FeedController
{
	private GridPane postGrid;
	private List<MiniPost> posts;
	
	public void initialize()
	{
		posts = fetchPosts();
		
		for (int i = 0; i < posts.size(); i++)
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("../views/UserPostCellView.fxml"));
			try
			{
				Node postBox = loader.load();
				MiniPostController miniController = loader.getController();
				miniController.setData(posts.get(i));
				
				postGrid.add(postBox, 0, i);
				
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private List<MiniPost> fetchPosts()
	{
		ArrayList<MiniPost> posts = new ArrayList<MiniPost>();
		
		posts.add(new MiniPost("I'm having an amazing time on Nexus!"));
		posts.add(new MiniPost("Lorum ipsum"));
		posts.add(new MiniPost("It is a truth universally acknowledge that a single man in possession of a good fortune must be in want of a wife."));
		posts.add(new MiniPost("Anyone hiring? I need money to pay my rent!"));
		posts.add(new MiniPost("Step right up for induction into the cult of the Eleysian mysteries!"));
		posts.add(new MiniPost("I'm having an amazing time on Nexus!"));
		posts.add(new MiniPost("I'm having an amazing time on Nexus!"));
		posts.add(new MiniPost("Lorum ipsum"));
		posts.add(new MiniPost("It is a truth universally acknowledge that a single man in want of"));
		posts.add(new MiniPost("Anyone hiring? I need money to pay my rent!"));
		posts.add(new MiniPost("Step right up for induction into the cult of the Eleysian mysteries!"));
		posts.add(new MiniPost("'It is a truth universally acknowledged, that a single man in possession of a good fortune, must be in want of a wife'.\r\n"
				+ "\r\n"
				+ "No-one in 1813 who read that opening sentence of Jane Austen's second novel would have imagined that it was destined to become one of the most famous first lines in literary history.\r\n"
				+ "\r\n"
				+ " \r\n"
				+ "\r\n"
				+ "This first edition featured in our display '200 years of \"Pride and Prejudice\": From Austen to zombies', which , which ran at the National Library of Scotland from 10 July to 15 September 2013."));
		
		return posts;
	}
}
