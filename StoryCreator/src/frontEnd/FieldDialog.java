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
	
	public FieldDialog(Frame owner, boolean modal, FieldPanel[] panels)
	{
		super(owner, modal);	
		this.setSize(WIDTH, HEIGHT);
		this.innerPanel = new JPanel();
		this.innerPanel.setLayout(new GridLayout(panels.length * 2, 1));
		for (FieldPanel panel : panels)
		{
			this.addLabel(panel.getHeading());
			this.addPanel(panel);
		}
		this.add(new JScrollPane(this.innerPanel));
	}
	
	public void addPanel(JPanel jPanel)
	{
		this.innerPanel.add(jPanel);
		yPos++;
	}
	
	public void addLabel(String labelText)
	{
		this.innerPanel.add(new JLabel(labelText));
		yPos++;
	}
	
	public static void main(String[] args)
	{
		FieldDialog fieldDialog = new FieldDialog(null, true, new FieldPanel[0]);
		Main.showWindowInCentre(fieldDialog);
		System.out.println("END");
	}
}
