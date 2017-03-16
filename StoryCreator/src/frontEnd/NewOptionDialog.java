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

public class NewOptionDialog extends NewStoryElementDialog
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;

	public NewOptionDialog(Frame owner, boolean modal)
	{
		super(owner, modal);

		this.setSize(WIDTH, HEIGHT);
		
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION);
	}
	
	public BranchOption getOption()
	{
		return new BranchOption(this.descriptionField.getText());
	}
	
	public TwistOption getTwistOption()
	{
		return new TwistOption(this.descriptionField.getText());
	}
	
	public static void main(String[] args)
	{
		NewOptionDialog newOptionDialog = new NewOptionDialog(null, true);
		Dimension screenCentre = main.Main.getScreenCentre();
		newOptionDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
		newOptionDialog.setVisible(true);
	}

}
