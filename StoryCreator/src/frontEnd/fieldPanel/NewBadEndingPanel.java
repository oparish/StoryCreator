package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewBadEndingPanel extends AbstractEndingPanel
{
	public NewBadEndingPanel(int branchLevel)
	{
		super(branchLevel, Main.getMainSpice().getBadSuggestion());
		this.heading = "New Bad Ending";
	}
}
