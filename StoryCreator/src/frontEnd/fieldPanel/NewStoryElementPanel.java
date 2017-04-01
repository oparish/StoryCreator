package frontEnd.fieldPanel;

import java.awt.Frame;

import storyElements.options.StoryElement;
import main.Main;

public abstract class NewStoryElementPanel<T extends StoryElement> extends FieldPanel<T>
{
	public NewStoryElementPanel(int branchLevel, String suggestion)
	{
		super(branchLevel);
		if (suggestion != null)
			this.addJLabel(suggestion);
	}
}
