package storyElements.optionLists;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.MyPanel;
import frontEnd.OldOrNewPanel;
import frontEnd.fieldPanel.BadExitPointPanel;
import frontEnd.fieldPanel.ExitPointPanel;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.FlavourListPanel;
import frontEnd.fieldPanel.GoodExitPointPanel;
import frontEnd.fieldPanel.NewBadBranchPanel;
import frontEnd.fieldPanel.NewBadEndingPanel;
import frontEnd.fieldPanel.NewBranchPanel;
import frontEnd.fieldPanel.NewEndingPanel;
import frontEnd.fieldPanel.NewGoodBranchPanel;
import frontEnd.fieldPanel.NewGoodEndingPanel;
import frontEnd.fieldPanel.NewObstaclePanel;
import frontEnd.fieldPanel.NewOptionPanel;
import frontEnd.fieldPanel.NewSubplotPanel;
import frontEnd.fieldPanel.NewTokenPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Exitable;
import storyElements.Scenario;
import storyElements.Token;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;

@SuppressWarnings("serial")
public class Branch extends StorySection<BranchOption> implements ExitPoint, Exitable
{	
	private boolean useOpening;
	private Integer defaultExitPoint = null;
	private Integer goodExitPoint = null;
	private Integer badExitPoint = null;
	private Integer obstacle = null;
	private Integer branchLevel;

	public Integer getBranchLevel() {
		return branchLevel;
	}
	
