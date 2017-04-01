package frontEnd.fieldPanel;

import javax.swing.JTextField;

import storyElements.Token;
import storyElements.options.OptionContentType;

public class NewObstaclePanel extends NewStoryElementPanel<Token>
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public NewObstaclePanel(int branchLevel)
	{
		super(branchLevel, null);
		this.heading = "New Obstacle";
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
		return OptionContentType.OBSTACLE;
	}
}
