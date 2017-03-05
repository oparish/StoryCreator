package storyElements.optionLists;

import java.util.ArrayList;

import main.Main;
import storyElements.options.Option;

@SuppressWarnings("serial")
public class RepeatingOptionList<T extends Option> extends OptionList<T>
{

	public RepeatingOptionList(ArrayList<T> initialOptions)
	{
		super(initialOptions);
	}

	public Generator getGenerator()
	{
		return new Generator();
	}
	
	public class Generator
	{
		private Subplot currentSubplot = null;
		private Generator()
		{
			
		}
		
		public Option getOption()
		{
			int rnd = Main.getRandomNumberInRange(RepeatingOptionList.this.size());
			return RepeatingOptionList.this.get(rnd);
		}
		

		public Subplot getCurrentSubplot() {
			return currentSubplot;
		}

		public void setCurrentSubplot(Subplot currentSubplot) {
			this.currentSubplot = currentSubplot;
		}
	}
	
}
