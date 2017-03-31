package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewBadEndingPanel extends AbstractEndingPanel
{
	public NewBadEndingPanel()
	{
		super(Main.getMainSpice().getBadSuggestion());
		this.heading = "New Bad Ending";
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.BADEXITPOINT;
	}
}
