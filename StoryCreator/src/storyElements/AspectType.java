package storyElements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import main.Main;
import storyElements.options.StoryElement;

public class AspectType implements StoryElement
{
	private String description;
	private HashMap<Integer, AspectList> aspectLists;
	private HashMap<Integer, Aspect> aspects;
	
	public AspectType(String description, HashMap<Integer, AspectList> aspectLists, HashMap<Integer, Aspect> aspects)
	{
		this.description = description;
		this.aspectLists = aspectLists;
		this.aspects = aspects;
		this.registerAspectLists(aspectLists.values(), this);
	}
	
	public AspectType(JsonObject jsonObject)
	{
		this.description = jsonObject.getString(Main.DESCRIPTION);
		this.aspectLists = new HashMap<Integer, AspectList>();
		this.aspects = new HashMap<Integer, Aspect>();
		
		JsonObject aspectListsObject = jsonObject.getJsonObject(Main.ASPECTLISTS);
		JsonObject aspectsObject = jsonObject.getJsonObject(Main.ASPECTS);
		
		for (String key : aspectListsObject.keySet())
		{
			int intKey = Integer.parseInt(key);
			JsonObject innerObject = aspectListsObject.getJsonObject(key);
			this.aspectLists.put(intKey, new AspectList(innerObject));
		}
		
		for (String key : aspectsObject.keySet())
		{
			int intKey = Integer.parseInt(key);
			JsonObject innerObject = aspectsObject.getJsonObject(key);
			this.aspects.put(intKey, new Aspect(this, innerObject));
		}
		this.registerAspectLists(aspectLists.values(), this);
	}
	
	public void registerAspectLists(Collection<AspectList> aspectLists, AspectType aspectType)
	{
		for (AspectList aspectList : aspectLists)
		{
			aspectList.setAspectType(this);
		}	
	}
	
	public Collection<Aspect> getAspectCollection()
	{
		return this.aspects.values();
	}
	
	public int getAspectsSize()
	{
		return this.aspects.size();
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		
		JsonObjectBuilder aspectListsBuilder = Json.createObjectBuilder();
		for (Entry<Integer, AspectList> entry : this.aspectLists.entrySet())
		{
			aspectListsBuilder.add(entry.getKey().toString(), entry.getValue().getJsonObjectBuilder());
		}
		jsonObjectBuilder.add(Main.ASPECTLISTS, aspectListsBuilder.build());
		
		JsonObjectBuilder aspectsBuilder = Json.createObjectBuilder();
		for (Entry<Integer, Aspect> entry : this.aspects.entrySet())
		{
			aspectsBuilder.add(entry.getKey().toString(), entry.getValue().getJsonObjectBuilder());
		}
		jsonObjectBuilder.add(Main.ASPECTS, aspectsBuilder.build());
		
		jsonObjectBuilder.add(Main.DESCRIPTION, this.description);
		return jsonObjectBuilder;
	}

	public Aspect getAspect(Integer key)
	{
		return this.aspects.get(key);
	}
	
	@Override
	public String getDescription()
	{
		return this.description;
	}
	
	public HashMap<Integer, AspectList> getAspectLists()
	{
		return this.aspectLists;
	}
	
	public void addAspect(Aspect aspect)
	{
		this.aspects.put(this.aspects.size(), aspect);
	}
}
