package frontEnd;

import java.awt.Dimension;

import javax.swing.JTextField;

import storyElements.Token;
import storyElements.options.BranchOption;

public class NewTokenPanel extends NewStoryElementPanel<Token>
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public NewTokenPanel()
	{
		super("New Token", null);
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION);
	}
	
	public Token getResult()
	{
		return new Token(this.descriptionField.getText());
	}
}
