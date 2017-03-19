package storyElements.optionLists;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.FieldPanel;
import frontEnd.NewBranchPanel;
import frontEnd.NewEndingPanel;
import frontEnd.NewOptionPanel;
import frontEnd.OldOrNewPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
import storyElements.options.StoryElement;

@SuppressWarnings("serial")
public class Branch extends StorySection<BranchOption> implements ExitPoint
{	
	private Integer defaultExitPoint = null;
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
}
