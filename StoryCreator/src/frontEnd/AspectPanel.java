package frontEnd;

import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JTextField;

import storyElements.Aspect;
import storyElements.AspectList;
import storyElements.AspectType;
import storyElements.options.AspectOption;

public class AspectPanel extends MyPanel<Aspect> 
{
	JTextField descriptionField;
	ArrayList<Integer> qualities;
	AspectType aspectType;
	
	public AspectPanel(EditorDialog editorDialog, AspectType aspectType)
	{
		super();
		this.aspectType = aspectType;
		this.qualities = new ArrayList<Integer>();
		ArrayList<AspectList> aspectLists = new ArrayList<AspectList>();
		for (AspectList aspectList : aspectType.getAspectLists().values())
		{
			aspectLists.add(aspectList);
			AspectOption aspectOption = aspectList.generateOption(editorDialog);
			this.qualities.add(aspectOption.getId());
		}
		this.addJLabel(this.aspectType.getDescription());
		this.descriptionField = new JTextField();
		this.addTextField(this.descriptionField, "Description");
		int i = 0;
		for (Integer quality : this.qualities)
		{
			AspectList aspectList = aspectLists.get(i);
			this.add(new JLabel(aspectList.getDescription()), this.setupGridBagConstraints(0, this.yPos, 1, 1, 0));
			this.add(new JLabel(aspectList.get(quality).getDescription()), this.setupGridBagConstraints(1, this.yPos, 1, 1, 0));
			this.yPos++;
			i++;
		}
		
	}
	
	@Override
	public Aspect getResult()
	{
		HashMap<Integer, Integer> qualitiesMap = new HashMap<Integer, Integer>();
		int i = 0;
		for (Integer quality: this.qualities)
		{
			qualitiesMap.put(i, quality);
			i++;
		}
		Aspect aspect = new Aspect(this.aspectType, this.descriptionField.getText(), qualitiesMap);	
		this.aspectType.addAspect(aspect);
		return aspect;
	}

}
