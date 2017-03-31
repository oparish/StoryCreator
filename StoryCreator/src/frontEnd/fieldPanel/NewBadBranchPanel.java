package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewBadBranchPanel extends AbstractBranchPanel implements OptionContentPanel
{
	public NewBadBranchPanel()
	{
		super(Main.getMainSpice().getBadSuggestion());
		this.heading = "New Bad Branch";
	}
	
	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.BADEXITPOINT;
	}
}
