package frontEnd;

import java.util.ArrayList;

import javax.swing.JTextField;

import frontEnd.fieldPanel.FieldPanel;
import storyElements.AspectList;
import storyElements.optionLists.FlavourList;
import storyElements.optionLists.TwistList;
import storyElements.options.AspectOption;
import storyElements.options.FlavourOption;
import storyElements.options.TwistOption;

public class AspectOptionPanel extends MyPanel<AspectOption>
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public AspectOptionPanel(AspectList aspectList)
	{
		super();
		this.heading = "Aspect Option";
		this.addJLabel(aspectList.getDescription());
		this.descriptionField = new JTextField();
		this.addStoryElementList(StoryElementList.create(aspectList));
		this.addTextField(this.descriptionField, DESCRIPTION);	
	}
	
	public AspectOption getResult()
	{
		return new AspectOption(this.descriptionField.getText());
	}
}
