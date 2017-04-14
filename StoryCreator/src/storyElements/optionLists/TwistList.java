package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import storyElements.options.BranchOption;
import storyElements.options.Option;
import storyElements.options.StoryElement;
import storyElements.options.TwistOption;
import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.MyPanel;
import frontEnd.TwistOptionPanel;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.NewOptionPanel;
import main.Main;

public class TwistList extends NonRepeatingOptionList implements StoryElement
{
	protected String type = "Twist";
	private String description;
	
	public TwistList(ArrayList<TwistOption> initialOptions, String description)
	{
		super(initialOptions, TwistOptionPanel.class);
		this.description = description;
	}
	
	public TwistList(String description)
	{
		super(TwistOptionPanel.class);
		this.description = description;
	}

	@Override
	public String getDescription()
	{
		return this.description;
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		return jsonObjectBuilder;
	}
}
