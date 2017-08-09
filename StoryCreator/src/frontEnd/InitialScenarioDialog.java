package frontEnd;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.json.JsonObject;
import javax.swing.JLabel;

import main.Main;
import storyElements.AspectType;
import storyElements.Scenario;
import storyElements.Spice;
import storyElements.options.ExpandingContentType;

public class InitialScenarioDialog extends InitialDialog
{
	private static final String NEW_SCENARIO = "New Scenario";
	private static final String LOAD_SCENARIO = "Load Scenario";
	private static final String STARTING_LEVEL = "Starting Level";
	private static final String NEW_ASPECT_TYPE = "New Aspect Type";
	
	StoryElementList<Scenario> existingScenarios; 
	NumberSpinner startingSpinner;
	Scenario addedScenario = null;
	
	public InitialScenarioDialog()
	{
		super();
		this.loadScenarioFiles();
		this.add(this.makeButton(NEW_SCENARIO, ButtonID.NEW_SCENARIO), this.setupGridBagConstraints(0, 1, 1, 1));
		this.add(this.makeButton(LOAD_SCENARIO, ButtonID.LOAD_SCENARIO), this.setupGridBagConstraints(1, 1, 1, 1));
		this.add(this.makeButton(NEW_ASPECT_TYPE, ButtonID.NEW_ASPECT_TYPE), this.setupGridBagConstraints(0, 3, 2, 1));
		this.startingSpinner = new NumberSpinner();
		this.add(new JLabel(STARTING_LEVEL), this.setupGridBagConstraints(0, 2, 1, 1));
		this.add(this.startingSpinner, this.setupGridBagConstraints(1, 2, 1, 1));
	}
	
	private void loadScenarioFiles()
	{
		ArrayList<Scenario> scenarios = new ArrayList<Scenario>();
		for (String filePath : Main.getMainSpice().getScenarioFilePaths())
		{
			File file = new File(filePath);
			JsonObject scenarioObject = this.loadJsonObject(file);
			Scenario newScenario = new Scenario(scenarioObject);
			newScenario.setFile(file);
			scenarios.add(newScenario);
		}
		this.existingScenarios = StoryElementList.create(scenarios);
		this.add(this.existingScenarios, this.setupGridBagConstraints(0, 0, 2, 1));
		
	}
	
	public int getBranchLevel()
	{
		return this.startingSpinner.getInt();
	}
	
	private void newScenario()
	{
		NewScenarioPanel newScenarioPanel = new NewScenarioPanel();
		FieldDialog newScenarioDialog = new FieldDialog(null, true, new MyPanel[]{newScenarioPanel});
		Main.showWindowInCentre(newScenarioDialog);
		Scenario newScenario = newScenarioPanel.getResult();
        Main.setMainScenario(newScenario);
        this.addScenarioToSpice();
		this.addedScenario = newScenario;
	}
	
	private void loadScenario()
	{
		File file = this.loadFile();
		JsonObject scenarioObject = this.loadJsonObject(file);
		Scenario newScenario = new Scenario(scenarioObject);	
		Main.setMainScenario(newScenario);
		newScenario.setFile(file);
		this.addScenarioToSpice();
		this.addedScenario = newScenario;
	}
	
	public Scenario getResult()
	{
		if (this.addedScenario != null)
			return this.addedScenario;
		else
			return this.existingScenarios.getSelectedElement();
	}
	
	private void addScenarioToSpice()
	{
		String scenarioFilePath = Main.saveScenario(this);
		Main.getMainSpice().addScenarioFilePath(scenarioFilePath);
	}
	
	private void newAspectType()
	{
		AspectTypePanel aspectTypePanel = new AspectTypePanel();
		FieldDialog fieldDialog = new FieldDialog(null, true, aspectTypePanel);
		Main.showWindowInCentre(fieldDialog);
		Main.getMainSpice().addAspectType(Main.getMainSpice().getAspectTypes().size(), aspectTypePanel.getResult());
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
			case NEW_ASPECT_TYPE:
				this.newAspectType();
				break;
		}
	}

}
