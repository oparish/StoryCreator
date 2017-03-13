package storyElements;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import main.Main;
import frontEnd.EditorDialog;
import storyElements.optionLists.Branch;
import storyElements.optionLists.FlavourList;
import storyElements.optionLists.Subplot;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.FlavourOption;
import storyElements.options.Option;
import storyElements.options.SubplotOption;

public class Scenario implements JsonStructure
{	
	HashMap<Integer, ExitPoint> exitPoints = new HashMap<Integer, ExitPoint>();
	HashMap<Integer, Subplot> subplots = new HashMap<Integer, Subplot>();
	HashMap<Integer, FlavourList> flavourLists = new HashMap<Integer, FlavourList>();
	Branch currentBranch;
	Integer startingBranch;
	int branchCounter = 1;

	Chance optionBecomesSubplot = null;
	Chance optionBecomesNewExitPoint = null;
	Chance flavourHasSubFlavour = null;
	int branchLength;
	int subplotLength;
	int scenarioLength;
	String description;

	public Scenario(String description, ArrayList<BranchOption> initialBranchOptions, String initialBranchDescription, int branchLength, int subplotLength, int scenarioLength)
	{
		this.description = description;
		this.currentBranch = this.setupNewBranch(initialBranchOptions, initialBranchDescription);
		this.startingBranch = 0;
		exitPoints.put(0, this.currentBranch);
		this.branchLength = branchLength;
		this.subplotLength = subplotLength;
		this.scenarioLength = scenarioLength;
	}
	
	public Scenario(JsonObject jsonObject)
	{
		this.description = jsonObject.getString(Main.DESCRIPTION);
		this.branchLength = Main.processJsonInt(jsonObject, Main.BRANCH_LENGTH);
		this.subplotLength = Main.processJsonInt(jsonObject, Main.SUBPLOT_LENGTH);
		this.scenarioLength = Main.processJsonInt(jsonObject, Main.SCENARIO_LENGTH);
		Integer optionBecomesSubplotProb = Main.processJsonInt(jsonObject, Main.OPTION_BECOMES_SUBPLOT);
		Integer optionBecomesNewExitPointProb = Main.processJsonInt(jsonObject, Main.OPTION_BECOMES_NEW_EXITPOINT);
		Integer flavourHasSubFlavourProb = Main.processJsonInt(jsonObject, Main.FLAVOUR_HAS_SUBFLAVOUR);
		if (optionBecomesSubplotProb != null)
			this.optionBecomesSubplot = new Chance(optionBecomesSubplotProb);
		if (optionBecomesNewExitPointProb != null)
			this.optionBecomesNewExitPoint = new Chance(optionBecomesNewExitPointProb);
		if (flavourHasSubFlavourProb != null)
			this.flavourHasSubFlavour = new Chance(flavourHasSubFlavourProb);
		
		JsonObject exitPointObject = jsonObject.getJsonObject(Main.EXITPOINTS);
		for (Entry<String, JsonValue> entry:  exitPointObject.entrySet())
		{
			ExitPoint exitPoint = Main.getFromJson((JsonObject) entry.getValue());
			this.exitPoints.put(Integer.valueOf(entry.getKey()), exitPoint);
			if (((JsonObject) entry.getValue()).containsKey(Main.STARTING_BRANCH))
			{
				this.startingBranch = Integer.valueOf(entry.getKey());
				this.currentBranch = (Branch) exitPoint;
			}
		}
		
		JsonObject flavourListsObject = jsonObject.getJsonObject(Main.FLAVOURLISTS);
		for (Entry<String, JsonValue> entry:  flavourListsObject.entrySet())
		{
			this.flavourLists.put(Integer.valueOf(entry.getKey()), new FlavourList((JsonObject) entry.getValue()));
		}
		
		JsonObject subplotsObject = jsonObject.getJsonObject(Main.SUBPLOTS);
		for (Entry<String, JsonValue> entry:  subplotsObject.entrySet())
		{
			this.subplots.put(Integer.valueOf(entry.getKey()), new Subplot((JsonObject) entry.getValue()));
		}
	}
	
	public void incrementBranchCounter()
	{
		this.branchCounter++;
	}
	
	public boolean checkLastBranch()
	{
		return this.branchCounter >= this.scenarioLength;
	}
	
