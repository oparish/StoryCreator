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
		super("New Ending", Main.getMainSpice().getSuggestion());	
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.EXITPOINT;
	}
}
