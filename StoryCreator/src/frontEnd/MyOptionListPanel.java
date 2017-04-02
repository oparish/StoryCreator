package frontEnd;

import java.util.ArrayList;

import javax.swing.JTextField;

public abstract class MyOptionListPanel<T> extends MyPanel<T>
{
	protected static final String INITIAL_OPTION = "Initial Option";	
	protected ArrayList<JTextField> initialOptionFields;
	
	public MyOptionListPanel()
	{
		super();
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
	
	public static ArrayList<String> getStringsFromFields(ArrayList<JTextField> fields)
	{
		ArrayList<String> strings = new ArrayList<String>();
		for (JTextField field : fields)
		{
			strings.add(field.getText());
		}
		return strings;
	}
}
