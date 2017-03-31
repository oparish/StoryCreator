package frontEnd.fieldPanel;

import java.awt.Frame;

import javax.swing.JTextField;

import main.Main;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.OptionContentType;

public class NewEndingPanel extends AbstractEndingPanel
{		
	public NewEndingPanel()
	{
		super(Main.getMainSpice().getSuggestion());
		this.heading = "New Ending";
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.EXITPOINT;
	}
}
