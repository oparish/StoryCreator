package frontEnd;

import javax.swing.JTextField;

import storyElements.AspectList;
import storyElements.BranchLevel;
import storyElements.options.AspectOption;

public class BranchLevelPanel extends MyPanel<BranchLevel> 
{
	private JTextField descriptionField;

	public BranchLevelPanel()
	{
		super();
		this.heading = "Branch Level";
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, "Description");
	}
	
	@Override
	public BranchLevel getResult()
	{
		return new BranchLevel(this.descriptionField.getText());
	}

}
