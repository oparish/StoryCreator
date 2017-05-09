package frontEnd;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.json.JsonObject;
import javax.swing.JLabel;

import main.Main;
import storyElements.AspectType;
import storyElements.Spice;
import storyElements.options.ExpandingContentType;

public class InitialSpiceDialog extends InitialDialog
{
	private static final String NEW_SPICE = "New Spice";
	private static final String LOAD_SPICE = "Load Spice";
	private static final String INITIAL_TWIST_LISTS = "Initial Twist Lists";
	NumberSpinner twistSpinner;
	
	public InitialSpiceDialog()
	{
		super();
		this.add(this.makeButton(NEW_SPICE, ButtonID.NEW_SPICE), this.setupGridBagConstraints(0, 0, 1, 1));
		this.add(this.makeButton(LOAD_SPICE, ButtonID.LOAD_SPICE), this.setupGridBagConstraints(1, 0, 1, 1));
		this.twistSpinner = new NumberSpinner();
		this.add(new JLabel(INITIAL_TWIST_LISTS), this.setupGridBagConstraints(0, 1, 1, 1));
		this.add(this.twistSpinner, this.setupGridBagConstraints(1, 1, 1, 1));
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		ButtonID buttonID = ((MyButton) e.getSource()).getId();
		switch(buttonID)
		{
			case NEW_SPICE:
				this.newSpice();
				break;
			case LOAD_SPICE:
				this.loadSpice();
				break;
		}	
	}
	
	public int getInitialTwists()
	{
		return this.twistSpinner.getInt();
	}
	
	private void newSpice()
	{
		NewSpicePanel newSpicePanel = new NewSpicePanel(this.getInitialTwists());
		FieldDialog newSpiceDialog = new FieldDialog(null, true, new MyPanel[]{newSpicePanel});
		Main.showWindowInCentre(newSpiceDialog);
        Main.setMainSpice(newSpicePanel.getResult());
	}
	
	private void loadSpice()
	{
		File file = this.loadFile();
		Main.setSpiceFile(file);
		JsonObject spiceObject = this.loadJsonObject(file);
		Main.setMainSpice(new Spice(spiceObject));
	}

}
