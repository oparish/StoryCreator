package storyElements;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
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
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;
import storyElements.options.SubplotOption;

public class Scenario implements JsonStructure, StoryElement
{	
	HashMap<Integer, ExitPoint> exitPoints = new HashMap<Integer, ExitPoint>();
	HashMap<Integer, Subplot> subplots = new HashMap<Integer, Subplot>();
	HashMap<Integer, FlavourList> flavourLists = new HashMap<Integer, FlavourList>();
	HashMap<Integer, ArrayList<ExitPoint>> branchHierarchy = new HashMap<Integer, ArrayList<ExitPoint>>();
	HashMap<Integer, Token> tokens = new HashMap<Integer, Token>();
	Branch currentBranch;
	int nextBranch = 0;

	Chance optionBecomesSubplot = null;
	Chance optionBecomesNewExitPoint = null;
	Chance optionHasFlavourList = null;
	Chance optionHasObstacle = null;
	Chance optionHasToken = null;
	Chance branchHasOpening = null;
	Chance suddenEnding = null;

	ArrayList<BranchLevel> branchLevels;
	int branchLength;
	int subplotLength;
	int minimumScenarioLength;

	String description;
	
	File file;

	public Scenario(String description, int branchLength, int subplotLength, ArrayList<BranchLevel> branchLevels)
	{
		this.description = description;
		this.branchLength = branchLength;
		this.subplotLength = subplotLength;
		this.branchLevels = branchLevels;
	}
	
