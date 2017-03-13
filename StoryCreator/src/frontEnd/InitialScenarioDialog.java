package frontEnd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import storyElements.Scenario;
import main.Main;

@SuppressWarnings("serial")
public class InitialScenarioDialog extends InitialDialog
{
	private static final String NEW_SCENARIO = "New Scenario";
	private static final String LOAD_SCENARIO = "Load Scenario";
	
	public InitialScenarioDialog()
	{
		super();
		this.add(this.makeButton(NEW_SCENARIO, ButtonID.NEW_SCENARIO), this.setupGridBagConstraints(0, 0, 1, 1));
		this.add(this.makeButton(LOAD_SCENARIO, ButtonID.LOAD_SCENARIO), this.setupGridBagConstraints(1, 0, 1, 1));
	}
	
	private void newScenario()
	{
		this.setVisible(false);
		NewScenarioDialog newScenarioDialog = new NewScenarioDialog(null, false, Main.INITIALOPTIONS_FOR_SCENARIO);
		newScenarioDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                Main.setMainScenario(((NewScenarioDialog) e.getWindow()).getNewScenario());
                InitialSpiceDialog initialSpiceDialog = new InitialSpiceDialog();
                Main.showWindowInCentre(initialSpiceDialog);
            }  
        });
		Main.showWindowInCentre(newScenarioDialog);
	}
	
	private void loadScenario()
	{
		this.setVisible(false);
		File file = this.loadFile();
		Main.setScenarioFile(file);
		JsonObject scenarioObject = this.loadJsonObject(file);
		Scenario newScenario = new Scenario(scenarioObject);
		Main.setMainScenario(newScenario);
		InitialSpiceDialog initialSpiceDialog = new InitialSpiceDialog();
        Main.showWindowInCentre(initialSpiceDialog);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		MyButton sourceButton = (MyButton) e.getSource();
		switch(sourceButton.getId())
		{
			case NEW_SCENARIO:
				this.newScenario();
			break;
			case LOAD_SCENARIO:
				this.loadScenario();
			break;
			default:
				break;
		}
	}
}