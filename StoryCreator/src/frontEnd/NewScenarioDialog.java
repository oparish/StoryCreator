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
public class NewScenarioDialog extends NewOptionListDialog
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	
	private static final String SCENARIO_DESCRIPTION = "Scenario Description";
	private static final String INITIAL_BRANCH_DESCRIPTION = "Initial Branch Description";
	private static final String OPTION_WITH_EXITPOINT = "Option With Exit Point";
	private static final String OPTION_WITH_SUBPLOT = "Option With Subplot";
	private static final String OPTION_WITH_FLAVOUR = "Option With Flavour";

	private JTextField scenarioDescriptionField;
	private JTextField initialBranchDescriptionField;
	private NumberSpinner optionToBranchSpinner;
	private NumberSpinner optionWithSubPlotSpinner;
	private NumberSpinner optionWithFlavourSpinner;
	
	public NewScenarioDialog(Frame owner, boolean modal, int initialOptionNumber)
	{
		super(owner, modal);
		this.setSize(WIDTH, HEIGHT);
		
		this.scenarioDescriptionField = new JTextField();
		this.addTextField(this.scenarioDescriptionField, SCENARIO_DESCRIPTION);
		
		this.initialBranchDescriptionField = new JTextField();
		this.addTextField(this.initialBranchDescriptionField, INITIAL_BRANCH_DESCRIPTION);
		
		this.addInitialOptions(Main.INITIALOPTIONS_FOR_SCENARIO);
		
		this.optionToBranchSpinner = new NumberSpinner();
		this.addSpinner(this.optionToBranchSpinner, OPTION_WITH_EXITPOINT);
		
		this.optionWithSubPlotSpinner = new NumberSpinner();
		this.addSpinner(this.optionWithSubPlotSpinner, OPTION_WITH_SUBPLOT);
		
		this.optionWithFlavourSpinner = new NumberSpinner();
		this.addSpinner(this.optionWithFlavourSpinner, OPTION_WITH_FLAVOUR);
	}
	
	public Scenario getNewScenario()
	{
		ArrayList<BranchOption> initialOptions = new ArrayList<BranchOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new BranchOption(textField.getText()));
		}
		Scenario newScenario =  new Scenario(this.scenarioDescriptionField.getText(), initialOptions, this.initialBranchDescriptionField.getText(), Main.BRANCHLENGTH, Main.SUBPLOTLENGTH, Main.SCENARIOLENGTH);
		newScenario.setOptionBecomesNewExitPoint(new Chance(this.optionToBranchSpinner.getInt()));
		return newScenario;
	}
	
	public static void main(String[] args)
	{
		NewScenarioDialog newScenarioDialog = new NewScenarioDialog(null, true, Main.INITIALOPTIONS_FOR_SCENARIO);
		newScenarioDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });
		Dimension screenCentre = main.Main.getScreenCentre();
		newScenarioDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
		newScenarioDialog.setVisible(true);
	}
}
