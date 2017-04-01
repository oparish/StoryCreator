package storyElements.options;

import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.optionLists.FlavourList;
import storyElements.optionLists.Subplot;

public class BranchOption extends Option
{	
	public Subplot getSubPlot()
	{
		return Main.getMainScenario().getSubplot(this.contentIntegerMap.get(OptionContentType.SUBPLOT));
	}

	public void setSubPlot(Integer subPlot) {
		this.contentIntegerMap.put(OptionContentType.SUBPLOT, subPlot);
	}
	
	public BranchOption(JsonObject jsonObject)
	{
		super(jsonObject);	
	}
	
	public BranchOption(String description)
	{
		super(description);
	}
	
	public BranchOption(String description, HashMap<OptionContentType, Integer> contentIntegerMap)
	{
		super(description, contentIntegerMap);
	}
	
	public Integer getObstacle() {
		return this.contentIntegerMap.get(OptionContentType.OBSTACLE);
	}
	
	public Integer getToken() {
		return this.contentIntegerMap.get(OptionContentType.TOKEN);
	}
	
	public FlavourList getFlavourList() {
		return Main.getMainScenario().getFlavourList(this.contentIntegerMap.get(OptionContentType.FLAVOURLIST));
	}
	
	public ExitPoint getExitPoint() {
		return Main.getMainScenario().getExitPoint(this.contentIntegerMap.get(OptionContentType.EXITPOINT));
	}
	
	public ExitPoint getGoodExitPointID() {
		return Main.getMainScenario().getExitPoint(this.contentIntegerMap.get(OptionContentType.GOODEXITPOINT));
	}

	public ExitPoint getBadExitPointID() {
		return Main.getMainScenario().getExitPoint(this.contentIntegerMap.get(OptionContentType.BADEXITPOINT));
	}
}
