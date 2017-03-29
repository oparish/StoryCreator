package storyElements.options;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import frontEnd.EditorDialog;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;

public class EndingOption extends Option implements ExitPoint
{	
	public EndingOption(JsonObject jsonObject)
	{
		super(jsonObject);
	}
	
	public EndingOption(String description, Integer branchLevel)
	{
		super(description);
		this.contentIntegerMap.put(OptionContentType.BRANCH_LEVEL, branchLevel);
	}
}
