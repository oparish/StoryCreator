package storyElements.options;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import frontEnd.EditorDialog;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;

public class EndingOption extends Option implements ExitPoint
{
	public EndingOption(String description)
	{
		super(description);
	}
	
	public EndingOption(JsonObject jsonObject)
	{
		super(jsonObject);
	}

	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.TYPE, Main.ENDINGOPTION);
		return jsonObjectBuilder;
	}

	@Override
	public void useAsExitPoint(EditorDialog editorDialog)
	{
		editorDialog.reachEnding(this);
	}
	
}
