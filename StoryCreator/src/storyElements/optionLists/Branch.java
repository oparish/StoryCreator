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
import frontEnd.FieldPanel;
import frontEnd.FlavourListPanel;
import frontEnd.NewBranchPanel;
import frontEnd.NewEndingPanel;
import frontEnd.NewObstaclePanel;
import frontEnd.NewOptionPanel;
import frontEnd.NewTokenPanel;
import frontEnd.OldOrNewPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.Token;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
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
		NewBranchPanel newBranchPanel = new NewBranchPanel(Main.getMainSpice().getSuggestion());
		
		ArrayList<ExitPoint> exitPoints = currentScenario.getBranchLevel(currentScenario.getNextBranch());
		
		if (exitPoints == null)	
			fieldPanel = newBranchPanel;
		else
			fieldPanel = new OldOrNewPanel<Branch>((Branch) exitPoints.get(Main.getRandomNumberInRange(exitPoints.size())), newBranchPanel);
		
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
		
		private Option tryNewOption(EditorDialog editorDialog)
		{
			this.lastNumber = Branch.this.size();
			Scenario currentScenario = Main.getMainScenario();
			
			boolean becomesExitPoint = currentScenario.getOptionBecomesNewExitPoint().check();
			boolean hasFlavour = currentScenario.getOptionHasFlavour().check();
			boolean isLastBranch = currentScenario.checkLastBranch();
			boolean hasToken = currentScenario.getOptionHasToken().check();
			boolean hasObstacle = currentScenario.getOptionHasObstacle().check();

			ArrayList<ExitPoint> exitPointsAtLevel = currentScenario.getBranchLevel(currentScenario.getNextBranch());
			HashMap<Integer, FlavourList> flavourLists = currentScenario.getFlavourLists();
			
			ArrayList<FieldPanel> fieldPanels = new ArrayList<FieldPanel>();
			NewOptionPanel newOptionPanel = new NewOptionPanel();
			fieldPanels.add(newOptionPanel);
			
			FieldPanel<? extends ExitPoint> exitPointPanel = null;
			if (becomesExitPoint && !hasObstacle)
			{
				if (isLastBranch)
				{
					exitPointPanel = new NewEndingPanel();
				}
				else if (exitPointsAtLevel == null)
				{
					exitPointPanel = new NewBranchPanel(Main.getMainSpice().getSuggestion());
				}
				else
				{
					exitPointPanel = new OldOrNewPanel<Branch>((Branch) exitPointsAtLevel.get(Main.getRandomNumberInRange(exitPointsAtLevel.size())), new NewBranchPanel(Main.getMainSpice().getSuggestion()));
				}
				fieldPanels.add(exitPointPanel);
			}
			
			FieldPanel<FlavourList> flavourListPanel = null;
			if (hasFlavour)
			{
				if (flavourLists.size() != 0)
				{
					flavourListPanel = new OldOrNewPanel<FlavourList>(flavourLists.get(Main.getRandomNumberInRange(flavourLists.size())), new FlavourListPanel());
				}
				else
				{
					flavourListPanel = new FlavourListPanel();
				}
				fieldPanels.add(flavourListPanel);
			}
			
			FieldPanel<Token> tokenPanel = null;
			if (hasToken)
			{
				if (currentScenario.getTokens().size() != 0)
				{
					ArrayList<Token> tokens = currentScenario.getTokens();
					tokenPanel = new OldOrNewPanel<Token>(tokens.get(Main.getRandomNumberInRange(tokens.size())), new NewTokenPanel());
				}
				else
				{
					tokenPanel = new NewTokenPanel();
				}
				fieldPanels.add(tokenPanel);
			}
		
			FieldPanel<Token> obstaclePanel = null;
			FieldPanel<? extends ExitPoint> goodExitPointPanel = null;
			FieldPanel<? extends ExitPoint> badExitPointPanel = null;
			if (becomesExitPoint && hasObstacle)
			{
				if (currentScenario.getTokens().size() != 0)
				{
					ArrayList<Token> tokens = currentScenario.getTokens();
					obstaclePanel = new OldOrNewPanel<Token>(tokens.get(Main.getRandomNumberInRange(tokens.size())), new NewObstaclePanel());
				}
				else
				{
					obstaclePanel = new NewObstaclePanel();
				}
				fieldPanels.add(obstaclePanel);
				
				if (isLastBranch)
				{
					goodExitPointPanel = new NewEndingPanel();
				}
				else if (exitPointsAtLevel == null)
				{
					goodExitPointPanel = new NewBranchPanel(Main.getMainSpice().getGoodSuggestion());
				}
				else
				{
					goodExitPointPanel = new OldOrNewPanel<Branch>((Branch) exitPointsAtLevel.get(Main.getRandomNumberInRange(exitPointsAtLevel.size())), new NewBranchPanel(Main.getMainSpice().getGoodSuggestion()));
				}
				fieldPanels.add(goodExitPointPanel);
				
				if (isLastBranch)
				{
					badExitPointPanel = new NewEndingPanel();
				}
				else if (exitPointsAtLevel == null)
				{
					badExitPointPanel = new NewBranchPanel(Main.getMainSpice().getBadSuggestion());
				}
				else
				{
					badExitPointPanel = new OldOrNewPanel<Branch>((Branch) exitPointsAtLevel.get(Main.getRandomNumberInRange(exitPointsAtLevel.size())), new NewBranchPanel(Main.getMainSpice().getBadSuggestion()));
				}
				fieldPanels.add(badExitPointPanel);
			}
			
			FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, fieldPanels.toArray(new FieldPanel[fieldPanels.size()]));
			newOptionDialog.setTitle("New Option");
			Main.showWindowInCentre(newOptionDialog);
			
			BranchOption branchOption = newOptionPanel.getResult();
			
			if (becomesExitPoint && !hasObstacle)
			{
				int exitPointID = currentScenario.getExitPointID(exitPointPanel.getResult());
				branchOption.setExitPoint(exitPointID);
			}
			
			if (hasFlavour)
			{
				int flavourListID = currentScenario.getFlavourListID(flavourListPanel.getResult());
				branchOption.setFlavourList(flavourListID);
			}
			
			if (hasToken)
			{
				int tokenID = currentScenario.getTokenID(tokenPanel.getResult());
				branchOption.setToken(tokenID);
			}

			if (becomesExitPoint && hasObstacle)
			{
				int tokenID = currentScenario.getTokenID(obstaclePanel.getResult());
				branchOption.setObstacle(tokenID);
				
				int goodExitPointID = currentScenario.getExitPointID(goodExitPointPanel.getResult());
				branchOption.setGoodExitPointID(goodExitPointID);
				
				int badExitPointID = currentScenario.getExitPointID(badExitPointPanel.getResult());
				branchOption.setBadExitPointID(badExitPointID);
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
