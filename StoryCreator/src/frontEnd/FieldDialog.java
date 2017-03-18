package frontEnd;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import main.Main;
import storyElements.optionLists.Branch;

public class FieldDialog extends JDialog
{
	protected int yPos = 0;
	
	public FieldDialog(Frame owner, boolean modal, FieldPanel[] panels)
	{
		super(owner, modal);
		this.setLayout(new GridLayout(panels.length, 1));
		this.setSize(500, 500);
		for (FieldPanel panel : panels)
		{
			this.addPanel(panel);
		}
	}
	
	public void addPanel(JPanel jPanel)
	{
		this.add(jPanel);
		yPos++;
	}
	
//	protected GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight)
//	{
//		GridBagConstraints gridBagConstraints = new GridBagConstraints();
//		gridBagConstraints.gridx = gridx;
//		gridBagConstraints.gridy = gridy;
//		gridBagConstraints.gridwidth = gridWidth;
//		gridBagConstraints.gridheight = gridHeight;
//		gridBagConstraints.weightx = 1;
//		gridBagConstraints.weighty = 1;
//		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
//		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
//		return gridBagConstraints;
//	}
	
	public static void main(String[] args)
	{
		FieldDialog fieldDialog = new FieldDialog(null, true, new FieldPanel[0]);
		Main.showWindowInCentre(fieldDialog);
		System.out.println("END");
	}
}
