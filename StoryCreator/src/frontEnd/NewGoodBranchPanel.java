package frontEnd;

import main.Main;
import storyElements.options.OptionContentType;

public class NewGoodBranchPanel extends AbstractBranchPanel implements OptionContentPanel
{
	public NewGoodBranchPanel()
	{
		super("New Good Branch", Main.getMainSpice().getGoodSuggestion());
	}
	
	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.GOODEXITPOINT;
	}
}
