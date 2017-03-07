package storyElements.options;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;
import storyElements.Scenario;
import storyElements.optionLists.FlavourList;

public class FlavourOption extends Option
{
	private Integer subFlavourList;
	
	public FlavourOption(String description)
	{
		super(description);
	}
	
	public FlavourOption(JsonObject jsonObject)
	{
		super(jsonObject);
		this.subFlavourList = Main.processJsonInt(jsonObject, Main.SUBFLAVOURLIST);
	}
	
	public FlavourList getSubFlavourList()
	{
		return Main.getMainScenario().getFlavourList(this.subFlavourList);
	}

	public void setSubFlavourList(Integer subFlavourList)
	{
		this.subFlavourList = subFlavourList;
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		Scenario currentScenario = Main.getMainScenario();
		if (this.subFlavourList != null)
			jsonObjectBuilder.add(Main.SUBFLAVOURLIST, this.subFlavourList);
		return jsonObjectBuilder;
	}
}
