package frontEnd.fieldPanel;

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

public class NewBranchPanel extends AbstractBranchPanel
{
	public NewBranchPanel(int branchLevel)
	{
		super(branchLevel, Main.getMainSpice().getSuggestion());
		this.heading = "New Branch";
	}
	
	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.EXITPOINT;
	}
}
