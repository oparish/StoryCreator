package storyElements.options;

import javax.json.JsonObject;

import main.Main;
import storyElements.optionLists.FlavourList;

public class AspectOption extends Option
{
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AspectOption(JsonObject jsonObject)
	{
		super(jsonObject);
	}
	
	public AspectOption(String description)
	{
		super(description);
	}
}
