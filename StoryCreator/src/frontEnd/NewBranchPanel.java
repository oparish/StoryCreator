package frontEnd;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;

import main.Main;
import storyElements.Scenario;
import storyElements.optionLists.Branch;
import storyElements.options.BranchOption;
import storyElements.options.OptionContentType;

public class NewBranchPanel extends AbstractBranchPanel implements OptionContentPanel
{
	public NewBranchPanel()
	{
		super("New Branch", Main.getMainSpice().getSuggestion());
	}
	
	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.EXITPOINT;
	}
}
