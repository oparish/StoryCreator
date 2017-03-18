package frontEnd;

import java.awt.Frame;

import javax.swing.JTextField;

import main.Main;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;

public class NewEndingPanel extends FieldPanel
{	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private static final String ENDING = "Ending";
	
	private JTextField endingField;
	
	public NewEndingPanel()
	{
		super();	
		//this.setSize(WIDTH, HEIGHT);
		
		this.endingField = new JTextField();
		this.addTextField(this.endingField, ENDING);
	}
	
	public EndingOption getEndingOption()
	{
		return new EndingOption(this.endingField.getText(), Main.getMainScenario().getNextBranch());
	}
	
}
