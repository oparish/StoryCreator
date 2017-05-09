package frontEnd;

import javax.swing.JDialog;
import javax.swing.JTextField;

import main.Main;
import storyElements.AspectList;
import storyElements.options.AspectOption;
import storyElements.options.ExpandingContentType;
import storyElements.options.StoryElement;

public class AspectListPanel extends MyPanel<AspectList>
{
	JTextField description;
	ExpandingPanel<AspectOptionPanel, AspectOption> expandingPanel;
	
	public AspectListPanel()
	{
		super();
		this.description = new JTextField();
		this.addTextField(this.description, "Description");
		this.expandingPanel = new ExpandingPanel<AspectOptionPanel, AspectOption>(ExpandingContentType.ASPECTOPTION);
		this.addPanel(this.expandingPanel);
	}
	
	@Override
	public AspectList getResult()
	{
		AspectList aspectList = new AspectList(this.expandingPanel.getResult(), this.description.getText());
		return aspectList;
	}
	
	public static void main(String[] args)
	{
		JDialog window = new JDialog(new JDialog(), true);
		window.setSize(500, 500);
		AspectListPanel aspectListPanel = new AspectListPanel();
		window.add(aspectListPanel);
		Main.showWindowInCentre(window);
		System.out.println(aspectListPanel.getResult().getJsonObjectBuilder().build());
	}

}
