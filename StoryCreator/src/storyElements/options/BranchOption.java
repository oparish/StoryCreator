package storyElements.options;

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
	private Integer exitPointID;
	private Integer flavourListID;
	private Integer subPlot;
	
	public Subplot getSubPlot()
	{
		return Main.getMainScenario().getSubplot(this.subPlot);
	}

	public void setSubPlot(Integer subPlot) {
		this.subPlot = subPlot;
	}

	public BranchOption(String description)
	{
		super(description);
	}
	
	public BranchOption(JsonObject jsonObject)
	{
		super(jsonObject);	
		this.exitPointID = Main.processJsonInt(jsonObject, Main.EXITPOINT);
		this.flavourListID = Main.processJsonInt(jsonObject, Main.FLAVOURLIST);
		this.subPlot = Main.processJsonInt(jsonObject, Main.SUBPLOT);
	}
	
	
	public FlavourList getFlavourList() {
		return Main.getMainScenario().getFlavourList(this.flavourListID);
	}

	public void setFlavourList(Integer flavourList) {
		this.flavourListID = flavourList;
	}
	
	public ExitPoint getExitPoint() {
		return Main.getMainScenario().getExitPoint(this.exitPointID);
	}

	public void setExitPoint(Integer exitPoint) {
		this.exitPointID = exitPoint;
	}
	
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		Scenario currentScenario = Main.getMainScenario();
		if (this.exitPointID != null)
			jsonObjectBuilder.add(Main.EXITPOINT, this.exitPointID);
		if (this.subPlot != null)
			jsonObjectBuilder.add(Main.SUBPLOT, this.subPlot);
		if (this.flavourListID != null)
			jsonObjectBuilder.add(Main.FLAVOURLIST, this.flavourListID);
		return jsonObjectBuilder;
	}
}
