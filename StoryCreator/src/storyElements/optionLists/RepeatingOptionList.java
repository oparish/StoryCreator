package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import frontEnd.EditorDialog;
import frontEnd.NewBranchDialog;
import frontEnd.NewEndingDialog;
import frontEnd.NewOptionDialog;
import frontEnd.NewScenarioDialog;
import main.Main;
import storyElements.Scenario;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;
import storyElements.options.StoryElement;

@SuppressWarnings("serial")
public abstract class RepeatingOptionList<T extends Option> extends OptionList<T>
{
	public RepeatingOptionList()
	{
		super();
	}
	
	public RepeatingOptionList(ArrayList<T> initialOptions)
	{
		super(initialOptions);
	}
	
	protected abstract StoryElement getDefaultExitPoint(EditorDialog editorDialog);

	public Generator getGenerator()
	{
		return new Generator();
	}
	
	public class Generator
	{
		private Subplot currentSubplot = null;
		private Integer lastNumber = null;
		private int counter = 0;
		
		private Generator()
		{
			
		}
		
		public StoryElement generateStoryElement(EditorDialog editorDialog)
		{
			if (this.counter == Main.getMainScenario().getBranchLength())
			{
				return RepeatingOptionList.this.getDefaultExitPoint(editorDialog);
			}
			
			counter++;
			int rnd = Main.getRandomNumberInRange(RepeatingOptionList.this.size());
			if (this.lastNumber == null || this.lastNumber != rnd)
			{
				this.lastNumber = rnd;
				return RepeatingOptionList.this.get(rnd);
			}
			else
			{
				StoryElement storyElement = this.tryNewOption(editorDialog);
				return storyElement;
			}	
		}

		private BranchOption makeNewBranch(EditorDialog editorDialog)
		{
			NewBranchDialog newBranchDialog = new NewBranchDialog(editorDialog, true, Main.INITIALOPTIONS_FOR_SCENARIO, true);
			newBranchDialog.setTitle("New Branch");
			Main.showWindowInCentre(newBranchDialog);
	    	Branch branch = newBranchDialog.getNewBranch();
	    	Scenario scenario = Main.getMainScenario();
	    	Integer exitPointID = scenario.addExitPoint(branch);
	    	BranchOption branchOption = new BranchOption(newBranchDialog.getOptionHeader());
	    	branchOption.setExitPoint(exitPointID);
	    	return branchOption;
		}
		
		private Option makeNewEnding(EditorDialog editorDialog)
		{
			NewEndingDialog newEndingDialog = new NewEndingDialog(editorDialog, true, true);
			newEndingDialog.setTitle("New Ending");
			Main.showWindowInCentre(newEndingDialog);
			BranchOption branchOption = newEndingDialog.getBranchOption();
			EndingOption endingOption = newEndingDialog.getEndingOption();
			Scenario currentScenario = Main.getMainScenario();
			Integer exitPointID = currentScenario.addExitPoint(endingOption);
			branchOption.setExitPoint(exitPointID);
	        return branchOption;
		}
		
		private Option tryNewOption(EditorDialog editorDialog)
		{
			this.lastNumber = RepeatingOptionList.this.size();
			Scenario currentScenario = Main.getMainScenario();
			Option option;
			
			if (currentScenario.getOptionBecomesNewExitPoint().check())
			{
				if (currentScenario.checkLastBranch())
					option = this.makeNewEnding(editorDialog);
				else
					option = this.makeNewBranch(editorDialog);
			}
			else
			{
				option = this.makeNewOption(editorDialog);
			}
			RepeatingOptionList.this.add(option);
			return option;
		}
		

		private BranchOption makeNewOption(EditorDialog editorDialog)
		{
			NewOptionDialog newOptionDialog = new NewOptionDialog(editorDialog, true);
			newOptionDialog.setTitle("New Option");
			Main.showWindowInCentre(newOptionDialog);
	    	BranchOption branchOption = newOptionDialog.getOption();
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
