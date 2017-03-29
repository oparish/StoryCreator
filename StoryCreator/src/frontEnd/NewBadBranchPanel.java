package frontEnd;

import main.Main;
import storyElements.options.OptionContentType;

public class NewBadBranchPanel extends AbstractBranchPanel implements OptionContentPanel
{
	public NewBadBranchPanel()
	{
		super("New Bad Branch", Main.getMainSpice().getBadSuggestion());
	}
	
	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.BADEXITPOINT;
	}
}
