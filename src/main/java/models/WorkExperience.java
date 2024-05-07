package models;

public class WorkExperience
{
	private String startDate;
	private String endDate;
	private String companyName;
	private String jobTitle;
	private String description;
	
	/**
	 * @param 	startDate: 		the date the user began working this job.
	 * @param 	endDate: 		the date the user stopped working this job.
	 * @param 	companyName: 	the name of the company.
	 * @param 	jobTitle: 		the user's title at the job.
	 * @param 	description: 	any additional information the user wishes to provide.
	 */
	
	public WorkExperience(String startDate, String endDate, String companyName, String jobTitle,
			String description)
	{
		this.startDate = startDate;
		this.endDate = endDate;
		this.companyName = companyName;
		this.jobTitle = jobTitle;
		this.description = description;
	}

	public WorkExperience() {}
	//All of these are plain, auto-generated, maximally boring setters and getters. 
	protected String getStartDate()
	{
		return startDate;
	}

	
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	
	protected String getEndDate()
	{
		return endDate;
	}

	
	protected void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	
	protected String getCompanyName()
	{
		return companyName;
	}

	
	protected void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	
	protected String getJobTitle()
	{
		return jobTitle;
	}

	
	protected void setJobTitle(String jobTitle)
	{
		this.jobTitle = jobTitle;
	}

	
	protected String getDescription()
	{
		return description;
	}

	
	protected void setDescription(String description)
	{
		this.description = description;
	}
	
	@Override
	public int hashCode()
	{
		int result = 0;
		if (this != null)
		{
			int c = 31;
			result = startDate.hashCode();
			result = c * result + endDate.hashCode();
			result = c * result + companyName.hashCode();
			result = c * result + jobTitle.hashCode();
			result = c * result + description.hashCode();
		}

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof WorkExperience))
			return false;
		WorkExperience other = (WorkExperience) obj;
		
		return this.startDate.equals(other.startDate)
				&& this.endDate.equals(other.endDate)
				&& this.companyName.equals(other.companyName)
				&& this.jobTitle.equals(other.jobTitle)
				&& this.description.equals(other.description);
	}
}
