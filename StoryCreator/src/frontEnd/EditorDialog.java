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
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;

import frontEnd.fieldPanel.ExitPointPanel;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.NewBranchPanel;
import storyElements.Aspect;
import storyElements.ExitPoint;
import storyElements.Exitable;
import storyElements.Scenario;
import storyElements.Token;
import storyElements.optionLists.Branch;
import storyElements.optionLists.FlavourList;
import storyElements.optionLists.Branch.Generator;
import storyElements.optionLists.Subplot;
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
	private static final String BACKTRACK = "Backtrack";
	private static final String SAVESTORYTOFILE = "Save Story To File";
	private static final String SAVE_SPICE = "Save Spice";
	private static final String SAVE_SPICE_AS = "Save Spice As";
	
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 1000;
	
	private JEditorPane editorPanel;
	private JTextArea displayPanel;
	private JTextArea infoPanel;
	private JTextArea infoPanel2;
	
	private ArrayList<TwistOption> twistOptions;
	private Option currentOption;
	private FlavourOption flavour;
	private StringBuilder storyBuilder;
	private StringBuilder techBuilder;
	private Token currentObstacle;
	private Aspect currentAspect;
	
	MyButton progressButton;
	
	private Generator mainGenerator = null;
	private ExitPoint nextExitPoint = null;
	private ArrayList<ExitPoint> pastExitPoints = new ArrayList<ExitPoint>();
	private Subplot nextSubplot = null;
	private Subplot.Generator subplotGenerator = null;
	private int subplotCounter = 0;
	private HashMap<Integer, ArrayList<Token>> heldTokens;
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
			Branch currentBranch = (Branch) this.setupStartingBranch(startingLevel);
			this.pastExitPoints.add(currentBranch);
			scenario.setCurrentBranch(currentBranch);
			scenario.getExitPointID(currentBranch);
		}
		else
		{
			this.pastExitPoints.add(scenario.getCurrentBranch());
		}
		
		this.currentAspect = scenario.getCurrentBranch().generateAspect(this);
		
		this.updateDisplay();
	}
	
	public HashMap<Integer, ArrayList<Token>> getHeldTokens() {
		return heldTokens;
	}

	public ArrayList<Token> getUnheldTokens() {
		return unheldTokens;
	}
	
	private void setupTokens()
	{
		 this.unheldTokens = Main.getMainScenario().getTokens();
		 this.heldTokens = new HashMap<Integer, ArrayList<Token>>();
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
		this.infoPanel2 = new JTextArea();
		this.infoPanel2.setEditable(false);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(3, 1));
		rightPanel.add(new JScrollPane(this.infoPanel));
		rightPanel.add(new JScrollPane(this.infoPanel2));
		rightPanel.add(this.setupButtonPanel());
		return rightPanel;
	}
	
	private JPanel setupButtonPanel()
	{
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 4));
		this.progressButton = this.makeButton(PROGRESS, ButtonID.PROGRESS);
		buttonPanel.add(this.progressButton);
		buttonPanel.add(this.makeButton(BACKTRACK, ButtonID.BACKTRACK));
		buttonPanel.add(this.makeButton(SAVESTORYTOFILE, ButtonID.SAVESTORYTOFILE));
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
		this.moveText(this.editorPanel.getText() + "\r\n\r\n");
		this.editorPanel.setText("");
		this.generate();
	}
	
	private void moveText(String text)
	{
		this.storyBuilder.append(text);
		this.displayPanel.setText(this.storyBuilder.toString());
	}
	
	private void backtrack()
	{
		BacktrackPanel backtrackPanel = new BacktrackPanel(this.pastExitPoints);
		Main.showWindowInCentre(new FieldDialog(this, true, new MyPanel[]{backtrackPanel}));
		ExitPoint newExitPoint = backtrackPanel.getResult();
		if (newExitPoint instanceof Branch)
		{
			Branch newBranch = (Branch) newExitPoint;
			int newBranchLevel = newBranch.getBranchLevel();
			int currentBranchLevel = Main.getMainScenario().getCurrentBranch().getBranchLevel();
			
			for (int i = currentBranchLevel; i >= newBranchLevel; i--)
			{
				this.pastExitPoints.remove(i);
				if (this.heldTokens.get(i) != null)
					for (Token token : this.heldTokens.get(i))
					{
						this.unheldTokens.add(token);
					}
				this.heldTokens.remove(i);
			}
				
			Main.getMainScenario().setCurrentBranch(newBranch);
			this.nextExitPoint = newBranch;
			this.flavour = null;
			newBranch.setUseOpening(true);
			this.progressButton.setEnabled(true);
			this.progress();
			this.moveText("\r\n\r\nBACKTRACKING TO " + newBranch.getDescription() + "\r\n\r\n");
		}
		else
		{
			return;
		}
	}
	
	public void reachEnding(EndingOption ending)
	{	
		this.progressButton.setEnabled(false);
		this.currentOption = ending;
		this.updateDisplay();
	}
	
	public void updateTechInfo(String info)
	{
		this.techBuilder.append(info);
		this.infoPanel2.setText(this.techBuilder.toString());
	}
	
	private void generate()
	{	
		if (this.nextExitPoint != null)
		{
			if (this.nextExitPoint instanceof EndingOption)
			{
				EndingOption ending = (EndingOption) this.nextExitPoint;
				this.reachEnding(ending);
				this.updateTechInfo("\r\nEnding: " + ending.getDescription());
			}
			else
			{
				Branch branch = (Branch) this.nextExitPoint;
				this.startNewBranch((Branch) this.nextExitPoint);
				this.updateTechInfo("\r\nNew Branch: " + branch.getDescription());
			}
			this.nextExitPoint = null;
		}
		else if (this.nextSubplot != null)
		{
			this.setOption(this.subplotGenerator.generateStoryElement(this));
			this.subplotCounter++;
			if (this.subplotCounter >= Main.getMainScenario().getSubplotLength())
			{
				this.nextSubplot.setUseOpening(true);
				this.nextSubplot = null;
				this.subplotCounter = 0;
			}
		}
		else
		{
			Generator generator = this.getGenerator();
			StoryElement storyElement = generator.generateStoryElement(this);
			
			if (storyElement instanceof EndingOption)
			{
				EndingOption ending = (EndingOption) storyElement;
				this.reachEnding(ending);
				this.updateTechInfo("\r\nEnding: " + ending.getDescription());
			}
			else if (storyElement instanceof Option)
			{
				Option option = (Option) storyElement;
				this.setOption(option);
				this.updateTechInfo("\r\n" + option.getDescription());
			}
			else
			{
				Branch branch = (Branch) storyElement;
				this.startNewBranch(branch);
				this.updateTechInfo("\r\nNew Branch: " + branch.getDescription());
			}
		}
		this.updateTechInfo("\r\n");
	}
	
	private void setOption(Option option)
	{
		Scenario scenario = Main.getMainScenario();
		if (option instanceof BranchOption)
		{
			BranchOption branchOption = (BranchOption) option;
			if (branchOption.getToken() != null)
			{
				Token token = scenario.getTokenByID(branchOption.getToken());
				if (!this.checkHeldTokens(token))
				{
					this.updateTechInfo("\r\nReceived token:" + token.getDescription());
					this.addToHeldTokens(token);
					this.unheldTokens.remove(token);
				}
			}
			
			if (branchOption.getSubPlot() != null)
			{
				this.nextSubplot = branchOption.getSubPlot();
				this.subplotGenerator = this.nextSubplot.getGenerator();
			}
			
			FlavourList flavourList = branchOption.getFlavourList();
			if (flavourList != null)
			{
				this.flavour = flavourList.generateOption(this);
				this.updateTechInfo("\r\nFlavour:" + this.flavour.getDescription());
			}
			else
			{
				this.flavour = null;
			}
			
			ExitPoint newExitPoint = this.exitPointFromExitable(branchOption);
			if (newExitPoint != null)
			{
				this.mainGenerator = null;
				this.nextExitPoint = newExitPoint;
			}
		}

		this.currentOption = option;
		this.updateDisplay();
	}
	
	public ExitPoint exitPointFromExitable(Exitable exitable)
	{
		if (exitable.getObstacle() != null)
		{
			Token obstacle = Main.getMainScenario().getTokenByID(exitable.getObstacle());
			this.currentObstacle = obstacle;
			this.updateTechInfo("\r\nObstacle: " + obstacle.getDescription());
			if (this.checkHeldTokens(obstacle))
			{
				this.updateTechInfo("\r\nPassed obstacle.");
				return exitable.getGoodExitPoint();
			}
			else
			{
				this.updateTechInfo("\r\nFailed obstacle.");
				return exitable.getBadExitPoint();
			}
		}
		else
		{
			return exitable.getExitPoint();	
		}
	}
	
	public void addToHeldTokens(Token token)
	{
		int branchLevel = Main.getMainScenario().getCurrentBranch().getBranchLevel();
		ArrayList<Token> tokenList = this.heldTokens.get(branchLevel);
		if (tokenList != null)
		{
			tokenList.add(token);
		}
		else
		{
			tokenList = new ArrayList<Token>();
			tokenList.add(token);
			this.heldTokens.put(branchLevel, tokenList);
		}
	}
	
	public boolean checkHeldTokens(Token obstacle)
	{
		ArrayList<Token> tokenList = new ArrayList<Token>();
		for (Collection<Token> tokens : this.heldTokens.values())
		{
			for (Token token : tokens)
			{
				if (token == obstacle)
					return true;
			}
		}
		return false;
	}
	
	public ArrayList<Token> getAllTokens()
	{
		ArrayList<Token> tokenList = new ArrayList<Token>();
		for (Collection<Token> tokens : this.heldTokens.values())
		{
			for (Token token : tokens)
			{
				tokenList.add(token);
			}
		}
		return tokenList;
	}
	
	private void updateDisplay()
	{
		StringBuilder infoBuilder = new StringBuilder();
		Scenario currentScenario = Main.getMainScenario();
		infoBuilder.append("Scenario: " + currentScenario.getDescription() + "\r\n");
		
		infoBuilder.append("Twists: \r\n");
		
		HashMap<Integer, TwistList> twistLists = Main.getMainSpice().getTwistLists();
		int i = 0;
		
		for (TwistOption twistOption : this.twistOptions)
		{
			infoBuilder.append(twistLists.get(i).getDescription() + ": " + twistOption.getDescription() + "\r\n");
			i++;
		}
		
		infoBuilder.append("Branch: " + currentScenario.getCurrentBranch().getDescription() + "\r\n");
		infoBuilder.append("Current Option: " + this.getCurrentOptionDescription() + "\r\n");	
		infoBuilder.append("Current Flavour: " + this.getCurrentFlavourDescription() + "\r\n");
		infoBuilder.append("Current Obstacle: " + this.getCurrentObstacleDescription() + "\r\n");
		if (this.currentAspect != null)
		{
			infoBuilder.append("Current Aspect: " + this.currentAspect.getDescription() + "\r\n");
			infoBuilder.append(this.currentAspect.getQualitiesDescription() + "\r\n");
		}

		infoBuilder.append("Current Tokens:\r\n");
		for (Token token : this.getAllTokens())
		{
			infoBuilder.append(token.getDescription() + "\r\n");
		}
		this.infoPanel.setText(infoBuilder.toString());
	}
	
	private void startNewBranch(Branch branch)
	{
		Scenario scenario = Main.getMainScenario();
		this.pastExitPoints.add(branch);
		this.currentAspect = branch.generateAspect(this);
		scenario.setCurrentBranch(branch);
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
	
	private ExitPoint setupStartingBranch(int startingLevel)
	{
		Scenario currentScenario = Main.getMainScenario();
		FieldPanel<ExitPoint> fieldPanel;
		ExitPointPanel exitPointPanel = new ExitPointPanel(startingLevel);
		
		ArrayList<ExitPoint> exitPoints = currentScenario.getExitPointsAtBranchLevel(startingLevel);
		
		if (exitPoints == null)	
			fieldPanel = exitPointPanel;
		else
			fieldPanel = new OldOrNewPanel<ExitPoint>(OptionContentType.EXITPOINT, startingLevel);
		
		FieldDialog newBranchDialog = new FieldDialog(this, true, new FieldPanel[]{fieldPanel});
		Main.showWindowInCentre(newBranchDialog);
		ExitPoint exitPoint = fieldPanel.getResult();
		return exitPoint; 
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
			case BACKTRACK:
				this.backtrack();
			break;
			case SAVESTORYTOFILE:
				this.saveStoryToFile();
			break;
			case SAVE_SPICE:
				Main.saveScenario(this);
				Main.saveSpice(this);
			break;
			case SAVE_SPICE_AS:
				Main.saveScenario(this);
				Main.saveSpiceAs(this);
			break;
			default:
				break;
		}
	}
}
