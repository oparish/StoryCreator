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
	ArrayList<JTextField> goodSuggestionFields;
	ArrayList<JTextField> badSuggestionFields;
	
	public NewSpicePanel()
	{
		super("New Spice");
		this.addInitialOptions(Main.INITIALOPTIONS_FOR_SPICE);
		
		this.suggestionFields = this.addSuggestionFields("Suggestion ");
		this.goodSuggestionFields = this.addSuggestionFields("Good Suggestion ");
		this.badSuggestionFields = this.addSuggestionFields("Bad Suggestion ");
	}

	private ArrayList<JTextField> addSuggestionFields(String prefix)
	{
		ArrayList<JTextField> fields = new ArrayList<JTextField>();
		for (int i = 0; i < Main.SUGGESTION_NUMBER; i++)
		{
			JTextField newField = new JTextField();
			fields.add(newField);
			this.addTextField(newField, prefix + i);
		}
		return fields;
	}
	
	public Spice getResult()
	{
		TwistList twistList = new TwistList();
		for (JTextField textField : this.initialOptionFields)
		{
			twistList.add(new TwistOption(textField.getText()));
		}
		ArrayList<String> suggestions = NewOptionListPanel.getStringsFromFields(this.suggestionFields);
		ArrayList<String> goodSuggestions = NewOptionListPanel.getStringsFromFields(this.goodSuggestionFields);
		ArrayList<String> badSuggestions = NewOptionListPanel.getStringsFromFields(this.badSuggestionFields);
		
		return new Spice(twistList, suggestions, goodSuggestions, badSuggestions);
	}
	
}
