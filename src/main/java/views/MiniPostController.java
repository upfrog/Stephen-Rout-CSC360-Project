package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import models.MiniPost;

public class MiniPostController
{
	public void setData(MiniPost post)
	{
		PostLikeCount.setText(String.valueOf(post.getLikes()));
		postContent.setText(post.getContent());
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
