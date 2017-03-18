package frontEnd;

import java.awt.Frame;

import main.Main;

public class NewStoryElementPanel extends FieldPanel
{
	public NewStoryElementPanel()
	{
		super();
		this.addJLabel(Main.getMainSpice().getSuggestion());
	}
}
