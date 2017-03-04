package frontEnd;

import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class EditorDialog extends JFrame implements ActionListener
{
	private static final String SAVE = "Save";
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	private JEditorPane editorPanel;
	private JTextArea displayPanel;
	private JTextArea infoPanel;
	
	private StringBuilder storyBuilder;
	
	public EditorDialog()
	{
		super();

		this.setLayout(new GridBagLayout());	
		
		this.add(this.setupLeftPanel(), this.setupGridBagConstraints(0, 0, 2, 1));
		this.add(this.setupRightPanel(), this.setupGridBagConstraints(2, 0, 1, 1));
				
		this.setSize(WIDTH, HEIGHT);
		
		this.storyBuilder = new StringBuilder("");
	}
	
	private GridBagConstraints setupGridBagConstraints(int gridx, int gridy, int gridWidth, int gridHeight)
	{
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = gridx;
		gridBagConstraints.gridy = gridy;
		gridBagConstraints.gridwidth = gridWidth;
		gridBagConstraints.gridheight = gridHeight;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		return gridBagConstraints;
	}
	
	private JPanel setupLeftPanel()
	{
		JPanel leftPanel = new JPanel();
		
		this.displayPanel = this.setupDisplayPanel();
		this.editorPanel = this.setupEditorPane();
		leftPanel.setLayout(new GridLayout(2, 1));	
		leftPanel.add(new JScrollPane(this.displayPanel));
		
		JScrollPane scrollPane = new JScrollPane(this.editorPanel);
		scrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
		leftPanel.add(scrollPane);
		
		return leftPanel;
	}
	
	private JPanel setupRightPanel()
	{		
		this.infoPanel = new JTextArea();
		this.infoPanel.setEditable(false);
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(2, 1));
		rightPanel.add(new JScrollPane(this.infoPanel));
		rightPanel.add(this.setupButtonPanel());
		return rightPanel;
	}
	
	private Box setupButtonPanel()
	{
		Box verticalBox = Box.createVerticalBox();
		verticalBox.add(Box.createVerticalGlue());
		
		MyButton saveButton = new MyButton(SAVE, ButtonID.SAVE);
		saveButton.addActionListener(this);
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(saveButton);
		return verticalBox;
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
	
	private void saveText()
	{
		this.storyBuilder.append(this.editorPanel.getText() + "\n\n");
		this.displayPanel.setText(this.storyBuilder.toString());
		this.editorPanel.setText("");
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

	@Override
	public void actionPerformed(ActionEvent e)
	{
		MyButton sourceButton = (MyButton) e.getSource();
		switch(sourceButton.getId())
		{
			case SAVE:
				this.saveText();
			break;
		}
	}
}
