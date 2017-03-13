package frontEnd;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.optionLists.RepeatingOptionList.Generator;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
import storyElements.options.TwistOption;
import main.Main;

@SuppressWarnings("serial")
public class EditorDialog extends JFrame implements ActionListener
{
	private static final String PROGRESS = "Progress";
	private static final String SAVESTORYTOFILE = "Save Story To File";
	private static final String SAVE_SCENARIO = "Save Scenario";
	private static final String SAVE_SCENARIO_AS = "Save Scenario As";
	private static final String SAVE_SPICE = "Save Spice";
	private static final String SAVE_SPICE_AS = "Save Spice As";
	
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	
	private static final int INITIALOPTIONNUMBER = 3;
	
	private JEditorPane editorPanel;
	private JTextArea displayPanel;
	private JTextArea infoPanel;
	
	private TwistOption twistOption;
	private Option currentOption;
	private StringBuilder storyBuilder;
	private StringBuilder techBuilder;
	
	MyButton progressButton;
	
	private Generator mainGenerator = null;
	
	public EditorDialog()
	{
		super();

		this.setLayout(new GridBagLayout());	
		
		this.add(this.setupLeftPanel(), this.setupGridBagConstraints(0, 0, 2, 1));
		this.add(this.setupRightPanel(), this.setupGridBagConstraints(2, 0, 1, 1));
				
		this.setSize(WIDTH, HEIGHT);
		
		this.storyBuilder = new StringBuilder("");
		this.techBuilder = new StringBuilder("Scenario: " + Main.getMainScenario().getDescription());
		
		this.twistGenerate();
		this.updateDisplay();
	}
	
	private void twistGenerate()
	{
		Main.getMainSpice().getTwistList().generateOption(this);
	}
	
	public void setTwistOption(TwistOption twistOption)
	{
		this.twistOption = twistOption;
		this.techBuilder.append("\r\n" + twistOption.getDescription());
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
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		return gridBagConstraints;
	}
	
	public String getCurrentOptionDescription()
	{
		if (this.currentOption == null)
			return "";
		else
			return this.currentOption.getDescription();
	}
	
	private JPanel setupLeftPanel()
	{
		JPanel leftPanel = new JPanel();
		
		this.displayPanel = this.setupDisplayPanel();
		this.editorPanel = this.setupEditorPane();
		leftPanel.setLayout(new GridLayout(2, 1));	
		leftPanel.add(new JScrollPane(this.displayPanel));
		
		JScrollPane scrollPane = new JScrollPane(this.editorPanel);
		scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		leftPanel.add(scrollPane);
		
		return leftPanel;
	}
	
	private JPanel setupRightPanel()
	{		
		this.infoPanel = new JTextArea();
		this.infoPanel.setEditable(false);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(new JScrollPane(this.infoPanel));
		rightPanel.add(this.setupButtonPanel());
		return rightPanel;
	}
	
