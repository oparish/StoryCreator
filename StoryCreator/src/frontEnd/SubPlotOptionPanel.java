package frontEnd;

import javax.swing.JTextField;

import storyElements.optionLists.Subplot;
import storyElements.optionLists.TwistList;
import storyElements.options.SubplotOption;
import storyElements.options.TwistOption;

public class SubPlotOptionPanel  extends MyPanel<SubplotOption>
{
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public SubPlotOptionPanel(Subplot subplot)
	{
		super();
		this.heading = "Subplot Option";
		this.addJLabel(subplot.getDescription());
		this.descriptionField = new JTextField();
		this.addStoryElementList(StoryElementList.create(subplot));
		this.addTextField(this.descriptionField, DESCRIPTION);	
	}
	
	public SubplotOption getResult()
	{
		return new SubplotOption(this.descriptionField.getText());
	}
}
