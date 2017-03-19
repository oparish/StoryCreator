package frontEnd;

import java.awt.Frame;

import javax.swing.JTextField;

import main.Main;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;

public class NewEndingPanel extends FieldPanel<EndingOption>
{	
	private static final String ENDING = "Ending";
	
	private JTextField endingField;
	
	public NewEndingPanel()
	{
		super();	
		
		this.endingField = new JTextField();
		this.addTextField(this.endingField, ENDING);
	}
	
	public EndingOption getResult()
	{
		return new EndingOption(this.endingField.getText(), Main.getMainScenario().getNextBranch());
	}
	
}
