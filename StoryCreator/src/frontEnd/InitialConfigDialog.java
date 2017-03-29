package frontEnd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

public class InitialConfigDialog extends InitialDialog
{
	private static final int WIDTH = 400;
	private static final int HEIGHT = 150;
	
	private NumberSpinner startingSpinner;
	
	public InitialConfigDialog()
	{
		super();
		this.startingSpinner = new NumberSpinner();
		this.add(this.startingSpinner);
	}
	
	public int getBranchLevel()
	{
		return this.startingSpinner.getInt();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
