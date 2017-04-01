package frontEnd;

import java.util.ArrayList;

import javax.swing.JTextField;

import frontEnd.fieldPanel.NewStoryElementPanel;
import storyElements.optionLists.TwistList;
import storyElements.options.BranchOption;
import storyElements.options.TwistOption;

public class NewTwistOptionPanel extends MyPanel
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public NewTwistOptionPanel(TwistList twistOptions)
	{
		super();
		this.heading = "Twist Option";
		this.addJLabel(twistOptions.getDescription());
		this.descriptionField = new JTextField();
		this.addStoryElementList(StoryElementList.create(twistOptions));
		this.addTextField(this.descriptionField, DESCRIPTION);	
	}
	
	public TwistOption getResult()
	{
		return new TwistOption(this.descriptionField.getText());
	}
}
