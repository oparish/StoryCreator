package storyElements.options;

import java.lang.reflect.InvocationTargetException;

import storyElements.AspectList;
import storyElements.AspectType;
import frontEnd.AspectListPanel;
import frontEnd.AspectOptionPanel;
import frontEnd.AspectTypePanel;
import frontEnd.InitialAspectOptionPanel;
import frontEnd.MyPanel;
import frontEnd.fieldPanel.FieldPanel;

public enum ExpandingContentType 
{
	ASPECTOPTION(AspectOption.class, InitialAspectOptionPanel.class, "New Aspect Option"), 
	ASPECTLIST(AspectList.class, AspectListPanel.class, "New Aspect List"),
	ASPECTTYPE(AspectType.class, AspectTypePanel.class, "New Aspect Type");
	
	Class<? extends StoryElement> storyElementClass;
	public Class<? extends StoryElement> getStoryElementClass() {
		return storyElementClass;
	}

	public Class<? extends MyPanel> getFieldPanelClass() {
		return fieldPanelClass;
	}

	Class<? extends MyPanel> fieldPanelClass;
	String text;
	
	public String getText() {
		return text;
	}

	private ExpandingContentType(Class<? extends StoryElement> storyElementClass,
			Class<? extends MyPanel> fieldPanelClass, String text)
	{
		this.storyElementClass = storyElementClass;
		this.fieldPanelClass = fieldPanelClass;
		this.text = text;
	}
	
	public MyPanel getNewPanel()
	{
		try
		{
			return this.fieldPanelClass.getConstructor().newInstance();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
