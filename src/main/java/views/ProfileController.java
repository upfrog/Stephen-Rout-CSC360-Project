package views;

import models.User;
import models.ViewTransitionModelInterface;

public class ProfileController
{

	ViewTransitionModelInterface vtm;
	User user;
	
	public ProfileController()
	{
		
	}
	
	public void setVTM(ViewTransitionModelInterface vtm)
	{
		this.vtm = vtm;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public void populatePage()
	{
		System.out.println("populating!");
	}
	
}



