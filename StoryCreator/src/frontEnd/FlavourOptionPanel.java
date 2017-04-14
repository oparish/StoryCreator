package frontEnd;

import java.util.ArrayList;

import javax.swing.JTextField;

import storyElements.optionLists.FlavourList;
import storyElements.optionLists.TwistList;
import storyElements.options.FlavourOption;
import storyElements.options.TwistOption;

public class FlavourOptionPanel extends MyPanel<FlavourOption>
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public FlavourOptionPanel(FlavourList flavourList)
	{
		super();
		this.heading = "Flavour Option";
		this.addJLabel(flavourList.getDescription());
		this.descriptionField = new JTextField();
		this.addStoryElementList(StoryElementList.create(flavourList));
		this.addTextField(this.descriptionField, DESCRIPTION);	
	}
	
	public FlavourOption getResult()
	{
		return new FlavourOption(this.descriptionField.getText());
	}
}
