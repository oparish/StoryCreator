package storyElements;

import storyElements.options.StoryElement;

public class BranchLevel implements StoryElement 
{
	private String description;
	
	public BranchLevel(String description)
	{
		this.description = description;
	}
	
	@Override
	public String getDescription()
	{
		return this.description;
	}

}
