class WorkExperience
{
	private String startDate;
	private String endDate;
	private String companyName;
	private String jobTitle;
	private String description;
	
	/**
	 * @param startDate: the date the user began working this job.
	 * @param endDate: the date the user stopped working this job.
	 * @param companyName: the name of the company.
	 * @param jobTitle: the user's title at the job.
	 * @param description: any additional information the user wishes to provide.
	 */
	protected WorkExperience(String startDate, String endDate, String companyName, String jobTitle,
			String description)
	{
		this.startDate = startDate;
		this.endDate = endDate;
		this.companyName = companyName;
		this.jobTitle = jobTitle;
		this.description = description;
	}

	
	//All of these are plain, maximally boring setters and getters. 
	
	protected String getStartDate()
	{
		return startDate;
	}

	
	protected void setStartDate(String startDate)
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
}
