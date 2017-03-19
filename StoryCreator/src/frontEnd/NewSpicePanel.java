package frontEnd;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JTextField;

import storyElements.Spice;
import storyElements.optionLists.TwistList;
import storyElements.options.BranchOption;
import storyElements.options.StoryElement;
import storyElements.options.TwistOption;
import main.Main;

public class NewSpicePanel extends NewOptionListPanel<Spice>
{
	ArrayList<JTextField> suggestionFields;
	
	public NewSpicePanel()
	{
		super();
		this.addInitialOptions(Main.INITIALOPTIONS_FOR_SPICE);
		
		this.suggestionFields = new ArrayList<JTextField>();
		for (int i = 0; i < Main.SUGGESTION_NUMBER; i++)
		{
			JTextField newField = new JTextField();
			this.suggestionFields.add(newField);
			this.addTextField(newField, "Suggestion " + i);
		}
	}

	public Spice getResult()
	{
		TwistList twistList = new TwistList();
		for (JTextField textField : this.initialOptionFields)
		{
			twistList.add(new TwistOption(textField.getText()));
		}
		ArrayList<String> suggestions = new ArrayList<String>();
		for (JTextField textField : this.suggestionFields)
		{
			suggestions.add(textField.getText());
		}
		
		return new Spice(twistList, suggestions);
	}
	
}
