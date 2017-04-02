package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewGoodBranchPanel extends AbstractBranchPanel
{
	public NewGoodBranchPanel(int branchLevel)
	{
		super(branchLevel, Main.getMainSpice().getGoodSuggestion());
		this.heading = "New Good Branch";
	}

}