	public JsonObject getJsonObject()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
				.add(Main.DESCRIPTION, this.description)
				.add(Main.BRANCH_LENGTH, this.branchLength)
				.add(Main.SUBPLOT_LENGTH, this.subplotLength)
				.add(Main.SCENARIO_LENGTH, this.scenarioLength);
		if (this.optionBecomesSubplot != null)
			jsonObjectBuilder.add(Main.OPTION_BECOMES_SUBPLOT, this.optionBecomesSubplot.getProb());
		if (this.optionBecomesNewExitPoint != null)
			jsonObjectBuilder.add(Main.OPTION_BECOMES_NEW_EXITPOINT, this.optionBecomesNewExitPoint.getProb());
		if (this.flavourHasSubFlavour != null)
			jsonObjectBuilder.add(Main.FLAVOUR_HAS_SUBFLAVOUR, this.flavourHasSubFlavour.getProb());
		
		JsonObjectBuilder exitPointBuilder = Json.createObjectBuilder();		
		for (Entry<Integer, ExitPoint> entry : exitPoints.entrySet())
		{
			JsonObjectBuilder innerJsonObjectBuilder = entry.getValue().getJsonObjectBuilder();
			if (entry.getKey() == this.startingBranch)
			{
				innerJsonObjectBuilder.add(Main.STARTING_BRANCH, true);
			}
			exitPointBuilder.add(String.valueOf(entry.getKey()), innerJsonObjectBuilder.build());
		}		
		jsonObjectBuilder.add(Main.EXITPOINTS, exitPointBuilder.build());
		
		JsonObjectBuilder flavourListBuilder = Json.createObjectBuilder();		
		for (Entry<Integer, FlavourList> entry : flavourLists.entrySet())
		{
			flavourListBuilder.add(String.valueOf(entry.getKey()), entry.getValue().getJsonObjectBuilder().build());
		}		
		jsonObjectBuilder.add(Main.FLAVOURLISTS, flavourListBuilder.build());
		
		JsonObjectBuilder subPlotBuilder = Json.createObjectBuilder();		
		for (Entry<Integer, Subplot> entry : subplots.entrySet())
		{
			subPlotBuilder.add(String.valueOf(entry.getKey()), entry.getValue().getJsonObjectBuilder().build());
		}		
		jsonObjectBuilder.add(Main.SUBPLOTS, subPlotBuilder.build());
		
