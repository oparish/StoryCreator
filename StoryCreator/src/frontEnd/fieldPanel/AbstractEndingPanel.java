package frontEnd.fieldPanel;

import javax.swing.JTextField;

import main.Main;
import storyElements.options.EndingOption;

public abstract class AbstractEndingPanel extends NewStoryElementPanel<EndingOption> implements OptionContentPanel
{
	private static final String ENDING = "Ending";
	
	private JTextField endingField;
	
	public AbstractEndingPanel(String header, String suggestion)
	{
		super(header, suggestion);	
		this.endingField = new JTextField();
		this.addTextField(this.endingField, ENDING);	
	}
		
	public EndingOption getResult()
	{
		return new EndingOption(this.endingField.getText());
	}
}
