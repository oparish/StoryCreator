package frontEnd;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import main.Main;
import storyElements.options.StoryElement;

public class StoryElementList<T extends StoryElement> extends JScrollPane
{
	private JList<T> innerList;
	
	public StoryElementList(JList<T> innerList)
	{
		super(innerList);
		this.innerList = innerList;
		this.innerList.setCellRenderer(new StoryElementListCellRenderer());
		this.getViewport().setView(this.innerList);
	}
	
	public ListModel<T> getModel()
	{
		return this.innerList.getModel();
	}
	
	public T getSelectedElement()
	{
		return this.innerList.getSelectedValue();
	}
	
	public T getRandomStoryElement()
	{
		int number = Main.getRandomNumberInRange(this.innerList.getModel().getSize());
		return this.innerList.getModel().getElementAt(number);
	}
	
	private class StoryElementListCellRenderer implements ListCellRenderer<StoryElement>
	{
		@Override
		public Component getListCellRendererComponent(
				JList<? extends StoryElement> list, StoryElement value,
				int index, boolean isSelected, boolean cellHasFocus) {
			String text = value.getDescription();
			if (isSelected)
			{
				JLabel label = new JLabel(text);
				label.setOpaque(true);
				label.setBackground(Color.BLACK);
				label.setForeground(Color.WHITE);
				return label;
			}
			else
			{
				return new JLabel(text);
			}
		}
	}
	
	public static StoryElementList create(Collection<? extends StoryElement> storyElements)
	{
		DefaultListModel<StoryElement> model = new DefaultListModel<StoryElement>();
		for(StoryElement storyElement : storyElements)
		{
			model.addElement(storyElement);
		}
		return new StoryElementList(new JList<StoryElement>(model));
	}
}
