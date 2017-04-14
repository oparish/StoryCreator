package storyElements.optionLists;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.FlavourOptionPanel;
import frontEnd.MyPanel;
import frontEnd.fieldPanel.NewOptionPanel;
import main.Main;
import storyElements.options.BranchOption;
import storyElements.options.FlavourOption;
import storyElements.options.Option;
import storyElements.options.StoryElement;

@SuppressWarnings("serial")
public class FlavourList extends NonRepeatingOptionList<FlavourOption> implements StoryElement
{
	protected String type = "Flavour";
	String description;
	
	public FlavourList(ArrayList<FlavourOption> initialOptions, String description)
	{
		super(initialOptions, FlavourOptionPanel.class);
		this.description = description;
	}
	
	public FlavourList(JsonObject jsonObject)
	{
		super(FlavourOptionPanel.class);
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

	@Override
	public String getDescription() 
	{
		return this.description;
	}
}
