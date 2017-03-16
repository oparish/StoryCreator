package frontEnd;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import main.Main;
import storyElements.optionLists.Branch;

public class FieldDialog extends JDialog
{
	protected int yPos = 0;
	
	public FieldDialog(Frame owner, boolean modal)
	{
		super(owner, modal);
		this.setLayout(new GridBagLayout());
	}
	
	protected void addJLabel(String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1));
		this.yPos++;
	}
	
	protected void addTextField(JTextField jTextField, String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1));
		this.add(jTextField, this.setupGridBagConstraints(1, this.yPos, 1, 1));
		this.yPos++;
	}
	
	protected void addSpinner(JSpinner jSpinner, String labelText)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1));
		this.add(jSpinner, this.setupGridBagConstraints(1, this.yPos, 1, 1));
		this.yPos++;
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
	
	public static void main(String[] args)
	{
		FieldDialog fieldDialog = new FieldDialog(null, true);
		Main.showWindowInCentre(fieldDialog);
		System.out.println("END");
	}
}
