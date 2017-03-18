package frontEnd;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JTextField;

import main.Main;
import storyElements.Scenario;
import storyElements.optionLists.Branch;
import storyElements.options.BranchOption;

public class NewBranchDialog extends NewStoryElementDialog
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final String BRANCH_DESCRIPTION = "Branch Description";
	private static final String INITIAL_OPTION = "Initial Option";
	private static final String OPTION_HEADER = "Option Header";
	
	private JTextField optionHeaderField;
	private JTextField branchDescriptionField;
	private ArrayList<JTextField> initialOptionFields;
	
	public NewBranchDialog(Frame owner, boolean modal, int initialOptionNumber, boolean optionHeader)
	{
		super(owner, modal);
		this.setSize(WIDTH, HEIGHT);	
		
		if (optionHeader)
		{
			this.optionHeaderField = new JTextField();
			this.addTextField(this.optionHeaderField, OPTION_HEADER);
		}
		
		this.branchDescriptionField = new JTextField();
		this.addTextField(this.branchDescriptionField, BRANCH_DESCRIPTION);
		
		this.initialOptionFields = new ArrayList<JTextField>();
		for (int i = 0; i < initialOptionNumber; i++)
		{
			JTextField initialOptionField = new JTextField();
			this.initialOptionFields.add(initialOptionField);
			this.addTextField(initialOptionField, INITIAL_OPTION + " " + i);
		}
	}
	
	public String getOptionHeader()
	{
		return this.optionHeaderField.getText();
	}
	
	public Branch getNewBranch(int branchLevel)
	{
		ArrayList<BranchOption> initialOptions = new ArrayList<BranchOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new BranchOption(textField.getText()));
		}
		return new Branch(initialOptions, this.branchDescriptionField.getText(), branchLevel);
	}
}
