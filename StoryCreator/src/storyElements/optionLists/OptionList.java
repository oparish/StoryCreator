package storyElements.optionLists;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import main.Main;
import storyElements.options.Option;

@SuppressWarnings("serial")
public abstract class OptionList<T extends Option> extends ArrayList<Option>
{	
	public OptionList()
	{
		super();
	}
	
	public OptionList(ArrayList<T> initialOptions)
	{
		super();
		for (T option : initialOptions)
		{
			this.add(option);
		}
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (Option option : this)
		{
			jsonArrayBuilder.add(option.getJsonObjectBuilder().build());
		}
		jsonObjectBuilder.add(Main.OPTIONS, jsonArrayBuilder.build());
		return jsonObjectBuilder;
	}
}