		return jsonObjectBuilder.build();
	}
	
	public ExitPoint getExitPoint(Integer id)
	{
		return this.exitPoints.get(id);
	}
	
	public FlavourList getFlavourList(Integer id)
	{
		return this.flavourLists.get(id);
	}
	
	public Subplot getSubplot(Integer id)
	{
		return this.subplots.get(id);
	}
	
	public Integer identifyExitPoint(ExitPoint exitPoint)
	{
		return Scenario.identifyObject(this.exitPoints, exitPoint);
	}
	
	public Integer identifySubPlot(Subplot subplot)
	{
		return Scenario.identifyObject(this.subplots, subplot);
	}
	
	public Integer identifyFlavourList(FlavourList flavourList)
	{
		return Scenario.identifyObject(this.flavourLists, flavourList);
	}
	
	private static <T> Integer identifyObject(HashMap<Integer, T> map, T t)
	{
		for (Entry<Integer, T> entry : map.entrySet())
		{
			if (entry.getValue() == t)
				return entry.getKey();
		}
		return null;
	}
	
	public Branch getCurrentBranch() {
		return currentBranch;
	}

	public void setCurrentBranch(Branch currentBranch) {
		this.currentBranch = currentBranch;
	}

	public String getDescription() {
		return description;
	}
	
	public int getSubplotLength() {
		return subplotLength;
	}
	
	public int getBranchLength() {
		return branchLength;
	}
	
	public Chance getOptionBecomesNewExitPoint() {
		return optionBecomesNewExitPoint;
	}

	public void setOptionBecomesNewExitPoint(Chance optionBecomesNewExitPoint) {
		this.optionBecomesNewExitPoint = optionBecomesNewExitPoint;
	}

	public Chance getFlavourHasSubFlavour() {
		return flavourHasSubFlavour;
	}

	public void setFlavourHasSubFlavour(Chance flavourHasSubFlavour) {
		this.flavourHasSubFlavour = flavourHasSubFlavour;
	}
	
	public Chance getOptionBecomesSubplot() {
		return optionBecomesSubplot;
	}

	public void setOptionBecomesSubplot(Chance optionBecomesSubplot) {
		this.optionBecomesSubplot = optionBecomesSubplot;
	}
	
	private Branch setupNewBranch(ArrayList<BranchOption> initialBranchOptions, String initialBranchDescription)
	{
		Branch newBranch = new Branch(initialBranchOptions, initialBranchDescription);
		return newBranch;
	}
	
	public Integer addExitPoint(ExitPoint exitPoint)
	{
		Integer exitPointID = this.exitPoints.size();
		this.exitPoints.put(exitPointID, exitPoint);
		return exitPointID;
	}
	
	public Integer addFlavourList(FlavourList flavourList)
	{
		Integer flavourListID = this.flavourLists.size();
		this.flavourLists.put(flavourListID, flavourList);
		return flavourListID;
	}
	
	public Integer addSubPlot(Subplot subplot)
	{
		Integer subplotID = this.subplots.size();
		this.subplots.put(subplotID, subplot);
		return subplotID;
	}
	
	public static void main(String[] args)
	{
		ArrayList<BranchOption> branchOptions1 = new ArrayList<BranchOption>();
		branchOptions1.add(new BranchOption("One"));
		branchOptions1.add(new BranchOption("Two"));
		branchOptions1.add(new BranchOption("Three"));
		
		ArrayList<BranchOption> branchOptions2 = new ArrayList<BranchOption>();
		branchOptions2.add(new BranchOption("Four"));
		branchOptions2.add(new BranchOption("Five"));
		branchOptions2.add(new BranchOption("Six"));
		
		ArrayList<FlavourOption> flavourOptions = new ArrayList<FlavourOption>();
		flavourOptions.add(new FlavourOption("Seven"));
		flavourOptions.add(new FlavourOption("Eight"));
		flavourOptions.add(new FlavourOption("Nine"));
		
		ArrayList<FlavourOption> flavourOptions2 = new ArrayList<FlavourOption>();
		flavourOptions2.add(new FlavourOption("Strawberry"));
		flavourOptions2.add(new FlavourOption("Vanilla"));
		flavourOptions2.add(new FlavourOption("Chocolate"));
		
		ArrayList<SubplotOption> subplotOptions = new ArrayList<SubplotOption>();
		subplotOptions.add(new SubplotOption("A"));
		subplotOptions.add(new SubplotOption("B"));
		subplotOptions.add(new SubplotOption("C"));
		
		Scenario testScenario = new Scenario("Scenario 1", branchOptions1, "Descrip1", 0, 0, 0);
		testScenario.setFlavourHasSubFlavour(new Chance(50));
		testScenario.setOptionBecomesSubplot(new Chance(50));
		testScenario.setOptionBecomesNewExitPoint(new Chance(50));
		
		Branch newBranch = new Branch(branchOptions2, "Descrip2");	
		Integer newBranchId = testScenario.addExitPoint(newBranch);;
		testScenario.getCurrentBranch().setDefaultExitPoint(newBranchId);
		
		FlavourList flavourList = new FlavourList(flavourOptions, "Flavour 1");
		Integer flavourListId = testScenario.addFlavourList(flavourList);
		
		FlavourList flavourList2 = new FlavourList(flavourOptions2, "Flavour 2");
		int flavourListId2 = testScenario.addFlavourList(flavourList2);
		
		((FlavourOption) flavourList.get(0)).setSubFlavourList(flavourListId2);
		
		BranchOption newOption = new BranchOption("Ten");
		newOption.setFlavourList(flavourListId);
		newBranch.add(newOption);
		
		EndingOption ending1 = new EndingOption("END");
		Integer endingPointId = testScenario.addExitPoint(ending1);
		newOption.setExitPoint(endingPointId);
		
		BranchOption newOption2 = new BranchOption("Eleven");
		newBranch.add(newOption2);
		
		Subplot subplot = new Subplot(subplotOptions, "subplotDescrip");
		Integer subplotID = testScenario.addSubPlot(subplot);
		newOption2.setSubPlot(subplotID);
		
		Main.setMainScenario(testScenario);
		System.out.println(testScenario.getJsonObject());
		
		Scenario newScenario = new Scenario(testScenario.getJsonObject());
		Main.setMainScenario(newScenario);
		System.out.println(newScenario.getJsonObject());
	}
}
