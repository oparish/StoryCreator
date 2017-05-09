package frontEnd.fieldPanel;

import java.util.ArrayList;

import javax.swing.JTextField;

import frontEnd.OptionListFieldPanel;
import main.Main;
import storyElements.Chance;
import storyElements.Scenario;
import storyElements.optionLists.FlavourList;
import storyElements.options.BranchOption;
import storyElements.options.FlavourOption;
import storyElements.options.OptionContentType;

public class FlavourListPanel extends OptionListFieldPanel<FlavourList>
{
	private static final String DESCRIPTION = "Description";
	
	JTextField descriptionField;
	
	
	public FlavourListPanel(int branchLevel)
	{
		super(branchLevel);
		this.heading = "Flavour List";
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION);
		this.addInitialOptions(Main.INITIALOPTIONS_FOR_FLAVOURLIST);	
	}
	
	@Override
	public FlavourList getResult()
	{
		ArrayList<FlavourOption> initialOptions = new ArrayList<FlavourOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new FlavourOption(textField.getText()));
		}	
		return new FlavourList(initialOptions, this.descriptionField.getText());
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.FLAVOURLIST;
	}

}
