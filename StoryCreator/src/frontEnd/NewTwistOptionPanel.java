package frontEnd;

import javax.swing.JTextField;

import frontEnd.fieldPanel.NewStoryElementPanel;
import storyElements.options.BranchOption;
import storyElements.options.TwistOption;

public class NewTwistOptionPanel extends NewStoryElementPanel<TwistOption>
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public NewTwistOptionPanel()
	{
		super(null);
		this.heading = "Twist Option";
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION);	
	}
	
	public TwistOption getResult()
	{
		return new TwistOption(this.descriptionField.getText());
	}
}
