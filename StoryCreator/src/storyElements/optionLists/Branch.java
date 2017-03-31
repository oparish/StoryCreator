package storyElements.optionLists;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.OldOrNewPanel;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.FlavourListPanel;
import frontEnd.fieldPanel.NewBadBranchPanel;
import frontEnd.fieldPanel.NewBranchPanel;
import frontEnd.fieldPanel.NewEndingPanel;
import frontEnd.fieldPanel.NewGoodBranchPanel;
import frontEnd.fieldPanel.NewObstaclePanel;
import frontEnd.fieldPanel.NewOptionPanel;
import frontEnd.fieldPanel.NewTokenPanel;
import frontEnd.fieldPanel.OptionContentPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.Token;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;

@SuppressWarnings("serial")
public class Branch extends StorySection<BranchOption> implements ExitPoint
{	
	private boolean useOpening;
	private Integer defaultExitPoint = null;
	private Integer goodExitPoint = null;
	private Integer badExitPoint = null;
	private Integer branchLevel;

	public ExitPoint getDefaultExitPoint() {
		return Main.getMainScenario().getExitPoint(this.defaultExitPoint);
	}

	public void setDefaultExitPoint(Integer defaultExitPoint) {
		this.defaultExitPoint = defaultExitPoint;
	}

	public Branch(ArrayList<BranchOption> initialOptions, String description, int branchLevel)
	{
		super(initialOptions, description);
		this.branchLevel = branchLevel;
		this.useOpening = false;
	}
	
	public Branch(JsonObject jsonObject)
	{
		super();
		for (JsonValue optionJson : jsonObject.getJsonArray(Main.OPTIONS))
		{
			this.add(new BranchOption((JsonObject) optionJson));
		}
		this.defaultExitPoint = Main.processJsonInt(jsonObject, Main.EXITPOINT);
		this.description = jsonObject.getString(Main.DESCRIPTION);
		this.branchLevel = jsonObject.getInt(Main.BRANCH_LEVEL);
		this.useOpening = true;
	}
	
	protected ExitPoint getDefaultExitPoint(EditorDialog editorDialog)
	{
		if (this.defaultExitPoint == null)
		{
			return this.createDefaultExitPoint(editorDialog);
		}
		else
		{
			return Main.getMainScenario().getExitPoint(this.defaultExitPoint);
		}
	}
	
	private ExitPoint createDefaultExitPoint(EditorDialog editorDialog)
	{
		Scenario scenario = Main.getMainScenario();
		if (scenario.checkLastBranch())
			return this.createDefaultEndingOption(editorDialog);
		else
			return this.createBranch(editorDialog);
	}
	
