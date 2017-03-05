package frontEnd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import main.Main;

@SuppressWarnings("serial")
public class InitialDialog extends JFrame implements ActionListener
{
	private static final String NEW_SCENARIO = "New Scenario";
	private static final String LOAD_SCENARIO = "Load Scenario";
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 150;
	
	public InitialDialog()
	{
		super();
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridBagLayout());
		this.add(this.makeButton(NEW_SCENARIO, ButtonID.NEW_SCENARIO), this.setupGridBagConstraints(0, 0, 1, 1));
		this.add(this.makeButton(LOAD_SCENARIO, ButtonID.LOAD_SCENARIO), this.setupGridBagConstraints(1, 0, 1, 1));
	}
	
	private MyButton makeButton (String text, ButtonID buttonID)
	{
		MyButton myButton = new MyButton(text, buttonID);
		myButton.addActionListener(this);
		return myButton;
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
	
	private void newScenario()
	{
		this.setVisible(false);
		NewScenarioDialog newScenarioDialog = new NewScenarioDialog(null, false, Main.INITIALOPTIONNUMBER);
		newScenarioDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                Main.setMainScenario(((NewScenarioDialog) e.getWindow()).getNewScenario());
                EditorDialog editorDialog = new EditorDialog();
                Main.showWindowInCentre(editorDialog);
            }  
        });
		Main.showWindowInCentre(newScenarioDialog);
	}
	
	private void loadScenario()
	{
		
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
