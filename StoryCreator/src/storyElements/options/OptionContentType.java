package storyElements.options;

import main.Main;

public enum OptionContentType
{
	DESCRIPTION(Main.DESCRIPTION, String.class), BRANCH_LEVEL(Main.BRANCH_LEVEL, Integer.class), 
	FLAVOURLIST(Main.FLAVOURLIST, Integer.class), SUBPLOT(Main.SUBPLOT, Integer.class),
	TOKEN(Main.TOKEN, Integer.class), OBSTACLE(Main.OBSTACLE, Integer.class),
	EXITPOINT(Main.EXITPOINT, Integer.class), GOODEXITPOINT(Main.EXITPOINT, Integer.class),
	BADEXITPOINT(Main.EXITPOINT, Integer.class); 
	
	String identifier;
	Class objectClass;
	
	private OptionContentType(String identifier, Class objectClass)
	{
		this.identifier = identifier;
		this.objectClass = objectClass;
	}
}
