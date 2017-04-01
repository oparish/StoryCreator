package frontEnd;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import frontEnd.fieldPanel.FieldPanel;
import main.Main;
import storyElements.optionLists.Branch;

public class FieldDialog extends JDialog
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 800;
	
	protected int yPos = 0;
	JPanel innerPanel;
	
	private FieldDialog(Frame owner, boolean modal)
	{
		super(owner, modal);
		this.setSize(WIDTH, HEIGHT);
		this.innerPanel = new JPanel();
		this.innerPanel.setLayout(new GridBagLayout());
		this.add(new JScrollPane(this.innerPanel));
	}
	
	public FieldDialog(Frame owner, boolean modal, ArrayList<MyPanel> myPanels)
	{
		this(owner, modal);	
		for (MyPanel panel : myPanels)
		{
			this.addLabel(panel.getHeading());
			this.addPanel(panel);
		}
	}
	
	public FieldDialog(Frame owner, boolean modal, MyPanel[] myPanels)
	{
		this(owner, modal);	
		for (MyPanel panel : myPanels)
		{
			this.addLabel(panel.getHeading());
			this.addPanel(panel);
		}
	}
	
	protected GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight, int weighty)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridWidth;
		gridBagConstraints.gridheight = gridHeight;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = weighty;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		return gridBagConstraints;
	}
	
	public void addPanel(JPanel jPanel)
	{
		this.innerPanel.add(jPanel, this.setupGridBagConstraints(0, this.yPos, 1, 1, 1));
		yPos++;
	}
	
	public void addLabel(String labelText)
	{
		this.innerPanel.add(new JLabel(labelText), this.setupGridBagConstraints(0, this.yPos, 1, 1, 0));
		yPos++;
	}
	
	public static void main(String[] args)
	{
//		FieldDialog fieldDialog = new FieldDialog(null, true, new FieldPanel[0]);
//		Main.showWindowInCentre(fieldDialog);
//		System.out.println("END");
	}
}
