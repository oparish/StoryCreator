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
import javax.swing.JTextField;

import main.Main;
import storyElements.Scenario;
import storyElements.options.SimpleBranchOption;

@SuppressWarnings("serial")
public class NewScenarioDialog extends JDialog
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	
	private static final String DESCRIPTION = "Description";
	private static final String INITIAL_OPTION = "Initial Option";
	
	private JTextField descriptionField;
	private ArrayList<JTextField> initialOptionFields;
	
	public NewScenarioDialog(Frame owner, boolean modal, int initialOptionNumber)
	{
		super(owner, modal);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridBagLayout());
		
		int yPos = 0;
		
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, DESCRIPTION, yPos);
		yPos++;
		
		this.initialOptionFields = new ArrayList<JTextField>();
		for (int i = 0; i < initialOptionNumber; i++)
		{
			JTextField initialOptionField = new JTextField();
			this.initialOptionFields.add(initialOptionField);
			this.addTextField(initialOptionField, INITIAL_OPTION + " " + i, yPos);
			yPos++;
		}
	}
	
	private void addTextField(JTextField jTextField, String labelText, int yPos)
	{		
		this.add(new JLabel(labelText), this.setupGridBagConstraints(0, yPos, 1, 1));
		this.add(jTextField, this.setupGridBagConstraints(1, yPos, 1, 1));
	}
	
	public Scenario getNewScenario()
	{
		ArrayList<SimpleBranchOption> initialOptions = new ArrayList<SimpleBranchOption>();
		for (JTextField textField : this.initialOptionFields)
		{
			initialOptions.add(new SimpleBranchOption(textField.getText()));
		}
		return new Scenario(initialOptions, this.descriptionField.getText(), Main.BRANCHLENGTH, Main.SUBPLOTLENGTH);
	}
	
	private GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridWidth;
		gridBagConstraints.gridheight = gridHeight;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		return gridBagConstraints;
	}
	
	public static void main(String[] args)
	{
		NewScenarioDialog newScenarioDialog = new NewScenarioDialog(null, false, 3);
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
