package storyElements;

import java.util.ArrayList;
import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import frontEnd.AspectOptionPanel;
import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.FlavourOptionPanel;
import frontEnd.MyPanel;
import main.Main;
import storyElements.optionLists.NonRepeatingOptionList;
import storyElements.options.AspectOption;
import storyElements.options.FlavourOption;
import storyElements.options.StoryElement;

public class AspectList extends NonRepeatingOptionList<AspectOption> implements StoryElement
{
	private String description;
	private AspectType aspectType;
	
	public AspectType getAspectType() {
		return aspectType;
	}

	public void setAspectType(AspectType aspectType) {
		this.aspectType = aspectType;
	}

	public AspectList(JsonObject jsonObject)
	{
		super(AspectOptionPanel.class);
		int i = 0;
		for (JsonValue optionJson : jsonObject.getJsonArray(Main.OPTIONS))
		{
			AspectOption aspectOption = new AspectOption((JsonObject) optionJson);
			aspectOption.setId(i);
			this.add(aspectOption);
			i++;
		}
		this.description = jsonObject.getString(Main.DESCRIPTION);
	}
	
	public AspectList(ArrayList<AspectOption> initialOptions, String description)
	{
		super(initialOptions, AspectOptionPanel.class);
		this.description = description;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		return jsonObjectBuilder;
	}
	
	protected AspectOption generateNewOption(EditorDialog editorDialog)
	{
		AspectOption aspectOption = super.generateNewOption(editorDialog);
		aspectOption.setId(this.size() - 1);
		return aspectOption;
	}
}
