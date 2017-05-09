package frontEnd;

import javax.swing.JTextField;

import storyElements.options.AspectOption;

public class InitialAspectOptionPanel extends MyPanel<AspectOption>
{
	JTextField descriptionField;
	
	public InitialAspectOptionPanel()
	{
		super();
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, "Initial Option");
	}
	
	@Override
	public AspectOption getResult()
	{
		return new AspectOption(this.descriptionField.getText());
	}

}