	private JPanel setupButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 3));
		this.progressButton = this.makeButton(PROGRESS, ButtonID.PROGRESS);
		buttonPanel.add(this.progressButton);
		buttonPanel.add(this.makeButton(SAVESTORYTOFILE, ButtonID.SAVESTORYTOFILE));
		buttonPanel.add(this.makeButton(SAVE_SCENARIO, ButtonID.SAVE_SCENARIO));
		buttonPanel.add(this.makeButton(SAVE_SCENARIO_AS, ButtonID.SAVE_SCENARIO_AS));
		buttonPanel.add(this.makeButton(SAVE_SPICE, ButtonID.SAVE_SPICE));
		buttonPanel.add(this.makeButton(SAVE_SPICE_AS, ButtonID.SAVE_SPICE_AS));
		return buttonPanel;
	}
	
	private MyButton makeButton (String text, ButtonID buttonID)
	{
		MyButton myButton = new MyButton(text, buttonID);
		myButton.addActionListener(this);
		myButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		return myButton;
	}
	
	private JTextArea setupDisplayPanel()
	{
		JTextArea displayPanel = new JTextArea();
		displayPanel.setLineWrap(true);
		displayPanel.setEditable(false);
		return displayPanel;
	}
	
	private JEditorPane setupEditorPane()
	{
		JEditorPane editorPanel = new JEditorPane();
		editorPanel.setEditable(true);
		return editorPanel;
	}
	
	private void progress()
	{
		this.storyBuilder.append(this.editorPanel.getText() + "\r\n\r\n");
		this.displayPanel.setText(this.storyBuilder.toString());
		this.editorPanel.setText("");
		this.generate();
	}
	
	public void reachEnding(EndingOption ending)
	{
		this.techBuilder.append("\r\nEnding: " + ending.getDescription());
		this.progressButton.setEnabled(false);
		this.currentOption = ending;
		this.updateDisplay();
	}
	
	private void generate()
	{
		Generator generator = this.getGenerator();
		generator.generateOption(this);
	}
	
	public void setOption(Option option)
	{
		if (option instanceof BranchOption)
		{
			ExitPoint exitPoint = ((BranchOption) option).getExitPoint();
			if (exitPoint != null)
			{
				exitPoint.useAsExitPoint(this);
				return;
			}
		}

		this.currentOption = option;
		this.techBuilder.append("\r\n" + option.getDescription());
		this.updateDisplay();
	}
	
	private void updateDisplay()
	{
		StringBuilder infoBuilder = new StringBuilder();
		Scenario currentScenario = Main.getMainScenario();
		infoBuilder.append("Scenario: " + currentScenario.getDescription() + "\r\n");
		infoBuilder.append("Twist: " + this.twistOption.getDescription() + "\r\n");
		infoBuilder.append("Branch: " + currentScenario.getCurrentBranch().getDescription() + "\r\n");
		infoBuilder.append("Current Option: " + this.getCurrentOptionDescription());	
		this.infoPanel.setText(infoBuilder.toString());
	}
	
	public void startNewBranch()
	{
		Scenario scenario = Main.getMainScenario();
		scenario.incrementBranchCounter();
		this.techBuilder.append("\r\nNew Branch: " + scenario.getCurrentBranch().getDescription());
		this.mainGenerator = null;
		this.currentOption = null;
		this.updateDisplay();
	}
	
	private Generator getGenerator()
	{
		if (this.mainGenerator == null)
		{
			this.mainGenerator = Main.getMainScenario().getCurrentBranch().getGenerator();
		}
		return this.mainGenerator;
	}
	
	private void saveStoryToFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			File saveFile = chooser.getSelectedFile();
			String filename = saveFile.getName();
			if (!filename.endsWith(".txt"))
				saveFile = new File(saveFile.getAbsolutePath() + ".txt");	
			Main.saveTextToFile(saveFile, this.storyBuilder, this.editorPanel.getText(),this.techBuilder);
		}	
	}
	
	private void saveScenario()
	{
		if (Main.getScenarioFile() != null)
			Main.saveJsonStructureToFile(Main.getScenarioFile(), Main.getMainScenario());
		else
			this.saveScenarioAs();
	}
	
	private void saveSpice()
	{
		if (Main.getSpiceFile() != null)
			Main.saveJsonStructureToFile(Main.getSpiceFile(), Main.getMainSpice());
		else
			this.saveSpiceAs();
	}
	
	private void saveSpiceAs()
	{
		File spiceFile = this.chooseFile();
		Main.setSpiceFile(spiceFile);
		Main.saveJsonStructureToFile(spiceFile, Main.getMainSpice());
	}
	
	private File chooseFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			File saveFile = chooser.getSelectedFile();
			String filename = saveFile.getName();
			if (!filename.endsWith(".txt"))
				saveFile = new File(saveFile.getAbsolutePath() + ".txt");		
			return saveFile;
		}
		return null;
	}
	
	private void saveScenarioAs()
	{
		File scenarioFile = this.chooseFile();
		Main.setScenarioFile(scenarioFile);
		Main.saveJsonStructureToFile(scenarioFile, Main.getMainScenario());
	}
	
	public static void main(String[] args)
	{
		EditorDialog editorDialog = new EditorDialog();
		editorDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });
		Dimension screenCentre = main.Main.getScreenCentre();
		editorDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
		editorDialog.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		MyButton sourceButton = (MyButton) e.getSource();
		switch(sourceButton.getId())
		{
			case PROGRESS:
				this.progress();
			break;
			case SAVESTORYTOFILE:
				this.saveStoryToFile();
			break;
			case SAVE_SCENARIO:
				this.saveScenario();
			break;
			case SAVE_SCENARIO_AS:
				this.saveScenarioAs();
			break;
			case SAVE_SPICE:
				this.saveSpice();
			break;
			case SAVE_SPICE_AS:
				this.saveSpiceAs();
			break;
			default:
				break;
		}
	}
}
