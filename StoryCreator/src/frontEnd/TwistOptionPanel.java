package frontEnd;

import java.util.ArrayList;

import javax.swing.JTextField;

import frontEnd.fieldPanel.NewStoryElementPanel;
import storyElements.optionLists.TwistList;
import storyElements.options.BranchOption;
import storyElements.options.TwistOption;

public class TwistOptionPanel extends MyPanel<TwistOption>
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public TwistOptionPanel(TwistList twistList)
	{
		super();
		this.heading = "Twist Option";
		this.addJLabel(twistList.getDescription());
		this.descriptionField = new JTextField();
		this.addStoryElementList(StoryElementList.create(twistList));
		this.addTextField(this.descriptionField, DESCRIPTION);	
	}
	
	public TwistOption getResult()
	{
		return new TwistOption(this.descriptionField.getText());
	}
}
