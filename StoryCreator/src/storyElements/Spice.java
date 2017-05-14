package storyElements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

import frontEnd.AspectOptionPanel;
import main.Main;
import storyElements.optionLists.TwistList;
import storyElements.options.AspectOption;
import storyElements.options.BranchOption;
import storyElements.options.StoryElement;
import storyElements.options.TwistOption;

public class Spice implements JsonStructure, StoryElement
{
	private ArrayList<String> suggestions;
	private ArrayList<String> goodSuggestions;
	private ArrayList<String> badSuggestions;
	private HashMap<Integer, TwistList> twistLists;
	private HashMap<Integer, AspectType> aspectTypes;
	public HashMap<Integer, AspectType> getAspectTypes() {
		return aspectTypes;
	}

	private ArrayList<String> scenarioFilePaths = new ArrayList<String>();

	public Spice(JsonObject jsonObject)
	{
		this.twistLists = new HashMap<Integer, TwistList>();
		JsonObject twistsListObject = jsonObject.getJsonObject(Main.TWISTLISTS);
		for (Entry<String, JsonValue> entry:  twistsListObject.entrySet())
		{
			JsonObject twistListObject = (JsonObject) entry.getValue();
			JsonArray optionArray = twistListObject.getJsonArray(Main.OPTIONS);
			String description = twistListObject.getString(Main.DESCRIPTION);
			TwistList twistList = new TwistList(description);
			for (JsonValue optionJson : optionArray)
			{
				twistList.add(new TwistOption((JsonObject) optionJson));
			}
			this.twistLists.put(Integer.valueOf(entry.getKey()), twistList);
		}
		
		this.aspectTypes = new HashMap<Integer, AspectType>();
		JsonObject aspectTypesObject = jsonObject.getJsonObject(Main.ASPECTTYPES);
		for (String key: aspectTypesObject.keySet())
		{
			JsonObject innerObject = aspectTypesObject.getJsonObject(key);
			AspectType aspectType = new AspectType(innerObject);
			this.aspectTypes.put(Integer.valueOf(key), aspectType);
		}
		
		this.suggestions = Main.getStringsFromJsonArray(jsonObject.getJsonArray(Main.SUGGESTIONS));
		this.goodSuggestions = Main.getStringsFromJsonArray(jsonObject.getJsonArray(Main.GOOD_SUGGESTIONS));
		this.badSuggestions = Main.getStringsFromJsonArray(jsonObject.getJsonArray(Main.BAD_SUGGESTIONS));
		this.scenarioFilePaths = Main.getStringsFromJsonArray(jsonObject.getJsonArray(Main.SCENARIOFILEPATHS));
	}
	
	public Spice(HashMap<Integer, TwistList> initialTwistLists, ArrayList<String> suggestions, ArrayList<String> goodSuggestions, ArrayList<String> badSuggestions)
	{
		this.twistLists = initialTwistLists;
		this.aspectTypes = new HashMap<Integer, AspectType>();
		this.suggestions = suggestions;
		this.goodSuggestions = goodSuggestions;
		this.badSuggestions = badSuggestions;
	}
	
	public Integer getRandomAspectTypeID()
	{
		return Main.getRandomNumberInRange(this.aspectTypes.size());
	}
	
	public ArrayList<String> getScenarioFilePaths()
	{
		return scenarioFilePaths;
	}
	
	public void addScenarioFilePath(String filepath)
	{
		this.scenarioFilePaths.add(filepath);
	}
	
	public HashMap<Integer, TwistList> getTwistLists()
	{
		return twistLists;
	}

	public void addAspectType(Integer key, AspectType aspectType)
	{
		this.aspectTypes.put(key, aspectType);
	}
	
