import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;


public class MiniGame {
	private JFrame gameWindow;
	private JPanel panel,buttonPanel,miniPanel;
	private JLabel enemyLabel,rulesLabel;
	private JButton moveButton;
	int winCount=0;
	//TODO Enemy with different rock paper scissors images and random taunts
	MiniGame(){
		gameWindow= new JFrame();
		gameWindow.setTitle("ROCK PAPER SCISSORS");
		buildPanel();
		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		gameWindow.setVisible(true);
	}
	void buildPanel() {
		panel= new JPanel();
		panel.setLayout(new BorderLayout());
		
		//Set buttons and shortcuts
		buttonPanel= new JPanel();
		buttonPanel.setLayout( new BorderLayout());
		moveButton= new JButton("ROCK");
		moveButton.addActionListener(new ButtonListener());
		buttonPanel.add(moveButton,BorderLayout.WEST);
		moveButton= new JButton("PAPER");
		moveButton.addActionListener(new ButtonListener());
		buttonPanel.add(moveButton,BorderLayout.CENTER);
		moveButton= new JButton("SCISSORS");
		moveButton.addActionListener(new ButtonListener());
		buttonPanel.add(moveButton,BorderLayout.EAST);
		panel.add(buttonPanel,BorderLayout.SOUTH);
		
		miniPanel=new JPanel();
		miniPanel.setLayout(new BorderLayout());
		enemyLabel= new JLabel("READY?");
		miniPanel.add(enemyLabel,BorderLayout.CENTER);
		panel.add(enemyLabel,BorderLayout.CENTER);
		rulesLabel= new JLabel("BEAT YOUR ENEMY THREE TIMES TO WIN!");
		panel.add(rulesLabel,BorderLayout.NORTH);
		
		
		gameWindow.add(panel);
	}
	private class ButtonListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e)//What the button does when clicked
		{
			Random rand= new Random();
			int randNum= rand.nextInt(3);
			if(randNum==0) {
				enemyLabel.setText("ROCK");
			}
			else if(randNum==1) {
				enemyLabel.setText("PAPER");
			}
			else if(randNum==2) {
				enemyLabel.setText("SCISSORS");
			}
			if(e.getActionCommand().equals(enemyLabel.getText())) {
				winCount++;
				if(winCount==3) {
					JOptionPane.showMessageDialog( null, "Fine you win.");
					gameWindow.setVisible(false);
					JOptionPane.showMessageDialog(null,"There is nothing here for you now. Goodbye.");
				}
				else {
					JOptionPane.showMessageDialog(null ,"You won the battle not the war.");
				}
			}
			
		}
	}
}
