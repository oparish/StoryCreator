package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewBadBranchPanel extends AbstractBranchPanel
{
	public NewBadBranchPanel(int branchLevel)
	{
		super(branchLevel, Main.getMainSpice().getBadSuggestion());
		this.heading = "New Bad Branch";
	}
}
