package storyElements.optionLists;

import java.util.ArrayList;

import javax.json.JsonObject;
import javax.json.JsonValue;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.MyPanel;
import frontEnd.SubPlotOptionPanel;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.NewSubplotPanel;
import main.Main;
import storyElements.ExitPoint;
import storyElements.optionLists.Branch.Generator;
import storyElements.options.BranchOption;
import storyElements.options.Option;
import storyElements.options.OptionContentType;
import storyElements.options.StoryElement;
import storyElements.options.SubplotOption;

@SuppressWarnings("serial")
public class Subplot extends StorySection<SubplotOption> implements StoryElement
{
	private boolean useOpening;
	
	public void setUseOpening(boolean useOpening) {
		this.useOpening = useOpening;
	}

	public Subplot(ArrayList<SubplotOption> initialOptions, String description)
	{
		super(initialOptions, description);
		this.useOpening = false;
	}
	
	public Subplot(JsonObject jsonObject)
	{
		super();
		for (JsonValue optionJson : jsonObject.getJsonArray(Main.OPTIONS))
		{
			this.add(new SubplotOption((JsonObject) optionJson));
		}
		this.description = jsonObject.getString(Main.DESCRIPTION);
		this.useOpening = true;
	}
	
	public Generator getGenerator()
	{
		return new Generator();
	}
	
	public class Generator
	{
		private Integer lastNumber = null;
		private boolean openingUnused = true;
		
		public SubplotOption generateStoryElement(EditorDialog editorDialog)
		{
			if (Subplot.this.useOpening && this.openingUnused)
			{
				this.openingUnused = false;
				
				if (Main.getMainScenario().getBranchHasOpening().check())
				{
					this.lastNumber = Subplot.this.size();
					return this.tryNewOption(editorDialog);
				}
			}
			
			int rnd = Main.getRandomNumberInRange(Subplot.this.size());
			if (this.lastNumber == null || this.lastNumber != rnd)
			{
				this.lastNumber = rnd;
				return (SubplotOption) Subplot.this.get(rnd);
			}
			else
			{
				SubplotOption storyElement = this.tryNewOption(editorDialog);
				return storyElement;
			}	
		}
		
		private SubplotOption tryNewOption(EditorDialog editorDialog)
		{
			SubPlotOptionPanel subplotPanel = new SubPlotOptionPanel(Subplot.this);
			
			FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, new MyPanel[]{subplotPanel});
			newOptionDialog.setTitle("New Option");
			Main.showWindowInCentre(newOptionDialog);
			
			SubplotOption subplotOption = subplotPanel.getResult();
			Subplot.this.add(subplotOption);
			
			return subplotOption;
		}
	}
}
