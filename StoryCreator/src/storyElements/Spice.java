package storyElements;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;

import main.Main;
import storyElements.optionLists.TwistList;
import storyElements.options.BranchOption;
import storyElements.options.StoryElement;
import storyElements.options.TwistOption;

public class Spice implements JsonStructure, StoryElement
{
	private ArrayList<String> suggestions;
	private ArrayList<String> goodSuggestions;
	private ArrayList<String> badSuggestions;
	private TwistList twistList;
	
	public TwistList getTwistList() {
		return twistList;
	}

	public Spice(JsonObject jsonObject)
	{
		JsonObject twistListObject = jsonObject.getJsonObject(Main.TWISTLIST);
		JsonArray optionArray = twistListObject.getJsonArray(Main.OPTIONS);
		this.twistList = new TwistList();
		for (JsonValue optionJson : optionArray)
		{
			this.twistList.add(new TwistOption((JsonObject) optionJson));
		}
		
		this.suggestions = Main.getStringsFromJsonArray(jsonObject.getJsonArray(Main.SUGGESTIONS));
		this.goodSuggestions = Main.getStringsFromJsonArray(jsonObject.getJsonArray(Main.GOOD_SUGGESTIONS));
		this.badSuggestions = Main.getStringsFromJsonArray(jsonObject.getJsonArray(Main.BAD_SUGGESTIONS));
	}
	
	public Spice(TwistList initialTwistList, ArrayList<String> suggestions, ArrayList<String> goodSuggestions, ArrayList<String> badSuggestions)
	{
		this.twistList = initialTwistList;
		this.suggestions = suggestions;
		this.goodSuggestions = goodSuggestions;
		this.badSuggestions = badSuggestions;
	}

	@Override
	public JsonObject getJsonObject()
	{
		JsonArrayBuilder suggestionsBuilder = Main.getJsonBuilderForStrings(this.suggestions);
		JsonArrayBuilder goodSuggestionsBuilder = Main.getJsonBuilderForStrings(this.goodSuggestions);
		JsonArrayBuilder badSuggestionsBuilder = Main.getJsonBuilderForStrings(this.badSuggestions);
		
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add(Main.TWISTLIST, this.twistList.getJsonObjectBuilder().build());
		jsonObjectBuilder.add(Main.SUGGESTIONS, suggestionsBuilder.build());
		jsonObjectBuilder.add(Main.GOOD_SUGGESTIONS, goodSuggestionsBuilder.build());	
		jsonObjectBuilder.add(Main.BAD_SUGGESTIONS, badSuggestionsBuilder.build());	
		return jsonObjectBuilder.build();
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
	
	@Override
	public String getDescription()
	{
		return "";
	}
	
	public static void main(String[] args)
	{
		TwistList twistList = new TwistList();
		twistList.add(new TwistOption("Twist 1"));
		twistList.add(new TwistOption("Twist 2"));
		twistList.add(new TwistOption("Twist 3"));
		
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
		
		Spice spice = new Spice(twistList, suggestions, goodSuggestions, badSuggestions);
		Spice newSpice = new Spice(spice.getJsonObject());
		System.out.println(spice.getJsonObject());
		System.out.println(newSpice.getJsonObject());
	}
}
