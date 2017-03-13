package storyElements.options;

import javax.json.JsonObject;

public class TwistOption extends Option
{
	public TwistOption(String description)
	{
		super(description);
	}
	
	public TwistOption(JsonObject jsonObject)
	{
		super(jsonObject);
	}
}
