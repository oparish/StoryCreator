package storyElements.options;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;

public abstract class Option implements StoryElement
{	
	protected String description;
	protected HashMap<OptionContentType, Integer> contentIntegerMap;
	
	public String getDescription() {
		return this.description;
	}

	public Option(String description, HashMap<OptionContentType, Integer> contentIntegerMap)
	{
		if (contentIntegerMap != null)
			this.contentIntegerMap = contentIntegerMap;
		else
			this.contentIntegerMap = new HashMap<OptionContentType, Integer>();
		this.description = description;
	}
	
	public Option(String description)
	{
		this.contentIntegerMap = new HashMap<OptionContentType, Integer>();
		this.description = description;
	}
	
	public Option(JsonObject jsonObject)
	{
		this.contentIntegerMap = new HashMap<OptionContentType, Integer>();
		for (OptionContentType optionContentType : OptionContentType.values())
		{
			if (jsonObject.containsKey(optionContentType.identifier))
			{
				this.contentIntegerMap.put(optionContentType, Main.processJsonInt(jsonObject, optionContentType.identifier));
			}
		}
		this.description = jsonObject.getString(Main.DESCRIPTION);
	}
	
	public void setContentInteger(OptionContentType optionContentType, Integer content)
	{
		this.contentIntegerMap.put(optionContentType, content);
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		for(Entry<OptionContentType, Integer> contentInteger : this.contentIntegerMap.entrySet())
		{
			jsonObjectBuilder.add(contentInteger.getKey().identifier, contentInteger.getValue());
		}
		
		return jsonObjectBuilder;
	}
}
