package storyElements.options;

import java.util.Map.Entry;

import javax.json.Json;
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
	
	public EndingOption(String description)
	{
		super(description);
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.BRANCH_LEVEL, Main.getMainScenario().getMaximumScenarioLength());
		return jsonObjectBuilder;
	}
}
