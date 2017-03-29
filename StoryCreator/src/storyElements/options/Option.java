package storyElements.options;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;

public abstract class Option implements StoryElement
{	
	protected HashMap<OptionContentType, String> contentStringMap;
	protected HashMap<OptionContentType, Integer> contentIntegerMap;
	
	public String getDescription() {
		return contentStringMap.get(OptionContentType.DESCRIPTION);
	}

	public Option(HashMap<OptionContentType, String> contentStringMap, HashMap<OptionContentType, Integer> contentIntegerMap)
	{
		if (contentStringMap != null)
			this.contentStringMap = contentStringMap;
		else
			this.contentStringMap = new HashMap<OptionContentType, String>();
		if (contentIntegerMap != null)
			this.contentIntegerMap = contentIntegerMap;
		else
			this.contentIntegerMap = new HashMap<OptionContentType, Integer>();
	}
	
	public Option(String description)
	{
		this.contentIntegerMap = new HashMap<OptionContentType, Integer>();
		this.contentStringMap = new HashMap<OptionContentType, String>();
		this.contentStringMap.put(OptionContentType.DESCRIPTION, description);
	}
	
	public Option(JsonObject jsonObject)
	{
		this.contentStringMap = new HashMap<OptionContentType, String>();
		this.contentIntegerMap = new HashMap<OptionContentType, Integer>();
		for (OptionContentType optionContentType : OptionContentType.values())
		{
			if (jsonObject.containsKey(optionContentType.identifier))
			{
				if (optionContentType.objectClass == String.class)
					this.contentStringMap.put(optionContentType, jsonObject.getString(optionContentType.identifier));
				else
					this.contentIntegerMap.put(optionContentType, Main.processJsonInt(jsonObject, optionContentType.identifier));
			}
		}
	}
	
	public void setContentInteger(OptionContentType optionContentType, Integer content)
	{
		this.contentIntegerMap.put(optionContentType, content);
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		
		for(Entry<OptionContentType, String> contentString : this.contentStringMap.entrySet())
		{
			jsonObjectBuilder.add(contentString.getKey().identifier, contentString.getValue());
		}
		for(Entry<OptionContentType, Integer> contentInteger : this.contentIntegerMap.entrySet())
		{
			jsonObjectBuilder.add(contentInteger.getKey().identifier, contentInteger.getValue());
		}
		
		return jsonObjectBuilder;
	}
}
