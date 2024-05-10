package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import models.UserPost;

public class UserPostController
{
	public String[] setData(UserPost post, int i)
	{
		postContent.setText(post.getContent());
		postContent.setId(postContent.getId() + i);
		
		postCreationDate.setText(post.getCreationDateTime());
		postCreationDate.setId(postCreationDate.getId() + i);
		
		PostLikeCount.setText(String.valueOf(post.getLikes()));
		PostLikeCount.setId(PostLikeCount.getId() + i);
		
		postLike.setId(postLike.getId() + i);
		
		postCommentCount.setText(String.valueOf(post.getLC().getList("Comments").size()));
		postCommentCount.setId(postCommentCount.getId() + i);
		
		PostSeeCommentsButton.setId(PostSeeCommentsButton.getId() + i);

		

		String[] ids = {"ff"};
		System.out.println(PostLikeCount.getId());
		System.out.println(postContent.getId());
		System.out.println(postCreationDate.getId());
		System.out.println(postCommentCount.getId());

		return ids;
	}
	@FXML
    private Text PostLikeCount;

    @FXML
    private Button PostSeeCommentsButton;

    @FXML
    private Text postCommentCount;

    @FXML
    private Label postContent;

    @FXML
    private Text postCreationDate;

    @FXML
    private Button postLike;

    @FXML
    void likePost(ActionEvent event) {

    }

    @FXML
    void onClickSeeComments(ActionEvent event) {

    }
}
