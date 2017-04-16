package storyElements.options;

import com.sun.media.jfxmedia.events.NewFrameEvent;

import frontEnd.fieldPanel.BadExitPointPanel;
import frontEnd.fieldPanel.ExitPointPanel;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.FlavourListPanel;
import frontEnd.fieldPanel.GoodExitPointPanel;
import frontEnd.fieldPanel.NewBadBranchPanel;
import frontEnd.fieldPanel.NewBranchPanel;
import frontEnd.fieldPanel.NewEndingPanel;
import frontEnd.fieldPanel.NewGoodBranchPanel;
import frontEnd.fieldPanel.NewObstaclePanel;
import frontEnd.fieldPanel.NewOptionPanel;
import frontEnd.fieldPanel.NewSubplotPanel;
import frontEnd.fieldPanel.NewTokenPanel;
import main.Main;

public enum OptionContentType
{
	FLAVOURLIST(Main.FLAVOURLIST, Integer.class, FlavourListPanel.class),
	TOKEN(Main.TOKEN, Integer.class, NewTokenPanel.class), OBSTACLE(Main.OBSTACLE, Integer.class, NewObstaclePanel.class),
	EXITPOINT(Main.EXITPOINT, Integer.class, ExitPointPanel.class), 
	GOODEXITPOINT(Main.EXITPOINT, Integer.class, GoodExitPointPanel.class),
	BADEXITPOINT(Main.EXITPOINT, Integer.class, BadExitPointPanel.class),
	SUBPLOT(Main.SUBPLOT, Integer.class, NewSubplotPanel.class); 
	
	String identifier;
	Class objectClass;
	Class<? extends FieldPanel> fieldPanelClass;
	
	public String getIdentifier() {
		return identifier;
	}

	public Class getObjectClass() {
		return objectClass;
	}

	public Class<? extends FieldPanel> getFieldPanelClass() {
		return fieldPanelClass;
	}
	
	private OptionContentType(String identifier, Class objectClass, Class<? extends FieldPanel> fieldPanelClass)
	{
		this.identifier = identifier;
		this.objectClass = objectClass;
		this.fieldPanelClass = fieldPanelClass;
	}
}
