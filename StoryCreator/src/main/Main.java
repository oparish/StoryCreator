package main;
import java.awt.Dimension;
import java.awt.Toolkit;


public class Main
{
	public static Dimension getScreenCentre()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension(screenSize.width/2, screenSize.height/2);
	}
}
