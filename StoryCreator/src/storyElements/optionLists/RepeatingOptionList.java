package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.FieldPanel;
import frontEnd.FlavourListPanel;
import frontEnd.NewBranchPanel;
import frontEnd.NewEndingPanel;
import frontEnd.NewOptionPanel;
import frontEnd.NewScenarioPanel;
import frontEnd.OldOrNewPanel;
import main.Main;
import storyElements.ExitPoint;
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
		
		private Option tryNewOption(EditorDialog editorDialog)
		{
			this.lastNumber = RepeatingOptionList.this.size();
			Scenario currentScenario = Main.getMainScenario();
			
			boolean becomesExitPoint = currentScenario.getOptionBecomesNewExitPoint().check();
			boolean hasFlavour = currentScenario.getOptionHasFlavour().check();
			boolean isLastBranch = currentScenario.checkLastBranch();

			ArrayList<ExitPoint> exitPointsAtLevel = currentScenario.getBranchLevel(currentScenario.getNextBranch());
			HashMap<Integer, FlavourList> flavourLists = currentScenario.getFlavourLists();
			
			ArrayList<FieldPanel> fieldPanels = new ArrayList<FieldPanel>();
			NewOptionPanel newOptionPanel = new NewOptionPanel();
			fieldPanels.add(newOptionPanel);
			
			FieldPanel<? extends ExitPoint> exitPointPanel = null;
			if (becomesExitPoint)
			{
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
					exitPointPanel = new OldOrNewPanel<Branch>((Branch) exitPointsAtLevel.get(Main.getRandomNumberInRange(exitPointsAtLevel.size())), new NewBranchPanel());
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
		
			FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, fieldPanels.toArray(new FieldPanel[fieldPanels.size()]));
			newOptionDialog.setTitle("New Option");
			Main.showWindowInCentre(newOptionDialog);
			
			BranchOption branchOption = newOptionPanel.getResult();
			
			if (becomesExitPoint)
			{
				int exitPointID = currentScenario.getExitPointID(exitPointPanel.getResult());
				branchOption.setExitPoint(exitPointID);
			}
			
			if (hasFlavour)
			{
				int flavourListID = currentScenario.getFlavourListID(flavourListPanel.getResult());
				branchOption.setFlavourList(flavourListID);
			}
			
			RepeatingOptionList.this.add(branchOption);
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
