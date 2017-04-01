package frontEnd.fieldPanel;

import java.awt.Frame;

import storyElements.options.StoryElement;
import main.Main;

public abstract class NewStoryElementPanel<T extends StoryElement> extends FieldPanel<T>
{
	protected String suggestion = null;

	public String getSuggestion() {
		return suggestion;
	}

	public NewStoryElementPanel(int branchLevel)
	{
		super(branchLevel);
	}
}
