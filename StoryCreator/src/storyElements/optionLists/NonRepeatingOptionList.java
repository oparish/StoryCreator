package storyElements.optionLists;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.json.JsonObject;

import main.Main;
import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.MyPanel;
import frontEnd.fieldPanel.FieldPanel;
import frontEnd.fieldPanel.NewOptionPanel;
import storyElements.options.BranchOption;
import storyElements.options.Option;

@SuppressWarnings("serial")
public class NonRepeatingOptionList<T extends Option> extends OptionList<T> {

	protected String type = "Option";
	private Class panelClass;
	
	public NonRepeatingOptionList(ArrayList<T> initialOptions, Class panelClass)
	{
		super(initialOptions);
		this.panelClass = panelClass;
	}
	
	public NonRepeatingOptionList(Class panelClass)
	{
		super();
		this.panelClass = panelClass;
	}
	
	private MyPanel<T> getOptionPanel()
	{
		try {
			return (MyPanel<T>) this.panelClass.getConstructor(this.getClass()).newInstance(this);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
		
	public T generateOption(EditorDialog editorDialog)
	{
		int rnd = Main.getRandomNumberInRange(this.size() + 1);
		if (rnd < this.size())
		{
			return (T) this.get(rnd);
		}
		else
		{
			return this.generateNewOption(editorDialog);
		}	
	}

	protected T generateNewOption(EditorDialog editorDialog)
	{
		MyPanel<T> panel = this.getOptionPanel();
		FieldDialog newOptionDialog = new FieldDialog(editorDialog, true, new MyPanel[]{panel});
		newOptionDialog.setTitle("New " + this.type);
		Main.showWindowInCentre(newOptionDialog);
		T option = panel.getResult();
		NonRepeatingOptionList.this.add(option);
		return option;
	}
	
}
