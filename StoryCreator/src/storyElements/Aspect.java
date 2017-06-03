package storyElements;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import frontEnd.InitialSpiceDialog;
import main.Main;
import storyElements.options.AspectOption;
import storyElements.options.StoryElement;

public class Aspect implements StoryElement
{
	AspectType aspectType;
	String description;
	HashMap<Integer, Integer> qualities;
	
	public Aspect(AspectType aspectType, String description, HashMap<Integer, Integer> qualities)
	{
		this.aspectType = aspectType;
		this.description = description;
		this.qualities = qualities;
	}
	
	public Aspect(AspectType aspectType, JsonObject jsonObject)
	{
		this.aspectType = aspectType;
		this.qualities = new HashMap<Integer, Integer>();
		JsonObject qualitiesObject = jsonObject.getJsonObject(Main.QUALITIES);
		for (String key : qualitiesObject.keySet())
		{
			this.qualities.put(Integer.valueOf(key), qualitiesObject.getInt(key));
		}
		this.description = jsonObject.getString(Main.DESCRIPTION);
	}

	@Override
	public String getDescription()
	{
		return this.description;
	}
	
	public String getQualitiesDescription()
	{
		StringBuilder stringBuilder = new StringBuilder();
		for (Entry<Integer, Integer> entry : this.qualities.entrySet())
		{
			AspectList aspectList = this.aspectType.getAspectLists().get(entry.getKey());
			stringBuilder.append(aspectList.getDescription() + " : " + aspectList.get(entry.getValue()).getDescription() + "\r\n");
		}
		return stringBuilder.toString();
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		
		JsonObjectBuilder qualitiesBuilder = Json.createObjectBuilder();
		for (Entry<Integer, Integer> entry : this.qualities.entrySet())
		{
			qualitiesBuilder.add(entry.getKey().toString(), entry.getValue());
		}
		jsonObjectBuilder.add(Main.QUALITIES, qualitiesBuilder.build());
		
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		return jsonObjectBuilder;
	}
	
	public static void main(String[] args)
	{
		InitialSpiceDialog initialSpiceDialog = new InitialSpiceDialog();
        Main.showWindowInCentre(initialSpiceDialog); 
        AspectType aspectType = Main.getMainSpice().getAspectTypes().get(0);
        for (AspectList aspectList : aspectType.getAspectLists().values())
        {
        	AspectOption aspectOption = (AspectOption) aspectList.get(Main.getRandomNumberInRange(aspectList.size()));
        	System.out.println(aspectOption.getDescription());
        }
	}
}
