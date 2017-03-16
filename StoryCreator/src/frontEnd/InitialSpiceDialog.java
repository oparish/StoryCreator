package frontEnd;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.json.JsonObject;

import storyElements.Spice;
import main.Main;

public class InitialSpiceDialog extends InitialDialog
{
	private static final String NEW_SPICE = "New Spice";
	private static final String LOAD_SPICE = "Load Spice";
	
	public InitialSpiceDialog(Frame owner, boolean modal)
	{
		super(owner, modal);
		this.add(this.makeButton(NEW_SPICE, ButtonID.NEW_SPICE), this.setupGridBagConstraints(0, 0, 1, 1));
		this.add(this.makeButton(LOAD_SPICE, ButtonID.LOAD_SPICE), this.setupGridBagConstraints(1, 0, 1, 1));
	}

	private void newSpice()
	{
		NewSpiceDialog newSpiceDialog = new NewSpiceDialog(null, true);
		Main.showWindowInCentre(newSpiceDialog);
        Main.setMainSpice(newSpiceDialog.getNewSpice());
		this.setVisible(false);
	}
	
	private void loadSpice()
	{
		File file = this.loadFile();
		Main.setSpiceFile(file);
		JsonObject spiceObject = this.loadJsonObject(file);
		Main.setMainSpice(new Spice(spiceObject));
		this.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		MyButton sourceButton = (MyButton) e.getSource();
		switch(sourceButton.getId())
		{
			case NEW_SPICE:
				this.newSpice();
			break;
			case LOAD_SPICE:
				this.loadSpice();
			break;
			default:
				break;
		}
	}
}
