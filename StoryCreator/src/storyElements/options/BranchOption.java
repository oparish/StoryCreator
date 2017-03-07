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
	private Integer exitPoint;
	private Integer flavourList;
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
		this.exitPoint = Main.processJsonInt(jsonObject, Main.EXITPOINT);
		this.flavourList = Main.processJsonInt(jsonObject, Main.FLAVOURLIST);
		this.subPlot = Main.processJsonInt(jsonObject, Main.SUBPLOT);
	}
	
	
	public FlavourList getFlavourList() {
		return Main.getMainScenario().getFlavourList(this.flavourList);
	}

	public void setFlavourList(Integer flavourList) {
		this.flavourList = flavourList;
	}
	
	public ExitPoint getExitPoint() {
		return Main.getMainScenario().getExitPoint(this.exitPoint);
	}

	public void setExitPoint(Integer exitPoint) {
		this.exitPoint = exitPoint;
	}
	
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		Scenario currentScenario = Main.getMainScenario();
		if (this.exitPoint != null)
			jsonObjectBuilder.add(Main.EXITPOINT, this.exitPoint);
		if (this.subPlot != null)
			jsonObjectBuilder.add(Main.SUBPLOT, this.subPlot);
		if (this.flavourList != null)
			jsonObjectBuilder.add(Main.FLAVOURLIST, this.flavourList);
		return jsonObjectBuilder;
	}
}
