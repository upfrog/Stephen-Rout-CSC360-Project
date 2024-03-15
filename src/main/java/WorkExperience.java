import java.time.LocalDateTime;

class WorkExperience
{
	LocalDateTime startDate;
	LocalDateTime endDate;
	String companyName;
	String jobTitle;
	String description;
	
	/**
	 * @param startDate: the date the user began working this job.
	 * @param endDate: the date the user stopped working this job.
	 * @param companyName: the name of the company.
	 * @param jobTitle: the user's title at the job.
	 * @param description: any additional information the user wishes to provide.
	 */
	protected WorkExperience(LocalDateTime startDate, LocalDateTime endDate, String companyName, String jobTitle,
			String description)
	{
		this.startDate = startDate;
		this.endDate = endDate;
		this.companyName = companyName;
		this.jobTitle = jobTitle;
		this.description = description;
	}

	protected LocalDateTime getStartDate()
	{
		return startDate;
	}

	protected void setStartDate(LocalDateTime startDate)
	{
		this.startDate = startDate;
	}

	protected LocalDateTime getEndDate()
	{
		return endDate;
	}

	protected void setEndDate(LocalDateTime endDate)
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
