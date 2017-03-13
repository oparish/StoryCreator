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
import frontEnd.NewOptionDialog;
import main.Main;
import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.options.BranchOption;
import storyElements.options.Option;

@SuppressWarnings("serial")
public class Branch extends StorySection<BranchOption> implements ExitPoint
{	
	private Integer defaultExitPoint = null;

	public ExitPoint getDefaultExitPoint() {
		return Main.getMainScenario().getExitPoint(this.defaultExitPoint);
	}

	public void setDefaultExitPoint(Integer defaultExitPoint) {
		this.defaultExitPoint = defaultExitPoint;
	}

	public Branch(ArrayList<BranchOption> initialOptions, String description)
	{
		super(initialOptions, description);
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
	}
	
	protected void end(EditorDialog editorDialog)
	{
		if (this.defaultExitPoint == null)
		{
			this.createDefaultExitPoint(editorDialog);
		}
		else
		{
			Main.getMainScenario().getExitPoint(this.defaultExitPoint).useAsExitPoint(editorDialog);
		}
	}
	
	private void createDefaultExitPoint(EditorDialog editorDialog)
	{
		Scenario scenario = Main.getMainScenario();
		if (scenario.checkLastBranch())
			this.createEndingOption(editorDialog);
		else
			this.createBranch(editorDialog);
	}
	
	private void createEndingOption(EditorDialog editorDialog)
	{
		NewOptionDialog newOptionDialog = new NewOptionDialog(editorDialog, true, null);
		newOptionDialog.setTitle("New Ending");
		newOptionDialog.setBranch(this);
		newOptionDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {
        		Scenario currentScenario = Main.getMainScenario();
        		NewOptionDialog newOptionDialog = (NewOptionDialog)e.getWindow();
            	Integer defaultExitPoint = currentScenario.addExitPoint(newOptionDialog.getEndingOption());
            	newOptionDialog.getBranch().setDefaultExitPoint(defaultExitPoint);
        		currentScenario.getExitPoint(defaultExitPoint).useAsExitPoint((EditorDialog) e.getWindow().getOwner());
            }  
        });
		Dimension screenCentre = main.Main.getScreenCentre();
		newOptionDialog.setLocation(screenCentre.width - newOptionDialog.getWidth()/2, screenCentre.height - newOptionDialog.getHeight()/2);
		newOptionDialog.setVisible(true);
	}
	
	private void createBranch(EditorDialog editorDialog)
	{
		NewBranchDialog newBranchDialog = new NewBranchDialog(editorDialog, true, Main.INITIALOPTIONS_FOR_SCENARIO);
		newBranchDialog.setBranch(this);
		newBranchDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
        		Scenario currentScenario = Main.getMainScenario();
        		NewBranchDialog newBranchDialog = (NewBranchDialog) e.getWindow();
            	Integer defaultExitPoint = currentScenario.addExitPoint(newBranchDialog.getNewBranch());
            	newBranchDialog.getBranch().setDefaultExitPoint(defaultExitPoint);
        		currentScenario.getExitPoint(defaultExitPoint).useAsExitPoint((EditorDialog) e.getWindow().getOwner());
            }  
        });
		Dimension screenCentre = main.Main.getScreenCentre();
		newBranchDialog.setLocation(screenCentre.width - newBranchDialog.getWidth()/2, screenCentre.height - newBranchDialog.getHeight()/2);
		newBranchDialog.setVisible(true);	
	}
	
	public JsonObjectBuilder getJsonObjectBuilder()
	{
		JsonObjectBuilder jsonObjectBuilder = super.getJsonObjectBuilder();
		Scenario currentScenario = Main.getMainScenario();
		jsonObjectBuilder.add(Main.TYPE, Main.BRANCH);
		if (this.defaultExitPoint != null)
			jsonObjectBuilder.add(Main.EXITPOINT, this.defaultExitPoint);
		return jsonObjectBuilder;
	}

	@Override
	public void useAsExitPoint(EditorDialog editorDialog)
	{
		Scenario currentScenario = Main.getMainScenario();
		currentScenario.setCurrentBranch(this);
		editorDialog.startNewBranch();
	}
}
