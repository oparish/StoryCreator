package storyElements.options;

public abstract class Option
{
	String description;
	
	public String getDescription() {
		return description;
	}

	public Option(String description)
	{
		this.description = description;
	}
}
