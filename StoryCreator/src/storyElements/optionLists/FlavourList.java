package storyElements.optionLists;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import main.Main;
import storyElements.options.BranchOption;
import storyElements.options.FlavourOption;
import storyElements.options.Option;

@SuppressWarnings("serial")
public class FlavourList extends NonRepeatingOptionList<FlavourOption>
{
	protected String type = "Flavour";
	String description;
	
	public FlavourList(ArrayList<FlavourOption> initialOptions, String description)
	{
		super(initialOptions);
		this.description = description;
	}
	
	public FlavourList(JsonObject jsonObject)
	{
		super();
		for (JsonValue optionJson : jsonObject.getJsonArray(Main.OPTIONS))
		{
			this.add(new FlavourOption((JsonObject) optionJson));
		}
		this.description = jsonObject.getString(Main.DESCRIPTION);
	}

	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		return jsonObjectBuilder;
	}
	
}
