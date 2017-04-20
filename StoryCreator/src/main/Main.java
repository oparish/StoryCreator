package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.stream.JsonParser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import frontEnd.EditorDialog;
import frontEnd.FieldDialog;
import frontEnd.StartDialog;
import frontEnd.fieldPanel.NewOptionPanel;
import storyElements.ExitPoint;
import storyElements.JsonStructure;
import storyElements.Scenario;
import storyElements.Spice;
import storyElements.optionLists.Branch;
import storyElements.options.EndingOption;


public class Main
{
	public static final int INITIALOPTIONS_FOR_SCENARIO = 1;
	public static final int INITIALOPTIONS_FOR_SPICE = 3;
	public static final int INITIALOPTIONS_FOR_FLAVOURLIST = 3;
	public static final int INITIALOPTIONS_FOR_BRANCH = 1;
	public static final int INITIALOPTIONS_FOR_SUBPLOT = 1;
	public static final int INITIALTWISTS_FOR_SPICE = 3;
	public static final int SUGGESTION_NUMBER = 4;
	public static final int DEFAULTBRANCHLENGTH = 3;
	public static final int DEFAULTSUBPLOTLENGTH = 2;
	public static final int DEFAULTSCENARIOLENGTH = 3;
	public static final int DEFAULTSUDDENENDING_CHANCE = 10;
	public static final int DEFAULTOPENINGCHANCE = 50;
	public static final int DEFAULT_MINIMUM_SCENARIO_LENGTH = 2;
	
	public static final String BRANCH = "branch";
	public static final String ENDINGOPTION = "endingoption";
	public static final String EXITPOINT = "exitpoint";
	public static final String GOOD_EXITPOINT = "goodexitpoint";
	public static final String BAD_EXITPOINT = "badexitpoint";
	public static final String DESCRIPTION = "description";
	public static final String BRANCH_LENGTH = "branch_length";
	public static final String SUBPLOT_LENGTH = "subplot_length";
	public static final String SCENARIO_LENGTH = "scenario_length";
	public static final String OPTION_BECOMES_SUBPLOT = "optionBecomesSubplot";
	public static final String OPTION_BECOMES_NEW_EXITPOINT = "optionBecomesExitPoint";
	public static final String FLAVOUR_HAS_SUBFLAVOUR = "flavourHasSubFlavour";
	public static final String OPTION_HAS_OBSTACLE = "optionHasObstacle";
	public static final String OPTION_HAS_TOKEN = "optionHasToken";
	public static final String EXITPOINTS = "exitpoints";
	public static final String FLAVOURLISTS = "flavourLists";
	public static final String SUBPLOTS = "subplots";
	public static final String OPTIONS = "options";
	public static final String SUBPLOT = "subplot";
	public static final String FLAVOURLIST = "flavourlist";
	public static final String SUBFLAVOURLIST = "subflavourlist";
	public static final String TWISTLISTS = "twistlists";
	public static final String SUGGESTIONS = "suggestions";
	public static final String GOOD_SUGGESTIONS = "goodSuggestions";
	public static final String BAD_SUGGESTIONS = "badSuggestions";
	public static final String BRANCH_LEVEL = "branchlevel";
	public static final String TOKENS = "tokens";
	public static final String TOKEN = "token";
	public static final String OBSTACLE = "obstacle";
	public static final String OPENINGCHANCE = "openingchance";
	public static final String SUDDENENDING_CHANCE = "suddenendingchance";
	public static final String MINIMUM_SCENARIO_LENGTH = "minimumscenariolength";
	
	private static Random random = new Random();
	private static Scenario mainScenario;
	private static File scenarioFile;
	private static Spice mainSpice;
	private static File spiceFile;
	
	public static ExitPoint getFromJson(JsonObject jsonObject, int scenarioLength)
	{
		if (jsonObject.getInt(Main.BRANCH_LEVEL) != scenarioLength)
		{
			return new Branch(jsonObject);
		}
		else
		{
			return new EndingOption(jsonObject);
		}
	}
	
	public static Spice getMainSpice()
	{
		return Main.mainSpice;
	}

	public static void setMainSpice(Spice mainSpice)
	{
		Main.mainSpice = mainSpice;
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
	
	public static void saveTextToFile(File saveFile, StringBuilder saveText, String editorText, StringBuilder saveText2)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(saveFile);
			fileWriter.write(saveText.toString() + "\r\n" + editorText + "\r\n\r\n" + saveText2.toString());
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static File getScenarioFile()
	{
		return Main.scenarioFile;
	}
	
	public static void setScenarioFile(File file)
	{
		Main.scenarioFile = file;
	}
	
	public static File getSpiceFile()
	{
		return Main.spiceFile;
	}
	
	public static void setSpiceFile(File file)
	{
		Main.spiceFile = file;
	}
	
	public static void saveJsonStructureToFile(File saveFile, JsonStructure jsonStructure)
	{
		try
		{
			FileWriter fileWriter = new FileWriter(saveFile);
			JsonWriter jsonWriter = Json.createWriter(fileWriter);
			jsonWriter.writeObject(jsonStructure.getJsonObject());
			fileWriter.close();
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
	
	public static ArrayList<String> getStringsFromJsonArray(JsonArray jsonArray)
	{
		ArrayList<String> strings = new ArrayList<String>();
		if (jsonArray == null)
			return strings;
		for (JsonValue stringJson : jsonArray)
		{
			strings.add(((JsonString) stringJson).getString());
		}
		return strings;
	}
	
	public static JsonArrayBuilder getJsonBuilderForStrings(ArrayList<String> strings)
	{
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for (String string: strings)
		{
			jsonArrayBuilder.add(string);
		}
		return jsonArrayBuilder;
	}
	
	public static void main(String[] args)
	{
		StartDialog startDialog = new StartDialog();
        Main.showWindowInCentre(startDialog); 
        EditorDialog editorDialog = new EditorDialog(startDialog.getBranchLevel());
        Main.showWindowInCentre(editorDialog); 
	}
}
