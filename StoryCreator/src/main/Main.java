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

import frontEnd.EditorDialog;
import frontEnd.InitialDialog;
import storyElements.Scenario;


public class Main
{
	public static final int INITIALOPTIONNUMBER = 3;
	public static final int BRANCHLENGTH = 4;
	public static final int SUBPLOTLENGTH = 2;
	
	private static Random random = new Random();
	private static Scenario mainScenario;
	
	
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
