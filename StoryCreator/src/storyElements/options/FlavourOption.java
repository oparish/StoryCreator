package storyElements.options;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;
import storyElements.Scenario;
import storyElements.optionLists.FlavourList;

public class FlavourOption extends Option
{
	public FlavourOption(JsonObject jsonObject)
	{
		super(jsonObject);
	}
	
	public FlavourOption(String description)
	{
		super(description);
	}
	
	public FlavourList getFlavourList()
	{
		return Main.getMainScenario().getFlavourList(this.contentIntegerMap.get(OptionContentType.FLAVOURLIST));
	}
	
}
