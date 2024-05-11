package models;

public interface ViewTransitionModelInterface
{
	public void showProfileView();
	public void setUser(User user);
	public User getUser();
	public void showMakePostView();
	public void showEditProfileView();
	public void showFeed();
	//void showProfileView(User user);
}
