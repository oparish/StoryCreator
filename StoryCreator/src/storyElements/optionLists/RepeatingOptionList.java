package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import frontEnd.EditorDialog;
import frontEnd.NewOptionDialog;
import frontEnd.NewScenarioDialog;
import main.Main;
import storyElements.options.BranchOption;
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

	public Generator getGenerator()
	{
		return new Generator();
	}
	
	public class Generator
	{
		private Subplot currentSubplot = null;
		private Integer lastNumber = null;
		
		private Generator()
		{
			
		}
		
		public void generateOption(EditorDialog editorDialog)
		{
			int rnd = Main.getRandomNumberInRange(RepeatingOptionList.this.size());
			if (this.lastNumber == null || this.lastNumber != rnd)
			{
				this.lastNumber = rnd;
				editorDialog.setOption(RepeatingOptionList.this.get(rnd));
			}
			else
			{
				this.lastNumber = null;
				NewOptionDialog newScenarioDialog = new NewOptionDialog(editorDialog, true, RepeatingOptionList.this);
				newScenarioDialog.addWindowListener(new WindowAdapter() {  
		            public void windowClosing(WindowEvent e) {
		            	NewOptionDialog newOptionDialog = (NewOptionDialog) e.getWindow();
		            	BranchOption branchOption = newOptionDialog.getOption();
		            	EditorDialog editorDialog = (EditorDialog) newOptionDialog.getOwner();
		                editorDialog.setOption(branchOption);
		                newOptionDialog.getOptionList().add(branchOption);
		            }  
		        });
				Main.showWindowInCentre(newScenarioDialog);
			}	
		}
		

		public Subplot getCurrentSubplot() {
			return currentSubplot;
		}

		public void setCurrentSubplot(Subplot currentSubplot) {
			this.currentSubplot = currentSubplot;
		}
	}
	
}
