package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import frontEnd.EditorDialog;
import frontEnd.NewBranchDialog;
import frontEnd.NewOptionDialog;
import frontEnd.NewScenarioDialog;
import main.Main;
import storyElements.Scenario;
import storyElements.options.BranchOption;
import storyElements.options.EndingOption;
import storyElements.options.Option;

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
	
	protected abstract void end(EditorDialog editorDialog);

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
		
		public void generateOption(EditorDialog editorDialog)
		{
			if (counter == Main.getMainScenario().getBranchLength())
			{
				RepeatingOptionList.this.end(editorDialog);
				return;
			}
			
			counter++;
			int rnd = Main.getRandomNumberInRange(RepeatingOptionList.this.size());
			if (this.lastNumber == null || this.lastNumber != rnd)
			{
				this.lastNumber = rnd;
				editorDialog.setOption(RepeatingOptionList.this.get(rnd));
			}
			else
			{
				this.tryNewOption(editorDialog);
			}	
		}

		private void makeNewBranch(EditorDialog editorDialog)
		{
			NewBranchDialog newBranchDialog = new NewBranchDialog(editorDialog, true, Main.INITIALOPTIONNUMBER);
			newBranchDialog.setTitle("New Branch");
			newBranchDialog.addWindowListener(new WindowAdapter() {  
			    public void windowClosing(WindowEvent e) {
			    	NewBranchDialog newBranchDialog = (NewBranchDialog) e.getWindow();
			    	Branch branch = newBranchDialog.getNewBranch();
			    	EditorDialog editorDialog = (EditorDialog) newBranchDialog.getOwner();
			    	Scenario scenario = Main.getMainScenario();
			    	scenario.addExitPoint(branch);
			    	scenario.setCurrentBranch(branch);
			        editorDialog.startNewBranch();
			    }  
			});
			Main.showWindowInCentre(newBranchDialog);
		}
		
		private void makeNewEnding(EditorDialog editorDialog)
		{
			NewOptionDialog newEndingDialog = new NewOptionDialog(editorDialog, true, RepeatingOptionList.this);
			newEndingDialog.setTitle("New Ending");
			newEndingDialog.addWindowListener(new WindowAdapter() {  
			    public void windowClosing(WindowEvent e) {
			    	NewOptionDialog newEndingDialog = (NewOptionDialog) e.getWindow();
			    	EndingOption endingOption = newEndingDialog.getEndingOption();		
			        newEndingDialog.getOptionList().add(endingOption);
			    	EditorDialog editorDialog = (EditorDialog) newEndingDialog.getOwner();
			    	endingOption.useAsExitPoint(editorDialog);
			    }  
			});
			Main.showWindowInCentre(newEndingDialog);
		}
		
		private void tryNewOption(EditorDialog editorDialog)
		{
			this.lastNumber = RepeatingOptionList.this.size();
			Scenario currentScenario = Main.getMainScenario();
			
			if (currentScenario.getOptionBecomesNewExitPoint().check())
			{
				if (currentScenario.checkLastBranch())
					this.makeNewEnding(editorDialog);
				else
					this.makeNewBranch(editorDialog);
			}
			else
			{
				this.makeNewOption(editorDialog);
			}
		}
		

		private void makeNewOption(EditorDialog editorDialog)
		{
			NewOptionDialog newOptionDialog = new NewOptionDialog(editorDialog, true, RepeatingOptionList.this);
			newOptionDialog.setTitle("New Option");
			newOptionDialog.addWindowListener(new WindowAdapter() {  
			    public void windowClosing(WindowEvent e) {
			    	NewOptionDialog newOptionDialog = (NewOptionDialog) e.getWindow();
			    	BranchOption branchOption = newOptionDialog.getOption();
			    	EditorDialog editorDialog = (EditorDialog) newOptionDialog.getOwner();
			        editorDialog.setOption(branchOption);
			        newOptionDialog.getOptionList().add(branchOption);
			    }  
			});
			Main.showWindowInCentre(newOptionDialog);
		}
		

		public Subplot getCurrentSubplot() {
			return currentSubplot;
		}

		public void setCurrentSubplot(Subplot currentSubplot) {
			this.currentSubplot = currentSubplot;
		}
	}
	
}
