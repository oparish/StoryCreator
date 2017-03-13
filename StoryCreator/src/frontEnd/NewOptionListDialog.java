package frontEnd;

import java.awt.Frame;
import java.util.ArrayList;

import javax.swing.JTextField;

public class NewOptionListDialog extends FieldDialog
{
	protected static final String INITIAL_OPTION = "Initial Option";	
	protected ArrayList<JTextField> initialOptionFields;
	
	public NewOptionListDialog(Frame owner, boolean modal)
	{
		super(owner, modal);
	}
	
	protected void addInitialOptions(int initialOptionNumber)
	{
		this.initialOptionFields = new ArrayList<JTextField>();
		for (int i = 0; i < initialOptionNumber; i++)
		{
			JTextField initialOptionField = new JTextField();
			this.initialOptionFields.add(initialOptionField);
			this.addTextField(initialOptionField, INITIAL_OPTION + " " + i);
		}
	}

}
