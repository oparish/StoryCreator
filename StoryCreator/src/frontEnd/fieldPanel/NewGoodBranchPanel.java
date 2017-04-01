package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewGoodBranchPanel extends AbstractBranchPanel implements OptionContentPanel
{
	public NewGoodBranchPanel(int branchLevel)
	{
		super(branchLevel, Main.getMainSpice().getGoodSuggestion());
		this.heading = "New Good Branch";
	}
	
	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.GOODEXITPOINT;
	}
}
