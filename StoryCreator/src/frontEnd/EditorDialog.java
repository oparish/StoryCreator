package frontEnd;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JWindow;


@SuppressWarnings("serial")
public class EditorDialog extends JFrame
{
	private static final int WIDTH = 500;
	private static final int HEIGHT = 500;
	private JEditorPane editorPanel;
	private JTextArea displayPanel;
	private JTextArea infoPanel;
	
	public EditorDialog()
	{
		super();

		this.setLayout(new GridLayout(1, 2));
		this.add(this.setupLeftPanel());
		this.add(this.setupRightPanel());
				
		this.setSize(WIDTH, HEIGHT);	
	}
	
	private JPanel setupLeftPanel()
	{
		JPanel leftPanel = new JPanel();
		this.displayPanel = this.setupDisplayPanel();
		this.editorPanel = this.setupEditorPane();
		leftPanel.setLayout(new GridLayout(2, 1));
		JScrollPane upperScrollPanel = new JScrollPane(this.displayPanel);
		JScrollPane lowerScrollPanel = new JScrollPane(this.editorPanel);	
		leftPanel.add(upperScrollPanel);
		leftPanel.add(lowerScrollPanel);
		return leftPanel;
	}
	
	private JScrollPane setupRightPanel()
	{		
		this.infoPanel = new JTextArea();
		this.infoPanel.setEditable(false);
		JScrollPane rightPanel = new JScrollPane(this.infoPanel);
		return rightPanel;
	}
	
	private JTextArea setupDisplayPanel()
	{
		JTextArea displayPanel = new JTextArea();
		displayPanel.setEditable(false);
		return displayPanel;
	}
	
	private JEditorPane setupEditorPane()
	{
		JEditorPane editorPanel = new JEditorPane();
		editorPanel.setEditable(true);
		return editorPanel;
	}
	
	public static void main(String[] args)
	{
		EditorDialog editorDialog = new EditorDialog();
		editorDialog.addWindowListener(new WindowAdapter() {  
            public void windowClosing(WindowEvent e) {  
                System.exit(0);  
            }  
        });
		Dimension screenCentre = main.Main.getScreenCentre();
		editorDialog.setLocation(screenCentre.width - WIDTH/2, screenCentre.height - HEIGHT/2);
		editorDialog.setVisible(true);
	}
}
