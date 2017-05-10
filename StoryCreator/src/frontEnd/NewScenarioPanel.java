package frontEnd;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import main.Main;
import storyElements.Chance;
import storyElements.Scenario;
import storyElements.options.BranchOption;

@SuppressWarnings("serial")
public class NewScenarioPanel extends MyOptionListPanel<Scenario>
{	
	private static final String SCENARIO_DESCRIPTION = "Scenario Description";
	private static final String INITIAL_BRANCH_DESCRIPTION = "Initial Branch Description";
	private static final String OPTION_WITH_EXITPOINT = "Option With Exit Point";
	private static final String OPTION_WITH_SUBPLOT = "Option With Subplot";
	private static final String OPTION_WITH_FLAVOUR = "Option With Flavour";
	private static final String OPTION_HAS_OBSTACLE = "Option Has Obstacle";
	private static final String OPTION_HAS_TOKEN = "Option Has Token";
	private static final String BRANCH_LENGTH = "Branch Length";
	private static final String SUBPLOT_LENGTH = "Subplot Length";
	private static final String SCENARIO_LENGTH = "Scenario Length";
	private static final String NEW_OPENING_CHANCE = "New Opening Chance";
	private static final String SUDDEN_ENDING_CHANCE = "Sudden Ending Chance";
	private static final String MINIMUM_SCENARIO_LENGTH = "Minimum Scenario Length";

	private JTextField scenarioDescriptionField;
	private NumberSpinner optionToBranchSpinner;
	private NumberSpinner optionWithSubPlotSpinner;
	private NumberSpinner optionWithFlavourSpinner;
	private NumberSpinner optionHasObstacleSpinner;
	private NumberSpinner optionHasTokenSpinner;
	private NumberSpinner branchLengthSpinner;
	private NumberSpinner subplotLengthSpinner;
	private NumberSpinner scenarioLengthSpinner;
	private NumberSpinner openingChanceSpinner;
	private NumberSpinner suddenEndingSpinner;
	private NumberSpinner minimumScenarioLengthSpinner;
	
	public NewScenarioPanel()
	{
		super();
		
		this.heading = "New Scenario";
		
		this.scenarioDescriptionField = new JTextField();
		this.addTextField(this.scenarioDescriptionField, SCENARIO_DESCRIPTION);
		
		this.optionToBranchSpinner = new NumberSpinner();
		this.addSpinner(this.optionToBranchSpinner, OPTION_WITH_EXITPOINT);
		
		this.optionWithSubPlotSpinner = new NumberSpinner();
		this.addSpinner(this.optionWithSubPlotSpinner, OPTION_WITH_SUBPLOT);
		
		this.optionWithFlavourSpinner = new NumberSpinner();
		this.addSpinner(this.optionWithFlavourSpinner, OPTION_WITH_FLAVOUR);
		
		this.optionHasObstacleSpinner = new NumberSpinner();
		this.addSpinner(this.optionHasObstacleSpinner, OPTION_HAS_OBSTACLE);
		
		this.optionHasTokenSpinner = new NumberSpinner();
		this.addSpinner(this.optionHasTokenSpinner, OPTION_HAS_TOKEN);
		
		this.branchLengthSpinner = new NumberSpinner();
		this.branchLengthSpinner.setValue(Main.DEFAULTBRANCHLENGTH);
		this.addSpinner(this.branchLengthSpinner, BRANCH_LENGTH);
		
		this.subplotLengthSpinner = new NumberSpinner();
		this.subplotLengthSpinner.setValue(Main.DEFAULTSUBPLOTLENGTH);
		this.addSpinner(this.subplotLengthSpinner, SUBPLOT_LENGTH);
		
		this.scenarioLengthSpinner = new NumberSpinner();
		this.scenarioLengthSpinner.setValue(Main.DEFAULTSCENARIOLENGTH);
		this.addSpinner(this.scenarioLengthSpinner, SCENARIO_LENGTH);
		
		this.openingChanceSpinner = new NumberSpinner();
		this.openingChanceSpinner.setValue(Main.DEFAULTOPENINGCHANCE);
		this.addSpinner(this.openingChanceSpinner, NEW_OPENING_CHANCE);
		
		this.suddenEndingSpinner = new NumberSpinner();
		this.suddenEndingSpinner.setValue(Main.DEFAULTSUDDENENDING_CHANCE);
		this.addSpinner(this.suddenEndingSpinner, SUDDEN_ENDING_CHANCE);
		
		this.minimumScenarioLengthSpinner = new NumberSpinner();
		this.minimumScenarioLengthSpinner.setValue(Main.DEFAULT_MINIMUM_SCENARIO_LENGTH);
		this.addSpinner(this.minimumScenarioLengthSpinner, MINIMUM_SCENARIO_LENGTH);
	}
	
	public Scenario getResult()
	{
		Scenario newScenario =  new Scenario(this.scenarioDescriptionField.getText(), this.branchLengthSpinner.getInt(), this.subplotLengthSpinner.getInt(), this.scenarioLengthSpinner.getInt());
		newScenario.setOptionBecomesNewExitPoint(new Chance(this.optionToBranchSpinner.getInt()));
		newScenario.setOptionHasFlavour(new Chance(this.optionWithFlavourSpinner.getInt()));
		newScenario.setOptionHasObstacle(new Chance(this.optionHasObstacleSpinner.getInt()));
		newScenario.setOptionHasToken(new Chance(this.optionHasTokenSpinner.getInt()));
		newScenario.setOptionBecomesSubplot(new Chance(this.optionWithSubPlotSpinner.getInt()));
		newScenario.setBranchHasOpening(new Chance(this.openingChanceSpinner.getInt()));
		newScenario.setSuddenEnding(new Chance(this.suddenEndingSpinner.getInt()));
		newScenario.setMinimumScenarioLength(this.minimumScenarioLengthSpinner.getInt());
		return newScenario;
	}
	
	public static void main(String[] args)
	{
		NewScenarioPanel newScenarioDialog = new NewScenarioPanel();

		Dimension screenCentre = main.Main.getScreenCentre();
		newScenarioDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
		newScenarioDialog.setVisible(true);
	}
}
