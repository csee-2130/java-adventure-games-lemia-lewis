import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.*;

public class BuildInterface {
	private JFrame mainWindow,helpWindow,startWindow;
	private JLabel mapLabel,healthLabel;
	private JPanel mainPanel,panel, miniPanel, microPanel,mapPanel,healthPanel,buttonPanel;
	private JPanel helpPanel;
	private ImageIcon healthImage,mapImage;
	private JTextField commandField;
	private JButton commitActionButton,helpButton, quitButton;
	private JList pastCommands,inventoryItems,helpCommands;
	private JScrollPane inventoryScroll,pastCommandsScroll,helpCommandScroll;
	private Player mainPlayer;
	private ArrayList<String> pastList;
	
	BuildInterface(){
		initizializePlayer();
		mainWindow= new JFrame("Picnic Adventure");
		mainWindow.setSize(250,250);
		mainPanel=new JPanel();
		mainPanel.setLayout(new BorderLayout());
		buildLowerPanel();//Contains help, quit, and health status
		buildMapPanel();
		buildPlayablePanel();
		
		mainWindow.add(mainPanel);
		mainWindow.pack();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		mainWindow.setVisible(true);
	}
	void initizializePlayer() {
		String name="";//initialize to empty for loop
		try {
		while(name.isBlank()) {
			name=JOptionPane.showInputDialog("Please enter name.", "");
		}
		}
		catch(NullPointerException e) {
			JOptionPane.showMessageDialog(null, "You have lost the choice to choose your name. It is now John Doe");
			name="John Doe";
		}
		mainPlayer=new Player(name);//initialize player
		
	}
	void buildLowerPanel() {
		panel=new JPanel();
		panel.setLayout(new BorderLayout());
		//Make inventory section
		miniPanel= new JPanel();
		miniPanel.setBorder(BorderFactory.createTitledBorder("Inventory"));
		inventoryItems= new JList();//Add list of items
		inventoryItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inventoryScroll=new JScrollPane(inventoryItems);//Make list scroll-able
		inventoryScroll.setPreferredSize(new Dimension(390,90));
		miniPanel.add(inventoryScroll);//Add scrollPane to inventory Panel
		panel.add(miniPanel,BorderLayout.NORTH);
		
		
		//Lowest section
		miniPanel= new JPanel();
		miniPanel.setLayout(new GridLayout(1,2));
			//Health panel Section
		healthPanel= new JPanel();
		healthPanel.setBorder(BorderFactory.createTitledBorder("Health"));
		healthImage= new ImageIcon();
		healthLabel= new JLabel();
		updateHealthBar();
		healthPanel.add(healthLabel);
		miniPanel.add(healthPanel);
			//Button sections
		microPanel= new JPanel();
		helpButton= new JButton("HELP");
		helpButton.addActionListener(new ButtonListener());
		buildHelpWindow();//Window to show up when help is clicked
		quitButton= new JButton("QUIT");
		quitButton.addActionListener(new ButtonListener());
		buttonPanel= new JPanel();
		buttonPanel.add(helpButton);
		microPanel.add(buttonPanel);
		buttonPanel=new JPanel();
		buttonPanel.add(quitButton);
		microPanel.add(buttonPanel);
		miniPanel.add(microPanel);

		panel.add(miniPanel,BorderLayout.SOUTH);
		mainPanel.add(panel,BorderLayout.SOUTH);
	}
	void buildMapPanel() {
		mapPanel= new JPanel();
		mapPanel.setBorder(BorderFactory.createTitledBorder("Map"));
		mapLabel= new JLabel("");
		updateMap();
		mapPanel.add(mapLabel);
		mainPanel.add(mapPanel,BorderLayout.EAST);
	}
	void buildPlayablePanel() {
		panel= new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Input Section"));

		//Add Commands List
		pastList= new ArrayList<String>();//To dynamically store all commands
		//TODO make it so no text needs to be directly added to gui
		pastList.add("You wake up feeling cold. Your body is not keeping you warm correctly.Your eyes seem to");
		pastList.add( " not have taking up enough light. They are too small you are too small. You have shrunk!");
		pastList.add( "Find a way to turn back in the new world you have landed in by inputing text.");
		pastList.add("Every move you make will decrease your health so be careful!");
		pastList.add(mainPlayer.getIntroText());//Add introduction to beginning of list
		pastCommands= new JList();
		updateCommandList();
		pastCommands.setVisibleRowCount(10);
		pastCommandsScroll= new JScrollPane(pastCommands);
		pastCommandsScroll.setPreferredSize(new Dimension(250,250));
        pastCommandsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pastCommandsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		miniPanel= new JPanel();
		miniPanel.add(pastCommandsScroll);
		panel.add(miniPanel,BorderLayout.CENTER);
		
		//Add input section
		miniPanel= new JPanel();
		commandField= new JTextField(10);
		commitActionButton= new JButton("COMMIT");
		commitActionButton.addActionListener(new ButtonListener());
		commandField.addActionListener(new ButtonListener());
		miniPanel.add(commandField);
		miniPanel.add(commitActionButton);
		panel.add(miniPanel,BorderLayout.SOUTH);
		
		
		mainPanel.add(panel,BorderLayout.WEST);
	}
	void updateCommandList() {
		String[] list= new String[pastList.size()];
		int i=0;
		for(String s:pastList) {
			list[i]=s;
			i++;
		}
		pastCommands.setListData(list);
	}
	
