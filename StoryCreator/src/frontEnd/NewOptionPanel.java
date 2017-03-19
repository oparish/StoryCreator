package frontEnd;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import storyElements.optionLists.OptionList;
import storyElements.optionLists.RepeatingOptionList;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
import storyElements.options.TwistOption;

public class NewOptionPanel extends NewStoryElementPanel<BranchOption>
{	
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public NewOptionPanel()
	{
		super();
		
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION);
	}
	
	public BranchOption getResult()
	{
		return new BranchOption(this.descriptionField.getText());
	}

	public static void main(String[] args)
	{
		NewOptionPanel newOptionDialog = new NewOptionPanel();
		Dimension screenCentre = main.Main.getScreenCentre();
		newOptionDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
		newOptionDialog.setVisible(true);
	}

}
