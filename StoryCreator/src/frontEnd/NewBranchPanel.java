package frontEnd;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JTextField;

import main.Main;
import storyElements.Scenario;
import storyElements.optionLists.Branch;
import storyElements.options.BranchOption;

public class NewBranchPanel extends NewStoryElementPanel<Branch>
{
	private static final String BRANCH_DESCRIPTION = "Branch Description";
	private static final String INITIAL_OPTION = "Initial Option";

	private JTextField branchDescriptionField;
	private ArrayList<JTextField> initialOptionFields;
	
	public NewBranchPanel()
	{
		super("New Branch");	
		this.branchDescriptionField = new JTextField();
		this.addTextField(this.branchDescriptionField, BRANCH_DESCRIPTION);
		
		this.initialOptionFields = new ArrayList<JTextField>();
		for (int i = 0; i < Main.INITIALOPTIONS_FOR_BRANCH; i++)
		{
			JTextField initialOptionField = new JTextField();
			this.initialOptionFields.add(initialOptionField);
			this.addTextField(initialOptionField, INITIAL_OPTION + " " + i);
		}	
	}
	
	public Branch getResult()
	{
		ArrayList<BranchOption> initialOptions = new ArrayList<BranchOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new BranchOption(textField.getText()));
		}
		return new Branch(initialOptions, this.branchDescriptionField.getText(), Main.getMainScenario().getNextBranch());
	}
}
