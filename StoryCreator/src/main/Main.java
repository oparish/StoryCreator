package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;

import frontEnd.EditorDialog;
import frontEnd.InitialDialog;
import storyElements.ExitPoint;
import storyElements.Scenario;
import storyElements.optionLists.Branch;
import storyElements.options.EndingOption;


public class Main
{
	public static final int INITIALOPTIONNUMBER = 3;
	public static final int BRANCHLENGTH = 2;
	public static final int SUBPLOTLENGTH = 2;
	public static final int SCENARIOLENGTH = 3;
	
	public static final String TYPE = "type";
	public static final String BRANCH = "branch";
	public static final String ENDINGOPTION = "endingoption";
	public static final String EXITPOINT = "exitpoint";
	public static final String DESCRIPTION = "description";
	public static final String BRANCH_LENGTH = "branch_length";
	public static final String SUBPLOT_LENGTH = "subplot_length";
	public static final String SCENARIO_LENGTH = "scenario_length";
	public static final String OPTION_BECOMES_SUBPLOT = "optionBecomesSubplot";
	public static final String OPTION_BECOMES_NEW_BRANCH = "optionBecomesNewBranch";
	public static final String FLAVOUR_HAS_SUBFLAVOUR = "flavourHasSubFlavour";
	public static final String EXITPOINTS = "exitpoints";
	public static final String FLAVOURLISTS = "flavourLists";
	public static final String SUBPLOTS = "subplots";
	public static final String STARTING_BRANCH = "startingbranch";
	public static final String OPTIONS = "options";
	public static final String SUBPLOT = "subplot";
	public static final String FLAVOURLIST = "flavour";
	public static final String SUBFLAVOURLIST = "subflavourlist";
	
	private static Random random = new Random();
	private static Scenario mainScenario;
	
	public static ExitPoint getFromJson(JsonObject jsonObject)
	{
		if (jsonObject.getString(Main.TYPE).matches(Main.BRANCH))
		{
			return new Branch(jsonObject);
		}
		else
		{
			return new EndingOption(jsonObject);
		}
	}
	
	public static Integer processJsonInt(JsonObject jsonObject, String key)
	{
		if (jsonObject.containsKey(key))
			return jsonObject.getInt(key);
		else
			return null;
	}
	
	public static Dimension getScreenCentre()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension(screenSize.width/2, screenSize.height/2);
	}
	
	public static void saveTextToFile(File saveFile, StringBuilder saveText, StringBuilder saveText2)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(saveFile);
			fileWriter.write(saveText.toString() + "\r\n" + saveText2.toString());
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveScenario(File saveFile, Scenario scenario)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(saveFile);
			JsonWriter jsonWriter = Json.createWriter(fileWriter);
			jsonWriter.writeObject(scenario.getJsonObject());
			jsonWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static int getRandomPercent()
	{
		return random.nextInt(99);
	}
	
	public static int getRandomNumberInRange(int range)
	{
		return random.nextInt(range);
	}
	
	public static Scenario getMainScenario()
	{
		return Main.mainScenario;
	}
	
	public static void setMainScenario(Scenario scenario)
	{
		Main.mainScenario = scenario;
	}
	
	public static void showWindowInCentre(Window window)
	{
		Dimension screenCentre = main.Main.getScreenCentre();
		window.setLocation(screenCentre.width - window.getWidth()/2, screenCentre.height - window.getHeight()/2);
		window.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		InitialDialog initialDialog = new InitialDialog();
		initialDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });
		Dimension screenCentre = main.Main.getScreenCentre();
		initialDialog.setLocation(screenCentre.width - initialDialog.getWidth()/2, screenCentre.height - initialDialog.getHeight()/2);
		initialDialog.setVisible(true);
	}
}
