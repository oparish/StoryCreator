package main;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Main
{
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
}
