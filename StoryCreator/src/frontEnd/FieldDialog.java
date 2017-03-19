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
	private static final int PANELWIDTH = 500;
	private static final int PANELHEIGHT = 300;
	
	protected int yPos = 0;
	
	public FieldDialog(Frame owner, boolean modal, FieldPanel[] panels)
	{
		super(owner, modal);
		this.setLayout(new GridLayout(panels.length, 1));
		this.setSize(PANELWIDTH, PANELHEIGHT * panels.length);
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
	
	public static void main(String[] args)
	{
		FieldDialog fieldDialog = new FieldDialog(null, true, new FieldPanel[0]);
		Main.showWindowInCentre(fieldDialog);
		System.out.println("END");
	}
}
