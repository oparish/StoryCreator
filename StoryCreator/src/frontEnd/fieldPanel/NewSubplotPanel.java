package frontEnd.fieldPanel;

import java.util.ArrayList;

import javax.swing.JTextField;

import main.Main;
import storyElements.optionLists.Subplot;
import storyElements.options.BranchOption;
import storyElements.options.OptionContentType;
import storyElements.options.SubplotOption;

public class NewSubplotPanel extends FieldPanel<Subplot>
{
	private static final String SUBPLOT_DESCRIPTION = "Subplot Description";
	private static final String INITIAL_OPTION = "Initial Option";

	private JTextField subplotDescriptionField;
	private ArrayList<JTextField> initialOptionFields;
	
	public NewSubplotPanel(int branchLevel)
	{
		super(branchLevel);
		this.heading = "Subplot Option";
		this.subplotDescriptionField = new JTextField();
		this.addTextField(this.subplotDescriptionField, SUBPLOT_DESCRIPTION);
		
		this.initialOptionFields = new ArrayList<JTextField>();
		for (int i = 0; i < Main.INITIALOPTIONS_FOR_SUBPLOT; i++)
		{
			JTextField initialOptionField = new JTextField();
			this.initialOptionFields.add(initialOptionField);
			this.addTextField(initialOptionField, INITIAL_OPTION + " " + i);
		}	
	}

	@Override
	public Subplot getResult()
	{
		ArrayList<SubplotOption> initialOptions = new ArrayList<SubplotOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new SubplotOption(textField.getText()));
		}
		return new Subplot(initialOptions, this.subplotDescriptionField.getText());
	}

	@Override
	public OptionContentType getOptionContentType()
	{
		return OptionContentType.SUBPLOT;
	}

}
