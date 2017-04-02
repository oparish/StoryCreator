package frontEnd.fieldPanel;

import javax.swing.JTextField;

import frontEnd.MyPanel;
import main.Main;
import storyElements.options.EndingOption;

public abstract class AbstractEndingPanel extends MyPanel<EndingOption>
{
	private static final String ENDING = "Ending";
	
	private JTextField endingField;
	
	public AbstractEndingPanel(int branchLevel, String suggestion)
	{
		super();
		this.endingField = new JTextField();
		this.addTextField(this.endingField, ENDING);	
	}
		
	public EndingOption getResult()
	{
		return new EndingOption(this.endingField.getText());
	}
}