	public Scenario(JsonObject jsonObject)
	{
		this.description = jsonObject.getString(Main.DESCRIPTION);
		this.branchLength = Main.processJsonInt(jsonObject, Main.BRANCH_LENGTH);
		this.subplotLength = Main.processJsonInt(jsonObject, Main.SUBPLOT_LENGTH);
		Integer optionBecomesSubplotProb = Main.processJsonInt(jsonObject, Main.OPTION_BECOMES_SUBPLOT);
		Integer optionBecomesNewExitPointProb = Main.processJsonInt(jsonObject, Main.OPTION_BECOMES_NEW_EXITPOINT);
		Integer flavourHasSubFlavourProb = Main.processJsonInt(jsonObject, Main.FLAVOUR_HAS_SUBFLAVOUR);
		Integer optionHasObstacle = Main.processJsonInt(jsonObject, Main.OPTION_HAS_OBSTACLE);
		Integer optionHasToken = Main.processJsonInt(jsonObject, Main.OPTION_HAS_TOKEN);
		Integer openingChance = Main.processJsonInt(jsonObject, Main.OPENINGCHANCE);
		Integer suddenEndingChance = Main.processJsonInt(jsonObject, Main.SUDDENENDING_CHANCE);
		Integer minimumScenarioLength = Main.processJsonInt(jsonObject, Main.MINIMUM_SCENARIO_LENGTH);
		
		if (optionBecomesSubplotProb != null)
			this.optionBecomesSubplot = new Chance(optionBecomesSubplotProb);
		if (optionBecomesNewExitPointProb != null)
			this.optionBecomesNewExitPoint = new Chance(optionBecomesNewExitPointProb);
		if (flavourHasSubFlavourProb != null)
			this.optionHasFlavourList = new Chance(flavourHasSubFlavourProb);
		if (optionHasObstacle != null)
			this.optionHasObstacle= new Chance(optionHasObstacle);
		if (optionHasToken != null)
			this.optionHasToken = new Chance(optionHasToken);
		if (openingChance != null)
			this.branchHasOpening = new Chance(openingChance);
		if (openingChance != null)
			this.suddenEnding = new Chance(suddenEndingChance);
		
		this.minimumScenarioLength = minimumScenarioLength;
		
		this.branchLevels = new ArrayList<BranchLevel>();
		ArrayList<String> branchLevelStrings = Main.getStringsFromJsonArray(jsonObject.getJsonArray(Main.BRANCHLEVELS));
		for (String branchLevelString : branchLevelStrings)
		{
			this.branchLevels.add(new BranchLevel(branchLevelString));
		}
		
		JsonObject exitPointListObject = jsonObject.getJsonObject(Main.EXITPOINTS);
		for (Entry<String, JsonValue> entry:  exitPointListObject.entrySet())
		{
			JsonObject exitPointObject = (JsonObject) entry.getValue();
			ExitPoint exitPoint = Main.getFromJson(exitPointObject, this.getMaximumScenarioLength());
			this.exitPoints.put(Integer.valueOf(entry.getKey()), exitPoint);
			int branchLevel = exitPointObject.getInt(Main.BRANCH_LEVEL);
			
			if (!this.branchHierarchy.containsKey(branchLevel))
				this.branchHierarchy.put(branchLevel, new ArrayList<ExitPoint>());
				
			this.branchHierarchy.get(branchLevel).add(exitPoint);
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
		
		JsonObject tokensObject = jsonObject.getJsonObject(Main.TOKENS);
		for (Entry<String, JsonValue> entry:  tokensObject.entrySet())
		{
			this.tokens.put(Integer.valueOf(entry.getKey()), new Token((JsonObject) entry.getValue()));
		}
	}
	
	public int getMaximumScenarioLength()
	{
		return this.branchLevels.size();
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public boolean canEnd()
	{
		return this.minimumScenarioLength <= this.nextBranch;
	}

	public void setToLastBranch()
	{
		this.nextBranch = this.getMaximumScenarioLength();
	}
	
	public int getMinimumScenarioLength() {
		return minimumScenarioLength;
	}

	public void setMinimumScenarioLength(int minimumScenarioLength) {
		this.minimumScenarioLength = minimumScenarioLength;
	}
	
	public Chance getSuddenEnding() {
		return suddenEnding;
	}

	public void setSuddenEnding(Chance suddenEnding) {
		this.suddenEnding = suddenEnding;
	}

	
	public Collection<? extends StoryElement> getStoryElementList(OptionContentType optionContentType, int branchLevel)
	{
		switch(optionContentType)
		{
			case BADEXITPOINT:
			case GOODEXITPOINT:
			case EXITPOINT:
				return this.getExitPointsAtBranchLevel(branchLevel);
			case TOKEN:
			case OBSTACLE:
				return this.tokens.values();
			case FLAVOURLIST:
				return this.flavourLists.values();
			case SUBPLOT:
				return this.subplots.values();
			default:
				return null;
		}
	}
	
	public ArrayList<Token> getTokens()
	{
		ArrayList<Token> tokens = new ArrayList<Token>();
		for (Token token : this.tokens.values())
		{
			tokens.add(token);
		}
		return tokens;
	}
	
	public Token getTokenByID(Integer tokenID)
	{
		return this.tokens.get(tokenID);
	}
	
	public HashMap<Integer, FlavourList> getFlavourLists()
	{
		return flavourLists;
	}
	
	public Chance getBranchHasOpening() {
		return branchHasOpening;
	}

	public void setBranchHasOpening(Chance branchHasOpening) {
		this.branchHasOpening = branchHasOpening;
	}
	
	public Chance getOptionHasToken() {
		return optionHasToken;
	}

	public void setOptionHasToken(Chance optionHasToken) {
		this.optionHasToken = optionHasToken;
	}
	
	public Chance getOptionHasObstacle() {
		return optionHasObstacle;
	}

	public void setOptionHasObstacle(Chance optionHasObstacle) {
		this.optionHasObstacle = optionHasObstacle;
	}

	public boolean pastScenarioMidPoint()
	{
		double midPoint = (double)this.getMaximumScenarioLength()/(double)2;
		if ((double)this.currentBranch.getBranchLevel()< midPoint)
			return false;
		else
			return true;
	}
	
	public ArrayList<ExitPoint> getExitPointsAtBranchLevel(int branchLevel)
	{
		if (this.branchHierarchy.containsKey(branchLevel))
			return this.branchHierarchy.get(branchLevel);
		else
			return null;
	}
	
	public void recordNewExitPoint(ExitPoint exitPoint, int branchLevel)
	{
		if (this.branchHierarchy.containsKey(branchLevel))
		{
			this.branchHierarchy.get(branchLevel).add(exitPoint);
		}
		else
		{
			ArrayList<ExitPoint> exitPoints = new ArrayList<ExitPoint>();
			exitPoints.add(exitPoint);
			this.branchHierarchy.put(branchLevel, exitPoints);
		}
	}
	
	public void incrementBranchCounter()
	{
		this.nextBranch++;
	}
	
	public int getNextBranch() {
		return nextBranch;
	}

	public boolean checkLastBranch()
	{
		return this.nextBranch >= this.branchLevels.size();
	}
	
	public JsonObject getJsonObject()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
				.add(Main.DESCRIPTION, this.description)
				.add(Main.BRANCH_LENGTH, this.branchLength)
				.add(Main.SUBPLOT_LENGTH, this.subplotLength)
				.add(Main.MINIMUM_SCENARIO_LENGTH, this.minimumScenarioLength);
		if (this.optionBecomesSubplot != null)
			jsonObjectBuilder.add(Main.OPTION_BECOMES_SUBPLOT, this.optionBecomesSubplot.getProb());
		if (this.optionBecomesNewExitPoint != null)
			jsonObjectBuilder.add(Main.OPTION_BECOMES_NEW_EXITPOINT, this.optionBecomesNewExitPoint.getProb());
		if (this.optionHasFlavourList != null)
			jsonObjectBuilder.add(Main.FLAVOUR_HAS_SUBFLAVOUR, this.optionHasFlavourList.getProb());
		if (this.optionHasObstacle != null)
			jsonObjectBuilder.add(Main.OPTION_HAS_OBSTACLE, this.optionHasObstacle.getProb());
		if (this.optionHasToken != null)
			jsonObjectBuilder.add(Main.OPTION_HAS_TOKEN, this.optionHasToken.getProb());
		if (this.branchHasOpening != null)
			jsonObjectBuilder.add(Main.OPENINGCHANCE, this.branchHasOpening.getProb());
		
		JsonObjectBuilder exitPointBuilder = Json.createObjectBuilder();		
		for (Entry<Integer, ExitPoint> entry : exitPoints.entrySet())
		{
			JsonObjectBuilder innerJsonObjectBuilder = entry.getValue().getJsonObjectBuilder();
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
		
		JsonObjectBuilder tokensBuilder = Json.createObjectBuilder();		
		for (Entry<Integer, Token> entry : tokens.entrySet())
		{
			tokensBuilder.add(String.valueOf(entry.getKey()), entry.getValue().getJsonObjectBuilder().build());
		}		
		jsonObjectBuilder.add(Main.TOKENS, tokensBuilder.build());
		
		ArrayList<String> branchLevelStrings = new ArrayList<String>();
		for (BranchLevel branchLevel : this.branchLevels)
		{
			branchLevelStrings.add(branchLevel.getDescription());
		}
		
		jsonObjectBuilder.add(Main.BRANCHLEVELS, Main.getJsonBuilderForStrings(branchLevelStrings));
		
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
	
	public Integer identifyToken(Token token)
	{
		return Scenario.identifyObject(this.tokens, token);
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
		this.nextBranch = this.currentBranch.getBranchLevel() + 1;
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

	public Chance getOptionHasFlavour() {
		return optionHasFlavourList;
	}

	public void setOptionHasFlavour(Chance flavourHasSubFlavour) {
		this.optionHasFlavourList = flavourHasSubFlavour;
	}
	
	public Chance getOptionBecomesSubplot() {
		return optionBecomesSubplot;
	}

	public void setOptionBecomesSubplot(Chance optionBecomesSubplot) {
		this.optionBecomesSubplot = optionBecomesSubplot;
	}
	
	public Integer getStoryElementID(StoryElement storyElement)
	{
		if (storyElement instanceof ExitPoint)
			return this.getExitPointID((ExitPoint) storyElement);
		else if (storyElement instanceof Token)
			return this.getTokenID((Token) storyElement);
		else if (storyElement instanceof Subplot)
			return this.getSubplotID((Subplot) storyElement);
		else
			return this.getFlavourListID((FlavourList) storyElement);
	}
	
	public Integer getExitPointID(ExitPoint exitPoint)
	{
		if (this.exitPoints.containsValue(exitPoint))
			return this.identifyExitPoint(exitPoint);
		else
		{
			Integer exitPointID = this.exitPoints.size();
			this.exitPoints.put(exitPointID, exitPoint);
			return exitPointID;
		}
	}
	
	public Integer getTokenID(Token token)
	{
		if (this.tokens.containsValue(token))
			return this.identifyToken(token);
		else
		{
			Integer tokenID = this.tokens.size();
			this.tokens.put(tokenID, token);
			return tokenID;
		}
	}
	
	public Integer getSubplotID(Subplot subplot)
	{
		if (this.subplots.containsValue(subplot))
			return this.identifySubPlot(subplot);
		else
		{
			Integer subplotID = this.subplots.size();
			this.subplots.put(subplotID, subplot);
			return subplotID;
		}
	}
	
	
	
	public Integer getFlavourListID(FlavourList flavourList)
	{
		if (this.flavourLists.containsValue(flavourList))
			return this.identifyFlavourList(flavourList);
		else
		{
			Integer flavourListID = this.flavourLists.size();
			this.flavourLists.put(flavourListID, flavourList);
			return flavourListID;
		}
	}
	
	public Integer addSubPlot(Subplot subplot)
	{
		Integer subplotID = this.subplots.size();
		this.subplots.put(subplotID, subplot);
		return subplotID;
	}
	
	public BranchLevel getBranchLevel(int level)
	{
		return this.branchLevels.get(level);
	}
	
	public static void main(String[] args)
	{
		BranchLevel branchLevel = new BranchLevel("Branch Level One");
		ArrayList<BranchLevel> branchLevels = new ArrayList<BranchLevel>();
		branchLevels.add(branchLevel);
		Scenario testScenario = new Scenario("Test Scenario", 1, 1, branchLevels);
		Scenario newScenario = new Scenario(testScenario.getJsonObject());
		Main.setMainScenario(newScenario);
		System.out.println(testScenario.getJsonObject());
		System.out.println(newScenario.getJsonObject());
	}
}
