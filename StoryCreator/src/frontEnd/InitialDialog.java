package frontEnd;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Main;
import storyElements.Scenario;

public abstract class InitialDialog extends JFrame implements ActionListener
{
	protected static final int WIDTH = 400;
	protected static final int HEIGHT = 150;
	
	public InitialDialog()
	{
		super();
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new GridBagLayout());
	}
	
	protected MyButton makeButton (String text, ButtonID buttonID)
	{
		MyButton myButton = new MyButton(text, buttonID);
		myButton.addActionListener(this);
		return myButton;
	}
	
	protected GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridWidth;
		gridBagConstraints.gridheight = gridHeight;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);
		return gridBagConstraints;
	}
	
	protected File loadFile()
	{
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Text", "txt"));
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File loadFile = chooser.getSelectedFile();
			return loadFile;
		}
		return null;
	}
	
	protected JsonObject loadJsonObject(File file)
	{
		JsonObject jsonObject = null;
		FileReader fileReader;
		try
		{
			fileReader = new FileReader(file);
			JsonReader jsonReader= Json.createReader(fileReader);	
			jsonObject = jsonReader.readObject();
			jsonReader.close();	
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return jsonObject;		
	}
}
