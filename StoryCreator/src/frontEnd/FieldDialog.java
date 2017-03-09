package frontEnd;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import storyElements.optionLists.Branch;

public class FieldDialog extends JDialog
{
	Branch branch;
	
	public FieldDialog(Frame owner, boolean modal)
	{
		super(owner, modal);
	}
	
	protected void addTextField(JTextField jTextField, String labelText, int yPos)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, yPos, 1, 1));
		this.add(jTextField, this.setupGridBagConstraints(1, yPos, 1, 1));
	}
	
	public void setBranch(Branch branch)
	{
		this.branch = branch;
	}
	
	public Branch getBranch()
	{
		return this.branch;
	}
	
	protected GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight)
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
}
