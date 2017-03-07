package storyElements.optionLists;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;
import storyElements.options.FlavourOption;
import storyElements.options.Option;

@SuppressWarnings("serial")
public abstract class StorySection<T extends Option> extends RepeatingOptionList<T>
{	
	String description;
	
	public StorySection(ArrayList<T> initialOptions, String description)
	{
		super(initialOptions);
		this.description = description;
	}
	
	public StorySection()
	{
		super();
	}
	
	public String getDescription() {
		return description;
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		return jsonObjectBuilder;
	}
}
