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
import frontEnd.NewTwistOptionPanel;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.NewOptionPanel;
import main.Main;

public class TwistList extends NonRepeatingOptionList implements StoryElement
{
	protected String type = "Twist";
	private String description;
	
	public TwistList(ArrayList<TwistOption> initialOptions, String description)
	{
		super(initialOptions);
		this.description = description;
	}
	
	public TwistList(String description)
	{
		super();
		this.description = description;
	}
	
	public Option generateOption(EditorDialog editorDialog)
	{
		int rnd = Main.getRandomNumberInRange(this.size() + 1);
		if (rnd < this.size())
		{
			return (Option) this.get(rnd);
		}
		else
		{
			NewTwistOptionPanel newTwistOptionPanel = new NewTwistOptionPanel();
			FieldDialog newTwistOptionDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{newTwistOptionPanel});
			newTwistOptionDialog.setTitle("New " + this.type);
			Main.showWindowInCentre(newTwistOptionDialog);
        	TwistOption twistOption = newTwistOptionPanel.getResult();
        	TwistList.this.add(twistOption);
            return twistOption;
        }  
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
