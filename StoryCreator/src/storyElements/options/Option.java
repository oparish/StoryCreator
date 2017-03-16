package storyElements.options;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;

public abstract class Option implements StoryElement
{	
	String description;
	
	public String getDescription() {
		return description;
	}

	public Option(String description)
	{
		this.description = description;
	}
	
	public Option(JsonObject jsonObject)
	{
		this.description = jsonObject.getString(Main.DESCRIPTION);
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add(Main.DESCRIPTION, this.description);
		return jsonObjectBuilder;
	}
}
