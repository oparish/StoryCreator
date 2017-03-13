package storyElements;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import main.Main;
import storyElements.optionLists.TwistList;
import storyElements.options.BranchOption;
import storyElements.options.TwistOption;

public class Spice implements JsonStructure
{
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
	}
	
	public Spice(TwistList initialTwistList)
	{
		this.twistList = initialTwistList;
	}

	@Override
	public JsonObject getJsonObject()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add(Main.TWISTLIST, this.twistList.getJsonObjectBuilder().build());
		return jsonObjectBuilder.build();
	}
	
	public static void main(String[] args)
	{
		TwistList twistList = new TwistList();
		twistList.add(new TwistOption("Twist 1"));
		twistList.add(new TwistOption("Twist 2"));
		twistList.add(new TwistOption("Twist 3"));
		Spice spice = new Spice(twistList);
		Spice newSpice = new Spice(spice.getJsonObject());
		System.out.println(spice.getJsonObject());
		System.out.println(newSpice.getJsonObject());
	}
}
