package frontEnd;

import main.Main;
import storyElements.options.OptionContentType;

public class NewBadEndingPanel extends AbstractEndingPanel
{
	public NewBadEndingPanel()
	{
		super("New Bad Ending", Main.getMainSpice().getBadSuggestion());	
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.BADEXITPOINT;
	}
}
