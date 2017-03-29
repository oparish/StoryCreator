package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewGoodEndingPanel extends AbstractEndingPanel
{
	public NewGoodEndingPanel()
	{
		super("New Good Ending", Main.getMainSpice().getGoodSuggestion());	
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.GOODEXITPOINT;
	}
}
