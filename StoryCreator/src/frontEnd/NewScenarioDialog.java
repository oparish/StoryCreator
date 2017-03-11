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
import storyElements.Scenario;
import storyElements.options.BranchOption;

@SuppressWarnings("serial")
public class NewScenarioDialog extends FieldDialog
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	
	private static final String SCENARIO_DESCRIPTION = "Scenario Description";
	private static final String INITIAL_BRANCH_DESCRIPTION = "Initial Branch Description";
	private static final String INITIAL_OPTION = "Initial Option";
	private static final String OPTION_WITH_BRANCH = "Option With Branch";
	private static final String OPTION_WITH_SUBPLOT = "Option With Subplot";
	private static final String OPTION_WITH_FLAVOUR = "Option With Flavour";
	
	private JTextField scenarioDescriptionField;
	private JTextField initialBranchDescriptionField;
	private ArrayList<JTextField> initialOptionFields;
	private JSpinner optionToBranchSpinner;
	private JSpinner optionWithSubPlotSpinner;
	private JSpinner optionWithFlavourSpinner;
	
	public NewScenarioDialog(Frame owner, boolean modal, int initialOptionNumber)
	{
		super(owner, modal);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridBagLayout());
		
		int yPos = 0;
		
		this.scenarioDescriptionField = new JTextField();
		this.addTextField(this.scenarioDescriptionField, SCENARIO_DESCRIPTION, yPos);
		yPos++;
		
		this.initialBranchDescriptionField = new JTextField();
		this.addTextField(this.initialBranchDescriptionField, INITIAL_BRANCH_DESCRIPTION, yPos);
		yPos++;
		
		this.initialOptionFields = new ArrayList<JTextField>();
		for (int i = 0; i < initialOptionNumber; i++)
		{
			JTextField initialOptionField = new JTextField();
			this.initialOptionFields.add(initialOptionField);
			this.addTextField(initialOptionField, INITIAL_OPTION + " " + i, yPos);
			yPos++;
		}
		
		this.optionToBranchSpinner = new JSpinner();
		this.addSpinner(this.optionToBranchSpinner, OPTION_WITH_BRANCH, yPos);
		yPos++;
		
		this.optionWithSubPlotSpinner = new JSpinner();
		this.addSpinner(this.optionWithSubPlotSpinner, OPTION_WITH_SUBPLOT, yPos);
		yPos++;
		
		this.optionWithFlavourSpinner = new JSpinner();
		this.addSpinner(this.optionWithFlavourSpinner, OPTION_WITH_FLAVOUR, yPos);
		yPos++;
	}
	
	public Scenario getNewScenario()
	{
		ArrayList<BranchOption> initialOptions = new ArrayList<BranchOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new BranchOption(textField.getText()));
		}
		return new Scenario(this.scenarioDescriptionField.getText(), initialOptions, this.initialBranchDescriptionField.getText(), Main.BRANCHLENGTH, Main.SUBPLOTLENGTH, Main.SCENARIOLENGTH);
	}
	
	public static void main(String[] args)
	{
		NewScenarioDialog newScenarioDialog = new NewScenarioDialog(null, false, Main.INITIALOPTIONNUMBER);
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
