package frontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JTextField;

import main.Main;
import storyElements.optionLists.TwistList;
import storyElements.options.StoryElement;
import storyElements.options.TwistOption;
import frontEnd.fieldPanel.FieldPanel;

public class NewTwistListPanel extends MyOptionListPanel<TwistList>
{
	private static final String NEW_OPTION = "New Option";
	private static final String TWIST_LIST_DESCRIPTION = "Twist List Description";
	private JTextField descriptionField;
	
	public NewTwistListPanel()
	{
		super();
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, TWIST_LIST_DESCRIPTION);
		this.addInitialOptions(Main.INITIALOPTIONS_FOR_SPICE);
	}	
	
	public TwistList getResult()
	{
		ArrayList<TwistOption> twistOptions = new ArrayList<TwistOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			twistOptions.add(new TwistOption(textField.getText()));
		}
		return new TwistList(twistOptions, this.descriptionField.getText());
	}
}
