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
import storyElements.options.Option;

public class NewOptionDialog extends JDialog
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	
	private static final String DESCRIPTION = "Description";
	
	private JTextField descriptionField;
	private OptionList optionList;
	
	public OptionList getOptionList() {
		return optionList;
	}

	public NewOptionDialog(Frame owner, boolean modal, OptionList repeatingOptionList)
	{
		super(owner, modal);
		this.optionList = repeatingOptionList;
		
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridBagLayout());
		
		int yPos = 0;
		
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION, yPos);
		yPos++;

	}
	
	private void addTextField(JTextField jTextField, String labelText, int yPos)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, yPos, 1, 1));
		this.add(jTextField, this.setupGridBagConstraints(1, yPos, 1, 1));
	}
	
	private GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridWidth;
		gridBagConstraints.gridheight = gridHeight;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		return gridBagConstraints;
	}
	
	public BranchOption getOption()
	{
		return new BranchOption(this.descriptionField.getText());
	}
	
	public static void main(String[] args)
	{
		NewOptionDialog newOptionDialog = new NewOptionDialog(null, false, null);
		newOptionDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });
		Dimension screenCentre = main.Main.getScreenCentre();
		newOptionDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
		newOptionDialog.setVisible(true);
	}

}
