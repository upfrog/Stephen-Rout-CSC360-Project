package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import models.ServerHandler;
import models.User;
import models.UserPost;
import models.ViewTransitionModelInterface;

public class UserPostController
{
	ViewTransitionModelInterface vtm;
	UserPost post;
	String postCreatorUID;
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	public String[] setData(UserPost post, int i)
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
		
		seeUserProfileButton.setId(seeUserProfileButton.getId() + i);
		
		User user = ServerHandler.INSTANCE.getUser(post.getCreatorUID()); 
		this.postCreatorUID = post.getCreatorUID();
		
		postViewJob.setText(user.getCurrentRole() + "@" + user.getWorksAt());
		postViewJob.setId(postViewJob.getId() + i);
		
		postViewName.setText(user.getDisplayName().getName());
		postViewName.setId(postViewName.getId() + i);

		
		

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
    private Button seeUserProfileButton;
    
    @FXML
    private Label postViewJob;

    @FXML
    private Label postViewName;

    @FXML
    void likePost(ActionEvent event) {
    	
    }

    @FXML
    void onClickSeeComments(ActionEvent event) {

    }
    
    @FXML
    void seeUserProfile(ActionEvent event) {
    	User user = ServerHandler.INSTANCE.getUser(postCreatorUID);
    	vtm.showForeignProfile(user);
    }
}
