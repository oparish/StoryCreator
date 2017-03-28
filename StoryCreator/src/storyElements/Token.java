package storyElements;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;
import storyElements.options.StoryElement;

public class Token implements StoryElement
{
	private String description;

	public Token(String description)
	{
		this.description = description;
	}
	
	public Token(JsonObject jsonObject)
	{
		this.description = jsonObject.getString(Main.DESCRIPTION);
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add(Main.DESCRIPTION, this.description);
		return jsonObjectBuilder;
	}
	
	@Override
	public String getDescription()
	{
		return this.description;
	}

}
