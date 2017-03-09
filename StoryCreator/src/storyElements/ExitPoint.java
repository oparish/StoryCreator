package storyElements;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import frontEnd.EditorDialog;

public interface ExitPoint
{
	public abstract JsonObjectBuilder getJsonObjectBuilder();
	
	public abstract void useAsExitPoint (EditorDialog editorDialog);
}
