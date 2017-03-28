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
	private Integer token;
	private Integer obstacle;
	private Integer goodExitPointID;
	private Integer badExitPointID;
	
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
		this.token = Main.processJsonInt(jsonObject, Main.TOKEN);
		this.obstacle = Main.processJsonInt(jsonObject, Main.OBSTACLE);
		this.goodExitPointID = Main.processJsonInt(jsonObject, Main.GOOD_EXITPOINT);
		this.badExitPointID = Main.processJsonInt(jsonObject, Main.BAD_EXITPOINT);
	}
	
	public Integer getObstacle() {
		return obstacle;
	}

	public void setObstacle(Integer obstacle) {
		this.obstacle = obstacle;
	}
	
	public Integer getToken() {
		return token;
	}

	public void setToken(Integer token) {
		this.token = token;
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
	
	public ExitPoint getGoodExitPointID() {
		return Main.getMainScenario().getExitPoint(this.goodExitPointID);
	}

	public void setGoodExitPointID(Integer goodExitPointID) {
		this.goodExitPointID = goodExitPointID;
	}

	public ExitPoint getBadExitPointID() {
		return Main.getMainScenario().getExitPoint(this.badExitPointID);
	}

	public void setBadExitPointID(Integer badExitPointID) {
		this.badExitPointID = badExitPointID;
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		if (this.exitPointID != null)
			jsonObjectBuilder.add(Main.EXITPOINT, this.exitPointID);
		if (this.subPlot != null)
			jsonObjectBuilder.add(Main.SUBPLOT, this.subPlot);
		if (this.flavourListID != null)
			jsonObjectBuilder.add(Main.FLAVOURLIST, this.flavourListID);
		if (this.token != null)
			jsonObjectBuilder.add(Main.TOKEN, this.token);
		if (this.obstacle != null)
			jsonObjectBuilder.add(Main.OBSTACLE, this.obstacle);
		if (this.goodExitPointID != null)
			jsonObjectBuilder.add(Main.GOOD_EXITPOINT, this.goodExitPointID);
		if (this.badExitPointID != null)
			jsonObjectBuilder.add(Main.BAD_EXITPOINT, this.badExitPointID);
		return jsonObjectBuilder;
	}
}
