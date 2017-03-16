package frontEnd;

import java.awt.Frame;

import javax.swing.JTextField;

import storyElements.options.BranchOption;
import storyElements.options.EndingOption;

public class NewEndingDialog extends FieldDialog
{	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final String DESCRIPTION = "Description";
	private static final String ENDING = "Ending";
	
	private JTextField descriptionField;
	private JTextField endingField;
	
	
	public NewEndingDialog(Frame owner, boolean modal, boolean optionHeader)
	{
		super(owner, modal);	
		this.setSize(WIDTH, HEIGHT);
		
		if (optionHeader)
		{
			this.descriptionField = new JTextField();
			this.addTextField(this.descriptionField, DESCRIPTION);
		}
		
		this.endingField = new JTextField();
		this.addTextField(this.endingField, ENDING);
	}

	public BranchOption getBranchOption()
	{
		return new BranchOption(this.descriptionField.getText());
	}
	
	public EndingOption getEndingOption()
	{
		return new EndingOption(this.endingField.getText());
	}
	
}
