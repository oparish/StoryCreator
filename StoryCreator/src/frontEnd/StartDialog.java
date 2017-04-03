package frontEnd;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.json.JsonObject;
import javax.swing.JLabel;

import main.Main;
import storyElements.Scenario;
import storyElements.Spice;

public class StartDialog extends InitialDialog
{
	private static final String NEW_SCENARIO = "New Scenario";
	private static final String LOAD_SCENARIO = "Load Scenario";
	private static final String NEW_SPICE = "New Spice";
	private static final String LOAD_SPICE = "Load Spice";
	private static final String STARTING_LEVEL = "Starting Level";
	private static final String INITIAL_TWIST_LISTS = "Initial Twist Lists";
	
	NumberSpinner startingSpinner;
	NumberSpinner twistSpinner;
	
	public StartDialog()
	{
		super();
		this.add(this.makeButton(NEW_SCENARIO, ButtonID.NEW_SCENARIO), this.setupGridBagConstraints(0, 0, 1, 1));
		this.add(this.makeButton(LOAD_SCENARIO, ButtonID.LOAD_SCENARIO), this.setupGridBagConstraints(1, 0, 1, 1));
		this.add(this.makeButton(NEW_SPICE, ButtonID.NEW_SPICE), this.setupGridBagConstraints(0, 1, 1, 1));
		this.add(this.makeButton(LOAD_SPICE, ButtonID.LOAD_SPICE), this.setupGridBagConstraints(1, 1, 1, 1));
		this.startingSpinner = new NumberSpinner();
		this.add(new JLabel(STARTING_LEVEL), this.setupGridBagConstraints(0, 2, 1, 1));
		this.add(this.startingSpinner, this.setupGridBagConstraints(1, 2, 1, 1));
		this.twistSpinner = new NumberSpinner();
		this.add(new JLabel(INITIAL_TWIST_LISTS), this.setupGridBagConstraints(0, 3, 1, 1));
		this.add(this.twistSpinner, this.setupGridBagConstraints(1, 3, 1, 1));
	}
	
	public int getBranchLevel()
	{
		return this.startingSpinner.getInt();
	}
	
	public int getInitialTwists()
	{
		return this.twistSpinner.getInt();
	}
	
	private void newScenario()
	{
		NewScenarioPanel newScenarioPanel = new NewScenarioPanel();
		FieldDialog newScenarioDialog = new FieldDialog(null, true, new MyPanel[]{newScenarioPanel});
		Main.showWindowInCentre(newScenarioDialog);
        Main.setMainScenario(newScenarioPanel.getResult());
	}
	
	private void loadScenario()
	{

		File file = this.loadFile();
		Main.setScenarioFile(file);
		JsonObject scenarioObject = this.loadJsonObject(file);
		Scenario newScenario = new Scenario(scenarioObject);
		Main.setMainScenario(newScenario);
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
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		ButtonID buttonID = ((MyButton) e.getSource()).getId();
		switch(buttonID)
		{
			case NEW_SCENARIO:
				this.newScenario();
				break;
			case LOAD_SCENARIO:
				this.loadScenario();
				break;
			case NEW_SPICE:
				this.newSpice();
				break;
			case LOAD_SPICE:
				this.loadSpice();
				break;
		}
	}

}
