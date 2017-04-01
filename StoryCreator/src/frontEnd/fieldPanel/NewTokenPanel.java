package frontEnd.fieldPanel;

import java.awt.Dimension;

import javax.swing.JTextField;

import storyElements.Token;
import storyElements.options.BranchOption;
import storyElements.options.OptionContentType;

public class NewTokenPanel extends FieldPanel<Token>
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public NewTokenPanel(int branchLevel)
	{
		super(branchLevel);
		this.heading = "New Token";
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION);
	}
	
	public Token getResult()
	{
		return new Token(this.descriptionField.getText());
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.TOKEN;
	}
}
