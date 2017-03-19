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
		
		JsonArray suggestionArray = jsonObject.getJsonArray(Main.SUGGESTIONS);
		this.suggestions = new ArrayList<String>();
		for (JsonValue optionJson : suggestionArray)
		{
			this.suggestions.add(((JsonString) optionJson).getString());
		}
	}
	
	public Spice(TwistList initialTwistList, ArrayList<String> suggestions)
	{
		this.twistList = initialTwistList;
		this.suggestions = suggestions;
	}

	@Override
	public JsonObject getJsonObject()
	{
		JsonArrayBuilder suggestionsBuilder = Json.createArrayBuilder();
		for (String suggestion: this.suggestions)
		{
			suggestionsBuilder.add(suggestion);
		}
		
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add(Main.TWISTLIST, this.twistList.getJsonObjectBuilder().build());
		jsonObjectBuilder.add(Main.SUGGESTIONS, suggestionsBuilder.build());	
		return jsonObjectBuilder.build();
	}
	
	public String getSuggestion()
	{
		int rnd = Main.getRandomNumberInRange(this.suggestions.size());
		return this.suggestions.get(rnd);
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
		
		Spice spice = new Spice(twistList, suggestions);
		Spice newSpice = new Spice(spice.getJsonObject());
		System.out.println(spice.getJsonObject());
		System.out.println(newSpice.getJsonObject());
	}
}
