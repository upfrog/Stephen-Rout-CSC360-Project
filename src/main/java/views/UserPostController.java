package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.User;
import models.UserPost;

public class UserPostController
{
	public String[] setData(UserPost post, int i, User user)
	{
		postContent.setText(post.getContent());
		postContent.setId(postContent.getId() + i);
		//postContent.setWrappingWidth();
		
		postCreationDate.setText(post.getCreationDateTime());
		postCreationDate.setId(postCreationDate.getId() + i);
		
		PostLikeCount.setText(String.valueOf(post.getLikes()));
		PostLikeCount.setId(PostLikeCount.getId() + i);
		
		postLike.setId(postLike.getId() + i);
		
		postCommentCount.setText(String.valueOf(post.getLC().getList("Comments").size()));
		postCommentCount.setId(postCommentCount.getId() + i);
		
		PostSeeCommentsButton.setId(PostSeeCommentsButton.getId() + i);
		
		//st.getCreatorUID()
		 //nameplateName.setText(user.getDisplayName().getName());
		//nameplateRoleAndCompany.setText(user.getCurrentRole() + "@" + user.getWorksAt());
		

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
    private Text postContent;

    @FXML
    private Text postCreationDate;

    @FXML
    private Button postLike;

    @FXML
    private Button nameplateFollow;

    @FXML
    private ImageView nameplateImage;

    @FXML
    private Text nameplateName;

    @FXML
    private Text nameplateRoleAndCompany;

    

    @FXML
    void likePost(ActionEvent event) {

    }

    @FXML
    void onClickSeeComments(ActionEvent event) {

    }
}