	void updateMap() {
		mapImage= new ImageIcon(mainPlayer.getCurrentRoom().getImagePath());
		Image editedImage= mapImage.getImage();
		editedImage= editedImage.getScaledInstance(180,270,Image.SCALE_SMOOTH);
		mapImage= new ImageIcon(editedImage);
		mapLabel.setIcon(mapImage);
	}
	void updateInventory() {
		inventoryItems.setListData(mainPlayer.getInventoryAsString());
	}
	void updateHealthBar() {
		healthImage= new ImageIcon("mapArt\\"+mainPlayer.getHealth()+"_10heart.png");
		Image editedImage= healthImage.getImage();//re-sizable version of image
		editedImage=editedImage.getScaledInstance(190, 35, Image.SCALE_SMOOTH);//set size
		healthImage=new ImageIcon(editedImage);
		healthLabel.setIcon(healthImage);
	}
	void buildHelpWindow() {
		helpWindow= new JFrame();
		helpWindow.setTitle("Did you ask for help?");
		helpWindow.setSize(300,200);
		helpPanel= new JPanel();
		helpCommands= new JList(mainPlayer.getAllPossibleCommand(-1));//only need room
		helpCommands.setVisibleRowCount(10);
		helpCommandScroll= new JScrollPane(helpCommands);
		helpCommandScroll.setPreferredSize(new Dimension(190,205));
		helpPanel.add(helpCommandScroll);
		helpWindow.add(helpPanel);
		helpWindow.pack();
		helpWindow.setVisible(false);
		helpWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	private class ButtonListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)//What the button does when clicked
		{
			if(e.getSource()==quitButton) {
				System.exit(0);//Exit program
			}
			else if(e.getSource()==helpButton) {
				//Update help window before showing
				helpCommands.setListData(mainPlayer.getAllPossibleCommand(inventoryItems.getSelectedIndex()));
				helpWindow.setVisible(true);
			}
			else if(e.getSource()==commitActionButton||e.getSource()==commandField) {
				pastList.add(commandField.getText());//Add command to list
				pastList.add(mainPlayer.checkCommand(commandField.getText().toLowerCase(), inventoryItems.getSelectedIndex()));
				//^ Add result to list
				pastList.add(mainPlayer.getCurrentRoom().getName().toUpperCase()+":"
						+mainPlayer.getCurrentRoom().getDescription());//Show current room description
				//updateAll
				updateCommandList();
				updateMap();
				updateHealthBar();
				updateInventory();
				//Check for player death
				if(mainPlayer.getHealth()==0) {
					JOptionPane.showMessageDialog(null, "Wow..."+mainPlayer.getName()+" almost made it?");//TODO change to 
						//TODO mainPlayer.getDeathMessage() for more advanced game
					System.exit(0);
				}
				//Check miniGame condition
				if(mainPlayer.getCurrentRoom().getUnlockedStatus()&&mainPlayer.getCurrentRoom().checkIfMiniGame()) {
					JOptionPane.showMessageDialog(null, "You see a worm.\"I challenge you to rock paper scissors"
							+ " fight if you want to live.");
					//TODO ^ Make enemy class so not always worm 
					new MiniGame();//Make mini-game linked to specific room or enemy 
					
					
				}
			}
		}
		
	}
}
