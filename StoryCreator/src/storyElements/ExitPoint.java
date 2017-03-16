package storyElements;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import storyElements.options.StoryElement;
import frontEnd.EditorDialog;

public interface ExitPoint extends StoryElement
{
	public abstract JsonObjectBuilder getJsonObjectBuilder();
}
