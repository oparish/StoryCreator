package storyElements.options;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import frontEnd.EditorDialog;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;

public class EndingOption extends Option implements ExitPoint
{
	private Integer branchLevel;
	
	public EndingOption(String description, int branchLevel)
	{
		super(description);
		this.branchLevel = branchLevel;
	}
	
	public EndingOption(JsonObject jsonObject)
	{
		super(jsonObject);
		this.branchLevel = jsonObject.getInt(Main.BRANCH_LEVEL);
	}

	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.BRANCH_LEVEL, this.branchLevel);
		return jsonObjectBuilder;
	}	
}
