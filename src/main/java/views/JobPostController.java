package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import models.JobPost;
import models.ServerHandler;
import models.User;
import models.UserPost;
import models.ViewTransitionModelInterface;

public class JobPostController
{
	ViewTransitionModelInterface vtm;
	JobPost post;
	String postCreatorUID;
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	public String[] setData(JobPost post, int i)
	{
		JobPostContent.setText(post.getContent());
		JobPostContent.setId(JobPostContent.getId() + i);
		//postContent.setWrappingWidth();
		
		JobPostCreationDate.setText(post.getCreationDateTime());
		JobPostCreationDate.setId(JobPostCreationDate.getId() + i);
		
		JobPostLikeCount.setText(String.valueOf(post.getLikes()));
		JobPostLikeCount.setId(JobPostLikeCount.getId() + i);
		
		JobPostLike.setId(JobPostLike.getId() + i);
		
		JobPostCommentCount.setText(String.valueOf(post.getLC().getList("Comments").size()));
		JobPostCommentCount.setId(JobPostCommentCount.getId() + i);
		
		JobPostSeeCommentsButton.setId(JobPostSeeCommentsButton.getId() + i);
		
		User user = ServerHandler.INSTANCE.getUser(post.getCreatorUID()); 
		this.postCreatorUID = post.getCreatorUID();
		
		JobPostCompanyName.setText(user.getDisplayName().getName());
		JobPostCompanyName.setId(JobPostCompanyName.getId() + i);
		
		JobPostTitle.setText(post.getPostTitle());
		JobPostTitle.setId(JobPostTitle.getId() + i);

		
		

		String[] ids = {"ff"};
		System.out.println(JobPostLikeCount.getId());
		System.out.println(JobPostContent.getId());
		System.out.println(JobPostCreationDate.getId());
		System.out.println(JobPostCommentCount.getId());

		return ids;
	}
	@FXML
    private Text JobPostLikeCount;

    @FXML
    private Button JobPostSeeCommentsButton;

    @FXML
    private Text JobPostCommentCount;

    @FXML
    private Text JobPostContent;

    @FXML
    private Text JobPostCreationDate;

    @FXML
    private Button JobPostLike;
    
    @FXML
    private Button JobPostSeeUserProfileButton;
    
    @FXML
    private Label JobPostCompanyName;

    @FXML
    private Label JobPostTitle;

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
    
    @FXML
    void applyToJob(ActionEvent event)
    {
    	
    }
}
