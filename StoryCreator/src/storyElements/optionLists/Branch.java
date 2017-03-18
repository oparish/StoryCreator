package storyElements.optionLists;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import frontEnd.EditorDialog;
import frontEnd.NewBranchDialog;
import frontEnd.NewEndingDialog;
import frontEnd.NewOptionDialog;
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
		NewEndingDialog newEndingDialog = new NewEndingDialog(editorDialog, true, false);
		newEndingDialog.setTitle("New Ending");
		Main.showWindowInCentre(newEndingDialog);
		Scenario currentScenario = Main.getMainScenario();
		EndingOption endingOption = newEndingDialog.getEndingOption();
		this.defaultExitPoint = currentScenario.addExitPoint(endingOption);
		return endingOption;
	}
	
	private ExitPoint createBranch(EditorDialog editorDialog)
	{
		NewBranchDialog newBranchDialog = new NewBranchDialog(editorDialog, true, Main.INITIALOPTIONS_FOR_SCENARIO, false);
		Main.showWindowInCentre(newBranchDialog);
		Scenario currentScenario = Main.getMainScenario();
		Branch newBranch = newBranchDialog.getNewBranch(currentScenario.getNextBranch());
		this.defaultExitPoint = currentScenario.addExitPoint(newBranch);
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