	private ExitPoint createDefaultEndingOption(EditorDialog editorDialog)
	{
		NewEndingPanel newEndingPanel = new NewEndingPanel();
		FieldDialog newEndingDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{newEndingPanel});
		newEndingDialog.setTitle("New Ending");
		Main.showWindowInCentre(newEndingDialog);
		Scenario currentScenario = Main.getMainScenario();
		EndingOption endingOption = newEndingPanel.getResult();
		this.defaultExitPoint = currentScenario.getExitPointID(endingOption);
		return endingOption;
	}
	
	private ExitPoint createBranch(EditorDialog editorDialog)
	{
		Scenario currentScenario = Main.getMainScenario();
		FieldPanel<Branch> fieldPanel;
		NewBranchPanel newBranchPanel = new NewBranchPanel();
		
		ArrayList<ExitPoint> exitPoints = currentScenario.getExitPointsAtBranchLevel(currentScenario.getNextBranch());
		
		if (exitPoints == null)	
			fieldPanel = newBranchPanel;
		else
			fieldPanel = new OldOrNewPanel<Branch>(OptionContentType.EXITPOINT, currentScenario.getNextBranch());
		
		FieldDialog newBranchDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{fieldPanel});
		Main.showWindowInCentre(newBranchDialog);
		Branch newBranch = fieldPanel.getResult();
		this.defaultExitPoint = currentScenario.getExitPointID(newBranch);
		return newBranch; 
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.BRANCH_LEVEL, this.branchLevel);
		if (this.defaultExitPoint != null)
			jsonObjectBuilder.add(Main.EXITPOINT, this.defaultExitPoint);
		return jsonObjectBuilder;
	}
	
	public Generator getGenerator()
	{
		return new Generator();
	}
	
	public class Generator
	{
		private Subplot currentSubplot = null;
		private Integer lastNumber = null;
		private int counter = 0;
		private boolean openingUnused = true;
		
		private Generator()
		{
			
		}
		
		public StoryElement generateStoryElement(EditorDialog editorDialog)
		{
			if (Branch.this.useOpening && this.openingUnused)
			{
				this.openingUnused = false;
				return this.tryNewOption(editorDialog);
			}
			
			if (this.counter == Main.getMainScenario().getBranchLength())
			{
				return Branch.this.getDefaultExitPoint(editorDialog);
			}
			
			counter++;
			int rnd = Main.getRandomNumberInRange(Branch.this.size());
			if (this.lastNumber == null || this.lastNumber != rnd)
			{
				this.lastNumber = rnd;
				return Branch.this.get(rnd);
			}
			else
			{
				StoryElement storyElement = this.tryNewOption(editorDialog);
				return storyElement;
			}	
		}
		
		private FieldPanel<? extends ExitPoint> getExitPointPanel(boolean isLastBranch, ArrayList<ExitPoint> exitPointsAtLevel)
		{
			FieldPanel<? extends ExitPoint> exitPointPanel;
			if (isLastBranch)
			{
				exitPointPanel = new NewEndingPanel();
			}
			else if (exitPointsAtLevel == null)
			{
				exitPointPanel = new NewBranchPanel();
			}
			else
			{
				exitPointPanel = new OldOrNewPanel<Branch>(OptionContentType.EXITPOINT, Main.getMainScenario().getNextBranch());
			}
			return exitPointPanel;
		}
		
		private FieldPanel<FlavourList> getFlavourListPanel(HashMap<Integer, FlavourList> flavourLists)
		{
			FieldPanel<FlavourList> flavourListPanel;
			if (flavourLists.size() != 0)
			{
				flavourListPanel = new OldOrNewPanel<FlavourList>(OptionContentType.FLAVOURLIST, Main.getMainScenario().getNextBranch());
			}
			else
			{
				flavourListPanel = new FlavourListPanel();
			}
			return flavourListPanel;
		}
		
		private FieldPanel<Token> getTokenPanel()
		{
			FieldPanel<Token> tokenPanel;
			Scenario currentScenario = Main.getMainScenario();
			if (currentScenario.getTokens().size() != 0)
			{
				ArrayList<Token> tokens = currentScenario.getTokens();
				tokenPanel = new OldOrNewPanel<Token>(OptionContentType.TOKEN, Main.getMainScenario().getNextBranch());
			}
			else
			{
				tokenPanel = new NewTokenPanel();
			}
			return tokenPanel;
		}
		
		private FieldPanel<Token> getObstaclePanel()
		{
			FieldPanel<Token> obstaclePanel;
			Scenario currentScenario = Main.getMainScenario();
			if (currentScenario.getTokens().size() != 0)
			{
				ArrayList<Token> tokens = currentScenario.getTokens();
				obstaclePanel = new OldOrNewPanel<Token>(OptionContentType.OBSTACLE, Main.getMainScenario().getNextBranch());
			}
			else
			{
				obstaclePanel = new NewObstaclePanel();
			}
			return obstaclePanel;
		}
		
		private FieldPanel<? extends ExitPoint> getGoodExitPointPanel(boolean isLastBranch, ArrayList<ExitPoint> exitPointsAtLevel)
		{
			FieldPanel<? extends ExitPoint> goodExitPointPanel;
			if (isLastBranch)
			{
				goodExitPointPanel = new NewEndingPanel();
			}
			else if (exitPointsAtLevel == null)
			{
				goodExitPointPanel = new NewGoodBranchPanel();
			}
			else
			{
				goodExitPointPanel = new OldOrNewPanel<Branch>(OptionContentType.GOODEXITPOINT, Main.getMainScenario().getNextBranch());
			}
			return goodExitPointPanel;
		}
		
		private FieldPanel<? extends ExitPoint> getBadExitPointPanel(boolean isLastBranch, ArrayList<ExitPoint> exitPointsAtLevel)
		{
			FieldPanel<? extends ExitPoint> badExitPointPanel;
			if (isLastBranch)
			{
				badExitPointPanel = new NewEndingPanel();
			}
			else if (exitPointsAtLevel == null)
			{
				badExitPointPanel = new NewBadBranchPanel();
			}
			else
			{
				badExitPointPanel = new OldOrNewPanel<Branch>(OptionContentType.BADEXITPOINT, Main.getMainScenario().getNextBranch());
			}
			return badExitPointPanel;
		}
		
		private Option tryNewOption(EditorDialog editorDialog)
		{
			this.lastNumber = Branch.this.size();
			Scenario currentScenario = Main.getMainScenario();
			
			boolean becomesExitPoint = currentScenario.getOptionBecomesNewExitPoint().check();
			boolean isLastBranch = currentScenario.checkLastBranch();
			boolean hasObstacle = currentScenario.getOptionHasObstacle().check();

			ArrayList<ExitPoint> exitPointsAtLevel = currentScenario.getExitPointsAtBranchLevel(currentScenario.getNextBranch());
			HashMap<Integer, FlavourList> flavourLists = currentScenario.getFlavourLists();
			
			ArrayList<FieldPanel> fieldPanels = new ArrayList<FieldPanel>();
			NewOptionPanel newOptionPanel = new NewOptionPanel(Branch.this);
			fieldPanels.add(newOptionPanel);
			
			if (becomesExitPoint && !hasObstacle)
			{
				fieldPanels.add(this.getExitPointPanel(isLastBranch, exitPointsAtLevel));
			}	
			
			if (currentScenario.getOptionHasFlavour().check())
			{
				fieldPanels.add(this.getFlavourListPanel(flavourLists));
			}	
			
			if (currentScenario.getOptionHasToken().check())
			{
				fieldPanels.add(this.getTokenPanel());
			}
		
			if (becomesExitPoint && hasObstacle)
			{
				fieldPanels.add(this.getObstaclePanel());
				fieldPanels.add(this.getGoodExitPointPanel(isLastBranch, exitPointsAtLevel));
				fieldPanels.add(this.getBadExitPointPanel(isLastBranch, exitPointsAtLevel));
			}
			
			FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, fieldPanels.toArray(new FieldPanel[fieldPanels.size()]));
			newOptionDialog.setTitle("New Option");
			Main.showWindowInCentre(newOptionDialog);
			
			BranchOption branchOption = newOptionPanel.getResult();
			
			for (FieldPanel fieldPanel : fieldPanels)
			{
				if (fieldPanel == newOptionPanel)
					continue;
				OptionContentType optionContentType = ((OptionContentPanel)fieldPanel).getOptionContentType();
				Integer contentID = Main.getMainScenario().getStoryElementID(fieldPanel.getResult());
				branchOption.setContentInteger(optionContentType, contentID);
			}
			
			Branch.this.add(branchOption);
			return branchOption;
		}	

		public Subplot getCurrentSubplot() {
			return currentSubplot;
		}

		public void setCurrentSubplot(Subplot currentSubplot) {
			this.currentSubplot = currentSubplot;
		}
	}
}
