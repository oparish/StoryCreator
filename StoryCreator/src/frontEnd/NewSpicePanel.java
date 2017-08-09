package frontEnd;

import java.awt.Frame;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTextField;

import frontEnd.fieldPanel.FieldPanel;
import storyElements.AspectType;
import storyElements.Spice;
import storyElements.optionLists.TwistList;
import storyElements.options.BranchOption;
import storyElements.options.ExpandingContentType;
import storyElements.options.StoryElement;
import storyElements.options.TwistOption;
import main.Main;

public class NewSpicePanel extends MyPanel<Spice>
{
	private static final String ASPECTTYPE_TITLE = "Aspect Type";
	
	ArrayList<NewTwistListPanel> newTwistListPanels;
	ArrayList<JTextField> suggestionFields;
	ArrayList<JTextField> goodSuggestionFields;
	ArrayList<JTextField> badSuggestionFields;
	ExpandingPanel<AspectTypePanel, AspectType> expandingPanel;
	
	public NewSpicePanel(int initialTwists)
	{
		super();
		this.heading = "New Spice";

		this.newTwistListPanels = new ArrayList<NewTwistListPanel>();
		for (int i = 0; i < initialTwists; i++)
		{
			NewTwistListPanel twistListPanel = new NewTwistListPanel();
			newTwistListPanels.add(twistListPanel);
			this.addPanel(twistListPanel);
		}
		
		this.suggestionFields = this.addSuggestionFields("Suggestion ");
		this.goodSuggestionFields = this.addSuggestionFields("Good Suggestion ");
		this.badSuggestionFields = this.addSuggestionFields("Bad Suggestion ");
		this.expandingPanel = new ExpandingPanel<AspectTypePanel, AspectType>(ExpandingContentType.ASPECTTYPE, 
				ASPECTTYPE_TITLE);
		this.addPanel(this.expandingPanel);
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
		int i = 0;
		HashMap<Integer, TwistList> twistLists = new HashMap<Integer, TwistList>();
		for (NewTwistListPanel newTwistListPanel : this.newTwistListPanels)
		{
			twistLists.put(i, newTwistListPanel.getResult());
			i++;
		}
		
		ArrayList<String> suggestions = OptionListFieldPanel.getStringsFromFields(this.suggestionFields);
		ArrayList<String> goodSuggestions = OptionListFieldPanel.getStringsFromFields(this.goodSuggestionFields);
		ArrayList<String> badSuggestions = OptionListFieldPanel.getStringsFromFields(this.badSuggestionFields);
		
		Spice spice =  new Spice(twistLists, suggestions, goodSuggestions, badSuggestions);
		
		int j = 0;
		for (AspectType aspectType : this.expandingPanel.getResult())
		{
			spice.addAspectType(i, aspectType);
			i++;
		}
		
		return spice;
	}
	
}