	@Override
	public JsonObject getJsonObject()
	{
		JsonArrayBuilder suggestionsBuilder = Main.getJsonBuilderForStrings(this.suggestions);
		JsonArrayBuilder goodSuggestionsBuilder = Main.getJsonBuilderForStrings(this.goodSuggestions);
		JsonArrayBuilder badSuggestionsBuilder = Main.getJsonBuilderForStrings(this.badSuggestions);		
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		
		JsonObjectBuilder twistListsBuilder = Json.createObjectBuilder();				
		for (Entry<Integer, TwistList> entry : twistLists.entrySet())
		{
			JsonObjectBuilder innerJsonObjectBuilder = entry.getValue().getJsonObjectBuilder();
			twistListsBuilder.add(String.valueOf(entry.getKey()), innerJsonObjectBuilder.build());
		}
		jsonObjectBuilder.add(Main.TWISTLISTS, twistListsBuilder.build());
		
		JsonObjectBuilder aspectTypesBuilder = Json.createObjectBuilder();	
		for (Entry<Integer, AspectType> entry : aspectTypes.entrySet())
		{
			JsonObjectBuilder innerJsonObjectBuilder = entry.getValue().getJsonObjectBuilder();
			aspectTypesBuilder.add(String.valueOf(entry.getKey()), innerJsonObjectBuilder.build());
		}
		jsonObjectBuilder.add(Main.ASPECTTYPES, aspectTypesBuilder.build());
		
		jsonObjectBuilder.add(Main.SUGGESTIONS, suggestionsBuilder.build());
		jsonObjectBuilder.add(Main.GOOD_SUGGESTIONS, goodSuggestionsBuilder.build());	
		jsonObjectBuilder.add(Main.BAD_SUGGESTIONS, badSuggestionsBuilder.build());
		jsonObjectBuilder.add(Main.SCENARIOFILEPATHS, this.getFilePathsJSON().build());
		return jsonObjectBuilder.build();
	}
	
	private JsonArrayBuilder getFilePathsJSON()
	{
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (String filePath : this.scenarioFilePaths)
		{
			jsonArrayBuilder.add(filePath);
		}
		return jsonArrayBuilder;
		
	}
	
	public String getSuggestion()
	{
		int rnd = Main.getRandomNumberInRange(this.suggestions.size());
		return this.suggestions.get(rnd);
	}
	
	public String getGoodSuggestion()
	{
		int rnd = Main.getRandomNumberInRange(this.goodSuggestions.size());
		return this.goodSuggestions.get(rnd);
	}
	
	public String getBadSuggestion()
	{
		int rnd = Main.getRandomNumberInRange(this.badSuggestions.size());
		return this.badSuggestions.get(rnd);
	}
	
	public Integer getAspectIDByType(AspectType aspectType)
	{
		for (Entry<Integer, AspectType> entry : this.aspectTypes.entrySet())
		{
			if (aspectType == entry.getValue())
				return entry.getKey();
		}
		return null;
	}
	
	@Override
	public String getDescription()
	{
		return "";
	}
	
	public static void main(String[] args)
	{
		TwistList twistList1 = new TwistList("Twist 1");
		twistList1.add(new TwistOption("Twist 1-1"));
		twistList1.add(new TwistOption("Twist 1-2"));
		twistList1.add(new TwistOption("Twist 1-3"));
		
		TwistList twistList2 = new TwistList("Twist 2");
		twistList2.add(new TwistOption("Twist 2-1"));
		twistList2.add(new TwistOption("Twist 2-2"));
		twistList2.add(new TwistOption("Twist 2-3"));
		
		HashMap<Integer, TwistList> twistLists = new HashMap<Integer, TwistList>();
		twistLists.put(0, twistList1);
		twistLists.put(1, twistList2);
		
		ArrayList<String> suggestions = new ArrayList<String>();
		suggestions.add("Suggestion 1");
		suggestions.add("Suggestion 2");
		suggestions.add("Suggestion 3");
		
		ArrayList<String> goodSuggestions = new ArrayList<String>();
		goodSuggestions.add("Good Suggestion 1");
		goodSuggestions.add("Good Suggestion 2");
		goodSuggestions.add("Good Suggestion 3");
		
		ArrayList<String> badSuggestions = new ArrayList<String>();
		badSuggestions.add("Bad Suggestion 1");
		badSuggestions.add("Bad Suggestion 2");
		badSuggestions.add("Bad Suggestion 3");
		
		Spice spice = new Spice(twistLists, suggestions, goodSuggestions, badSuggestions);
		
		HashMap<Integer, AspectList> aspectLists = new HashMap<Integer, AspectList>();
		HashMap<Integer, Aspect> aspects = new HashMap<Integer, Aspect>();
		HashMap<Integer, Integer> aspectQualities = new HashMap<Integer, Integer>();
		

		ArrayList<AspectOption> aspectOptions = new ArrayList<AspectOption>();
		aspectOptions.add(new AspectOption("One"));
		aspectLists.put(0, new AspectList(aspectOptions, "List One"));
		
		AspectType aspectType = new AspectType("Test", aspectLists, aspects);
		spice.addAspectType(0, aspectType);
		
		aspectQualities.put(0, 0);
		Aspect aspect = new Aspect(aspectType, "Aspect Test", aspectQualities);
		aspects.put(0, aspect);
	
		Spice newSpice = new Spice(spice.getJsonObject());
		System.out.println(spice.getJsonObject());
		System.out.println(newSpice.getJsonObject());
	}
}
