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
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.NewBranchPanel;
import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.Token;
import storyElements.optionLists.Branch;
import storyElements.optionLists.FlavourList;
import storyElements.optionLists.Branch.Generator;
import storyElements.optionLists.TwistList;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.FlavourOption;
import storyElements.options.Option;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;
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
	
	private ArrayList<TwistOption> twistOptions;
	private Option currentOption;
	private FlavourOption flavour;
	private StringBuilder storyBuilder;
	private StringBuilder techBuilder;
	private Token currentObstacle;
	
	MyButton progressButton;
	
	private Generator mainGenerator = null;
	private ExitPoint nextExitPoint = null;
	private ArrayList<Token> heldTokens;
	private ArrayList<Token> unheldTokens;

	public EditorDialog(int startingLevel)
	{
		super();
	
		Scenario scenario = Main.getMainScenario();
		
		this.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });

		this.setLayout(new GridBagLayout());	
		
		this.add(this.setupLeftPanel(), this.setupGridBagConstraints(0, 0, 2, 1));
		this.add(this.setupRightPanel(), this.setupGridBagConstraints(2, 0, 1, 1));
				
		this.setSize(WIDTH, HEIGHT);
		
		this.storyBuilder = new StringBuilder("");
		this.techBuilder = new StringBuilder("Scenario: " + scenario.getDescription());
		
		this.twistGenerate();
		this.setupTokens();
		
		if (scenario.getCurrentBranch() == null)
		{
			Branch currentBranch = this.setupStartingBranch(startingLevel);
			scenario.setCurrentBranch(currentBranch);
			scenario.getExitPointID(currentBranch);
		}
		
		this.updateDisplay();
	}
	
	public ArrayList<Token> getHeldTokens() {
		return heldTokens;
	}

	public ArrayList<Token> getUnheldTokens() {
		return unheldTokens;
	}
	
	private void setupTokens()
	{
		 this.unheldTokens = Main.getMainScenario().getTokens();
		 this.heldTokens = new ArrayList<Token>();
	}
	
	private void twistGenerate()
	{
		this.twistOptions = new ArrayList<TwistOption>();
		HashMap<Integer, TwistList> twistLists = Main.getMainSpice().getTwistLists();
		for (TwistList twistList : twistLists.values())
		{
			this.twistOptions.add((TwistOption) twistList.generateOption(this));
		}
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
	
	public String getCurrentFlavourDescription()
	{
		if (this.flavour == null)
			return "";
		else
			return this.flavour.getDescription();
	}
	
	public String getCurrentObstacleDescription()
	{
		if (this.currentObstacle == null)
			return "";
		else
			return this.currentObstacle.getDescription();
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
		if (this.nextExitPoint != null)
		{
			if (this.nextExitPoint instanceof EndingOption)
				this.reachEnding((EndingOption) this.nextExitPoint);
			else
				this.startNewBranch((Branch) this.nextExitPoint);
			this.nextExitPoint = null;
		}
		else
		{
			Generator generator = this.getGenerator();
			StoryElement storyElement = generator.generateStoryElement(this);
			
			if (storyElement instanceof EndingOption)
				this.reachEnding((EndingOption) storyElement);
			else if (storyElement instanceof Option)
				this.setOption((Option) storyElement);
			else
				this.startNewBranch((Branch) storyElement);
		}
	}
	
	private void setOption(Option option)
	{
		Scenario scenario = Main.getMainScenario();
		if (option instanceof BranchOption)
		{
			if (((BranchOption) option).getToken() != null)
			{
				Token token = scenario.getTokenByID(((BranchOption) option).getToken());
				if (!this.heldTokens.contains(token))
				{
					this.heldTokens.add(token);
					this.unheldTokens.remove(token);
				}
			}
			
			FlavourList flavourList = ((BranchOption) option).getFlavourList();
			if (flavourList != null)
			{
				this.flavour = flavourList.getFlavour();
			}
			else
			{
				this.flavour = null;
			}
			
			if (((BranchOption) option).getObstacle() != null)
			{
				this.mainGenerator = null;
				Token obstacle = scenario.getTokenByID(((BranchOption) option).getObstacle());
				this.currentObstacle = obstacle;
				if (this.heldTokens.contains(obstacle))
					this.nextExitPoint = ((BranchOption) option).getGoodExitPointID();
				else
					this.nextExitPoint = ((BranchOption) option).getBadExitPointID();
			}
			else
			{
				ExitPoint exitPoint = ((BranchOption) option).getExitPoint();
				if (exitPoint != null)
				{
					this.mainGenerator = null;
					this.nextExitPoint = exitPoint;
				}	
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
		
		infoBuilder.append("Twists: \r\n");
		for (TwistOption twistOption : this.twistOptions)
		{
			infoBuilder.append(twistOption.getDescription() + "\r\n");
		}
		
		infoBuilder.append("Branch: " + currentScenario.getCurrentBranch().getDescription() + "\r\n");
		infoBuilder.append("Current Option: " + this.getCurrentOptionDescription() + "\r\n");	
		infoBuilder.append("Current Flavour: " + this.getCurrentFlavourDescription() + "\r\n");
		infoBuilder.append("Current Obstacle: " + this.getCurrentObstacleDescription() + "\r\n");
		infoBuilder.append("Current Tokens:\r\n");
		for (Token token : this.heldTokens)
		{
			infoBuilder.append(token.getDescription() + "\r\n");
		}
		this.infoPanel.setText(infoBuilder.toString());
	}
	
	private void startNewBranch(Branch branch)
	{
		Scenario scenario = Main.getMainScenario();
		scenario.incrementBranchCounter();
		scenario.setCurrentBranch(branch);
		this.techBuilder.append("\r\nNew Branch: " + branch.getDescription());
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
	
	private Branch setupStartingBranch(int startingLevel)
	{
		Scenario currentScenario = Main.getMainScenario();
		FieldPanel<Branch> fieldPanel;
		NewBranchPanel newBranchPanel = new NewBranchPanel();
		
		ArrayList<ExitPoint> exitPoints = currentScenario.getExitPointsAtBranchLevel(startingLevel);
		
		if (exitPoints == null)	
			fieldPanel = newBranchPanel;
		else
			fieldPanel = new OldOrNewPanel<Branch>(OptionContentType.EXITPOINT, startingLevel);
		
		FieldDialog newBranchDialog = new FieldDialog(this, true, new FieldPanel[]{fieldPanel});
		Main.showWindowInCentre(newBranchDialog);
		Branch newBranch = fieldPanel.getResult();
		return newBranch; 
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
			Main.saveTextToFile(saveFile, this.storyBuilder, this.editorPanel.getText(), this.techBuilder);
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
//		EditorDialog editorDialog = new EditorDialog();
//		editorDialog.addWindowListener(new WindowAdapter() {  
//            public void windowClosing(WindowEvent e) {  
//                System.exit(0);  
//            }  
//        });
//		Dimension screenCentre = main.Main.getScreenCentre();
//		editorDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
//		editorDialog.setVisible(true);
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
