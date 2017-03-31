package frontEnd.fieldPanel;

import main.Main;
import storyElements.options.OptionContentType;

public class NewGoodBranchPanel extends AbstractBranchPanel implements OptionContentPanel
{
	public NewGoodBranchPanel()
	{
		super(Main.getMainSpice().getGoodSuggestion());
		this.heading = "New Good Branch";
	}
	
	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.GOODEXITPOINT;
	}
}
