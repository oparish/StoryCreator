package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewGoodEndingPanel extends AbstractEndingPanel
{
	public NewGoodEndingPanel(int branchLevel)
	{
		super(branchLevel, Main.getMainSpice().getGoodSuggestion());
		this.heading = "New Good Ending";
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.GOODEXITPOINT;
	}
}
