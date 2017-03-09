package storyElements.optionLists;

import java.util.ArrayList;

import javax.json.JsonObject;
import javax.json.JsonValue;

import frontEnd.EditorDialog;
import main.Main;
import storyElements.options.BranchOption;
import storyElements.options.SubplotOption;

@SuppressWarnings("serial")
public class Subplot extends StorySection<SubplotOption>
{
	public Subplot(ArrayList<SubplotOption> initialOptions, String description)
	{
		super(initialOptions, description);
	}
	
	public Subplot(JsonObject jsonObject)
	{
		super();
		for (JsonValue optionJson : jsonObject.getJsonArray(Main.OPTIONS))
		{
			this.add(new SubplotOption((JsonObject) optionJson));
		}
		this.description = jsonObject.getString(Main.DESCRIPTION);
	}

	@Override
	protected void end(EditorDialog editorDialog)
	{
		
	}

}
