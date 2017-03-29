package storyElements.options;

import javax.json.JsonObject;

public class SubplotOption extends Option
{	
	public SubplotOption(JsonObject jsonObject)
	{
		super(jsonObject);
	}
	
	public SubplotOption(String description)
	{
		super(description);
	}
}
