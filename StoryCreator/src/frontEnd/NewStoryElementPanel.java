package frontEnd;

import java.awt.Frame;

import storyElements.options.StoryElement;
import main.Main;

public abstract class NewStoryElementPanel<T extends StoryElement> extends FieldPanel<T>
{
	public NewStoryElementPanel(String header, String suggestion)
	{
		super(header);
		if (suggestion != null)
			this.addJLabel(suggestion);
	}
}