	public void setUseOpening(boolean useOpening) {
		this.useOpening = useOpening;
	}

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
		if (Main.getMainScenario() != null)
			Main.getMainScenario().recordNewExitPoint(this, branchLevel);
	}
	
	public Branch(JsonObject jsonObject)
	{
		super();
		for (JsonValue optionJson : jsonObject.getJsonArray(Main.OPTIONS))
		{
			this.add(new BranchOption((JsonObject) optionJson));
		}
		this.defaultExitPoint = Main.processJsonInt(jsonObject, Main.EXITPOINT);
		this.goodExitPoint = Main.processJsonInt(jsonObject, Main.GOOD_EXITPOINT);
		this.badExitPoint = Main.processJsonInt(jsonObject, Main.BAD_EXITPOINT);
		this.obstacle = Main.processJsonInt(jsonObject, Main.OBSTACLE);
		this.description = jsonObject.getString(Main.DESCRIPTION);
		this.branchLevel = jsonObject.getInt(Main.BRANCH_LEVEL);
		this.useOpening = true;
	}
	
	public ExitPoint getExitPoint() {
		return Main.getMainScenario().getExitPoint(this.defaultExitPoint);
	}
	
	public ExitPoint getGoodExitPoint() {
		return Main.getMainScenario().getExitPoint(this.goodExitPoint);
	}

	public ExitPoint getBadExitPoint() {
		return Main.getMainScenario().getExitPoint(this.badExitPoint);
	}
	
	private ExitPoint createNewBranchConclusion(EditorDialog editorDialog)
	{
		Scenario currentScenario = Main.getMainScenario();
		boolean suddenEnding;
		
		if (currentScenario.canEnd() && currentScenario.getSuddenEnding().check())
			suddenEnding = true;
		else
			suddenEnding = false;
		
		if (currentScenario.getOptionHasObstacle().check())
			return this.createObstacleBranchConclusion(editorDialog, suddenEnding);
		else
			return this.createDefaultBranchConclusion(editorDialog, suddenEnding);
	}
	
	private ExitPoint createDefaultBranchConclusion(EditorDialog editorDialog, boolean suddenEnding)
	{
		Scenario currentScenario = Main.getMainScenario();
		int nextBranch = currentScenario.getNextBranch();
		
		FieldPanel<ExitPoint> fieldPanel;
		ExitPointPanel newExitPointPanel = new ExitPointPanel(currentScenario.getNextBranch());
		
		ArrayList<ExitPoint> exitPoints = currentScenario.getExitPointsAtBranchLevel(currentScenario.getNextBranch());
		
		OptionContentType optionContentType;
		
		if (!suddenEnding && currentScenario.checkLastBranch())
		{
			optionContentType = OptionContentType.GOODEXITPOINT;
		}
		else if (suddenEnding)
		{
			optionContentType = OptionContentType.BADEXITPOINT;
		}
		else
		{
			optionContentType = OptionContentType.EXITPOINT;
		}
		
		if (exitPoints == null)
			fieldPanel = (FieldPanel<ExitPoint>) optionContentType.getInstance(suddenEnding ? 
					currentScenario.getMaximumScenarioLength() : nextBranch);
		else
			fieldPanel = new OldOrNewPanel<ExitPoint>(optionContentType, suddenEnding ? 
					currentScenario.getMaximumScenarioLength() : nextBranch);
		
		FieldDialog newBranchDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{fieldPanel});
		Main.showWindowInCentre(newBranchDialog);
		ExitPoint newBranch = fieldPanel.getResult();
		this.defaultExitPoint = currentScenario.getExitPointID(newBranch);
		return newBranch; 
	}
	
	private ExitPoint createObstacleBranchConclusion(EditorDialog editorDialog, boolean suddenEnding)
	{
		Scenario currentScenario = Main.getMainScenario();
		
		ArrayList<MyPanel> fieldPanels = new ArrayList<MyPanel>();
		int nextBranch = currentScenario.getNextBranch();
		
		ArrayList<Token> tokens = currentScenario.getTokens();
		FieldPanel<Token> obstaclePanel;
		if (tokens.size() == 0)	
		{
			obstaclePanel = new NewObstaclePanel(nextBranch);
		}
		else
		{
			obstaclePanel = new OldOrNewPanel<Token>(OptionContentType.OBSTACLE, nextBranch);
		}
		
		fieldPanels.add(obstaclePanel);
		
		ArrayList<ExitPoint> exitPoints = currentScenario.getExitPointsAtBranchLevel(nextBranch);	
		FieldPanel<ExitPoint> goodExitPointPanel;
		FieldPanel<ExitPoint> badExitPointPanel;
		
		if (exitPoints == null)	
		{
			goodExitPointPanel = new GoodExitPointPanel(nextBranch);
			badExitPointPanel = new BadExitPointPanel(suddenEnding ? 
					currentScenario.getMaximumScenarioLength() : nextBranch);
		}
		else
		{
			goodExitPointPanel = new OldOrNewPanel<ExitPoint>(OptionContentType.GOODEXITPOINT, nextBranch);
			badExitPointPanel = new OldOrNewPanel<ExitPoint>(OptionContentType.BADEXITPOINT, suddenEnding ? 
					currentScenario.getMaximumScenarioLength() : nextBranch);
		}

		fieldPanels.add(goodExitPointPanel);
		fieldPanels.add(badExitPointPanel);
		
		FieldDialog newBranchDialog = new FieldDialog(editorDialog, true, fieldPanels);
		Main.showWindowInCentre(newBranchDialog);
		
		this.obstacle = currentScenario.getTokenID(obstaclePanel.getResult());
		this.goodExitPoint = currentScenario.getExitPointID(goodExitPointPanel.getResult());
		this.badExitPoint = currentScenario.getExitPointID(badExitPointPanel.getResult());
		
		if (editorDialog.checkHeldTokens(obstaclePanel.getResult()))
		{
			editorDialog.updateTechInfo("\r\nPassed obstacle.");
			return this.getGoodExitPoint();
		}
		else
		{
			editorDialog.updateTechInfo("\r\nFailed obstacle.");
			return this.getBadExitPoint();
		}
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		jsonObjectBuilder.add(Main.BRANCH_LEVEL, this.branchLevel);
		if (this.defaultExitPoint != null)
			jsonObjectBuilder.add(Main.EXITPOINT, this.defaultExitPoint);
		if (this.goodExitPoint != null)
			jsonObjectBuilder.add(Main.GOOD_EXITPOINT, this.goodExitPoint);
		if (this.badExitPoint != null)
			jsonObjectBuilder.add(Main.BAD_EXITPOINT, this.badExitPoint);
		if (this.obstacle != null)
			jsonObjectBuilder.add(Main.OBSTACLE, this.obstacle);
		return jsonObjectBuilder;
	}
	
	public Generator getGenerator()
	{
		return new Generator();
	}
	
	public class Generator
	{
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
				
				if (Main.getMainScenario().getBranchHasOpening().check())
				{
					counter++;
					return this.tryNewOption(editorDialog);
				}
			}
			
			if (this.counter == Main.getMainScenario().getBranchLength())
			{
				ExitPoint newExitPoint = editorDialog.exitPointFromExitable(Branch.this);
				if (newExitPoint == null)
					newExitPoint = Branch.this.createNewBranchConclusion(editorDialog);
				return newExitPoint;
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
		
		private FieldPanel<? extends ExitPoint> getExitPointPanel(OptionContentType optionContentType, ArrayList<ExitPoint> exitPointsAtLevel)
		{
			FieldPanel<? extends ExitPoint> exitPointPanel;
			int nextBranchLevel = Main.getMainScenario().getNextBranch();
			if (exitPointsAtLevel == null)
			{
				exitPointPanel = (FieldPanel<? extends ExitPoint>) optionContentType.getInstance(nextBranchLevel);
			}
			else
			{
				exitPointPanel = new OldOrNewPanel<ExitPoint>(optionContentType, nextBranchLevel);
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
				flavourListPanel = new FlavourListPanel(Main.getMainScenario().getNextBranch());
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
				tokenPanel = new NewTokenPanel(Main.getMainScenario().getNextBranch());
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
				obstaclePanel = new NewObstaclePanel(Main.getMainScenario().getNextBranch());
			}
			return obstaclePanel;
		}

		private Option tryNewOption(EditorDialog editorDialog)
		{
			this.lastNumber = Branch.this.size();
			Scenario currentScenario = Main.getMainScenario();
			
			boolean becomesExitPoint = currentScenario.getOptionBecomesNewExitPoint().check();
			boolean hasObstacle = currentScenario.pastScenarioMidPoint() && currentScenario.getOptionHasObstacle().check();
			boolean hasToken = !currentScenario.pastScenarioMidPoint() && currentScenario.getOptionHasToken().check();
			boolean hasSubplot = currentScenario.getOptionBecomesSubplot().check();

			ArrayList<ExitPoint> exitPointsAtLevel = currentScenario.getExitPointsAtBranchLevel(currentScenario.getNextBranch());
			HashMap<Integer, FlavourList> flavourLists = currentScenario.getFlavourLists();
			
			ArrayList<FieldPanel> fieldPanels = new ArrayList<FieldPanel>();
			NewOptionPanel newOptionPanel = new NewOptionPanel(Branch.this);
			
			if (becomesExitPoint && hasSubplot)
				hasSubplot = false;
			
			if (becomesExitPoint && !hasObstacle)
			{
				fieldPanels.add(this.getExitPointPanel(OptionContentType.EXITPOINT, exitPointsAtLevel));
			}	
			
			if (currentScenario.getOptionHasFlavour().check())
			{
				fieldPanels.add(this.getFlavourListPanel(flavourLists));
			}	
			
			if (hasToken)
			{
				fieldPanels.add(this.getTokenPanel());
			}
		
			if (becomesExitPoint && hasObstacle)
			{
				fieldPanels.add(this.getObstaclePanel());
				fieldPanels.add(this.getExitPointPanel(OptionContentType.GOODEXITPOINT, exitPointsAtLevel));
				fieldPanels.add(this.getExitPointPanel(OptionContentType.BADEXITPOINT, exitPointsAtLevel));
			}
			
			if (hasSubplot)
			{
				fieldPanels.add(this.getSubplotPanel());
			}
			
			ArrayList<MyPanel> myPanels = new ArrayList<MyPanel>();
			myPanels.add(newOptionPanel);
			myPanels.addAll(fieldPanels);
			
			FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, myPanels);
			newOptionDialog.setTitle("New Option");
			Main.showWindowInCentre(newOptionDialog);
			
			BranchOption branchOption = newOptionPanel.getResult();
			
			for (FieldPanel fieldPanel : fieldPanels)
			{
				OptionContentType optionContentType = fieldPanel.getOptionContentType();
				Integer contentID = Main.getMainScenario().getStoryElementID(fieldPanel.getResult());
				branchOption.setContentInteger(optionContentType, contentID);
			}
			
			Branch.this.add(branchOption);
			return branchOption;
		}
		
		private FieldPanel<Subplot> getSubplotPanel()
		{
			FieldPanel<Subplot> subplotPanel;
			if (Main.getMainScenario().getStoryElementList(OptionContentType.SUBPLOT, Main.getMainScenario().getNextBranch()).size() != 0)
			{
				subplotPanel = new OldOrNewPanel<Subplot>(OptionContentType.SUBPLOT, Main.getMainScenario().getNextBranch());
			}
			else
			{
				subplotPanel = new NewSubplotPanel(Main.getMainScenario().getNextBranch());
			}
			return subplotPanel;
		}
	}


	
	@Override
	public Integer getObstacle()
	{
		return this.obstacle;
	}
}
