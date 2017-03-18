package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.FieldPanel;
import frontEnd.NewBranchPanel;
import frontEnd.NewEndingPanel;
import frontEnd.NewOptionPanel;
import frontEnd.NewScenarioPanel;
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
			NewOptionPanel newOptionPanel = new NewOptionPanel();
			NewBranchPanel newBranchPanel = new NewBranchPanel(Main.INITIALOPTIONS_FOR_SCENARIO);
			FieldDialog newBranchDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{newOptionPanel, newBranchPanel});
			newBranchDialog.setTitle("New Branch");
			Main.showWindowInCentre(newBranchDialog);
	    	Scenario scenario = Main.getMainScenario();
	    	Branch branch = newBranchPanel.getNewBranch(scenario.getNextBranch());
	    	Integer exitPointID = scenario.addExitPoint(branch);
	    	BranchOption branchOption = newOptionPanel.getOption();
	    	branchOption.setExitPoint(exitPointID);
	    	return branchOption;
		}
		
		private Option makeNewEnding(EditorDialog editorDialog)
		{
			NewOptionPanel newOptionPanel = new NewOptionPanel();
			NewEndingPanel newEndingPanel = new NewEndingPanel();
			FieldDialog newEndingDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{newEndingPanel, newOptionPanel});
			newEndingDialog.setTitle("New Ending");
			Main.showWindowInCentre(newEndingDialog);
			EndingOption endingOption = newEndingPanel.getEndingOption();
			BranchOption branchOption = newOptionPanel.getOption();
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
			NewOptionPanel newOptionPanel = new NewOptionPanel();
			FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, new FieldPanel[]{newOptionPanel});
			newOptionDialog.setTitle("New Option");
			Main.showWindowInCentre(newOptionDialog);
	    	BranchOption branchOption = newOptionPanel.getOption();
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
