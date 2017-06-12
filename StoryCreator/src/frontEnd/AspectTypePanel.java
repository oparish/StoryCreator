package frontEnd;

import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import main.Main;
import storyElements.Aspect;
import storyElements.AspectList;
import storyElements.AspectType;
import storyElements.options.AspectOption;
import storyElements.options.ExpandingContentType;

public class AspectTypePanel extends MyPanel<AspectType>
{
	private static final String ASPECTLIST_TITLE = "Aspect List";
	
	JTextField description;
	ExpandingPanel<AspectListPanel, AspectList> expandingPanel;
	
	public AspectTypePanel()
	{
		super();
		this.description = new JTextField();
		this.addTextField(this.description, "Description");
		this.expandingPanel = new ExpandingPanel<AspectListPanel, AspectList>(ExpandingContentType.ASPECTLIST, 
				ASPECTLIST_TITLE);
		this.addPanel(this.expandingPanel);
	}
	
	@Override
	public AspectType getResult()
	{
		HashMap<Integer, Aspect> aspects = new HashMap<Integer, Aspect>();
		HashMap<Integer, AspectList> aspectLists = new HashMap<Integer, AspectList>();
		
		int i = 0;
		for (AspectList aspectList : this.expandingPanel.getResult())
		{
			aspectLists.put(i, aspectList);
			i++;	
		}
		
		AspectType aspectType = new AspectType(this.description.getText(), aspectLists, aspects);
		return aspectType;
	}
	
	public static void main(String[] args)
	{
		JDialog window = new JDialog(new JDialog(), true);
		window.setSize(500, 500);
		AspectTypePanel aspectTypePanel = new AspectTypePanel();
		JScrollPane scrollPane = new JScrollPane(aspectTypePanel);
		window.add(scrollPane);
		Main.showWindowInCentre(window);
		System.out.println(aspectTypePanel.getResult().getJsonObjectBuilder().build());
	}
}
