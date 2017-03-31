package frontEnd.fieldPanel;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import frontEnd.StoryElementList;
import main.Main;
import storyElements.optionLists.OptionList;
import storyElements.optionLists.RepeatingOptionList;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
import storyElements.options.StoryElement;
import storyElements.options.TwistOption;

public class NewOptionPanel extends NewStoryElementPanel<BranchOption>
{	
	private static final String DESCRIPTION = "Description";
	
	private StoryElementList<StoryElement> storyElementList;
	private JTextField descriptionField;

	public NewOptionPanel(ArrayList<? extends Option> existingOptions)
	{
		super(Main.getMainSpice().getSuggestion());
		this.heading = "New Option";
		this.storyElementList = StoryElementList.create(existingOptions);
		this.addStoryElementList(this.storyElementList);
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION);
	}
	
	public BranchOption getResult()
	{
		return new BranchOption(this.descriptionField.getText());
	}

	public static void main(String[] args)
	{
		ArrayList<BranchOption> existingOptions = new ArrayList<BranchOption>();
		existingOptions.add(new BranchOption("One"));
		existingOptions.add(new BranchOption("Two"));
		
		NewOptionPanel newOptionDialog = new NewOptionPanel(existingOptions);
		Dimension screenCentre = main.Main.getScreenCentre();
		newOptionDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
		newOptionDialog.setVisible(true);
	}

}
