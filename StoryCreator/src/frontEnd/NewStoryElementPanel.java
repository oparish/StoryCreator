package frontEnd;

import java.awt.Frame;

import storyElements.options.StoryElement;
import main.Main;

public abstract class NewStoryElementPanel<T extends StoryElement> extends FieldPanel<T>
{
	public NewStoryElementPanel()
	{
		super();
		if (Main.getMainSpice() != null)
			this.addJLabel(Main.getMainSpice().getSuggestion());
	}
}
