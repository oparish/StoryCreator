package frontEnd.fieldPanel;

import java.util.ArrayList;

import javax.swing.JTextField;

import frontEnd.MyPanel;
import frontEnd.StoryElementList;
import main.Main;
import storyElements.AspectType;
import storyElements.optionLists.Branch;
import storyElements.options.BranchOption;
import storyElements.options.OptionContentType;

public abstract class AbstractBranchPanel extends MyPanel<Branch>
{
	private static final String BRANCH_DESCRIPTION = "Branch Description";
	private static final String INITIAL_OPTION = "Initial Option";

	private JTextField branchDescriptionField;
	private ArrayList<JTextField> initialOptionFields;
	private StoryElementList<AspectType> aspectTypes;
	
	public AbstractBranchPanel(int branchLevel, String suggestion)
	{
		super();
		this.branchDescriptionField = new JTextField();
		this.addTextField(this.branchDescriptionField, BRANCH_DESCRIPTION);
		
		this.aspectTypes = StoryElementList.create(Main.getMainSpice().getAspectTypes().values());
		this.addStoryElementList(this.aspectTypes);
		
		this.initialOptionFields = new ArrayList<JTextField>();
		for (int i = 0; i < Main.INITIALOPTIONS_FOR_BRANCH; i++)
		{
			JTextField initialOptionField = new JTextField();
			this.initialOptionFields.add(initialOptionField);
			this.addTextField(initialOptionField, INITIAL_OPTION + " " + i);
		}	
	}
	
	private AspectType getAspectType()
	{
		AspectType selectedType = this.aspectTypes.getSelectedElement();
		if (selectedType == null)
			return this.aspectTypes.getModel().getElementAt(0);
		else
			return selectedType;
	}
	
	public Branch getResult()
	{
		ArrayList<BranchOption> initialOptions = new ArrayList<BranchOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new BranchOption(textField.getText()));
		}
		return new Branch(initialOptions, this.branchDescriptionField.getText(), Main.getMainScenario().getNextBranch(), 
				Main.getMainSpice().getAspectIDByType(this.getAspectType()));
	}
}
