
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

public class BugRun {
  
  public static void main(String[] args) { 
	  
		// Setup EZ graphics system.
		EZ.initialize(500, 500);  // PIXEL picture element 
		EZ.setBackgroundColor(new Color(100, 150, 100)); // set the background color
		Color c = new Color (100, 100, 221);
		int fontsize = 50;
		EZImage road = EZ.addImage("road.png", 250, 250); // add a road 
		EZImage hecarim = EZ.addImage("hecarim.png", 60, 150); // add hecarim
		hecarim.scaleTo(0.4); // scale hecarim down to 0.4
		EZ.refreshScreen(); // refreshes the screen so the original hecarim does not show up
		EZImage rammus = EZ.addImage("rammus.png", 60, 220); // add rammus
		rammus.scaleTo(0.2); // scale rammus down to 0.2
		EZImage zilean = EZ.addImage("zilean.png", 60, 300); // add zilean
		
		// Start of the race, announces when to go
		EZText text = EZ.addText(100, 30, "3", c, fontsize); 
		EZ.pause(150);
		EZText text1 = EZ.addText(200, 30, "2", c, fontsize); 
		EZ.pause(1500);
		EZText text2 = EZ.addText(300, 30, "1", c, fontsize); 
		EZ.pause(1500);
		EZText text3= EZ.addText(400, 30, "GO", c, fontsize); 
		EZ.pause(1500);
		EZText winnerText = EZ.addText(250, 400, "Current Leader: " , c, fontsize/2);
		
		int hecarimPosX = 60; // set hecarimPosX at 60
		int hecarimPosY = 150; // set hecarimPosY at 150
		
		int zileanPosX = 60; // set zileanPosX at 60
		int zileanPosY = 300; // set zileanPosY at 300
		
		int rammusPosX = 60; // set rammusPosX at 60
		int rammusPosY = 220; // set hecarimPosY at 60
		
		// keep tracks of who finished
		boolean hecarimFinished = false;
		boolean zileanFinished = false;
		boolean rammusFinished = false;
		boolean winner = false;
		
		// keep track of last key that was pressed
		char lastKey = '0'; // set last_value at 0
		
		EZSound hecarimSound = EZ.addSound("hsound.wav");
		EZSound zileanSound = EZ.addSound("zsound.wav");
		EZSound rammusSound = EZ.addSound("rsound.wav");
		EZSound victorySound = EZ.addSound("victorysound.wav");
		
		// store who is the current leader
		String currentLeader = "";
		// margin for the leader of the race
		int oneSixteenth = 31;
		// x poistion of the finish line
		int finishLineX = 470;
		// how fast Rammus is moving on each key press
		int humanSpeed = 10;
	
		Random randomGenerator; 
		Random computerRunnerRandomOffset = new Random(); 
		int hecarimRandomSpeed = computerRunnerRandomOffset.nextInt(2) + 1;
		int zileanRandomSpeed = computerRunnerRandomOffset.nextInt(2) + 1;
		
	
		while (true) {
			// move players forward
			if (!hecarimFinished) {
				hecarim.translateTo(hecarimPosX, hecarimPosY); 
			}
			if (!zileanFinished) {
				zilean.translateTo(zileanPosX, zileanPosY); 
			}
			if (!rammusFinished) {
				rammus.translateTo(rammusPosX, rammusPosY); 
			}
			// increment computer player position with randomness
			hecarimPosX += computerRunnerRandomOffset.nextInt(1) + hecarimRandomSpeed;
			zileanPosX  += computerRunnerRandomOffset.nextInt(1) + zileanRandomSpeed; 
			
			// check whose ahead by 1/16th
			if (hecarimPosX > (zileanPosX + oneSixteenth) 
					&& hecarimPosX > (rammusPosX + oneSixteenth) 
					&& currentLeader != "hecarim") {
				currentLeader = "hecarim";
				winnerText.setMsg("Current Leader: Hecarim");
				hecarimSound.play();
				System.out.println(currentLeader);
			}	
			else if (zileanPosX > (hecarimPosX + oneSixteenth) 
					&& zileanPosX > (rammusPosX + oneSixteenth) 
					&& currentLeader != "zilean") {
				currentLeader = "zilean";
				winnerText.setMsg("Current Leader: Zilean");
				zileanSound.play();
				System.out.println(currentLeader);
			}
			else if (rammusPosX > (zileanPosX + oneSixteenth) 
					&& rammusPosX > (hecarimPosX + oneSixteenth) 
					&& currentLeader != "rammus") {
				currentLeader = "rammus";
				winnerText.setMsg("Current Leader: Rammus");
				rammusSound.play();
				System.out.println(currentLeader);
			}
			// check to see if the key was pressed
			if (EZInteraction.wasKeyPressed('a') && lastKey != 'a') { 
				rammusPosX += humanSpeed;
				lastKey = 'a';
			}
			else if (EZInteraction.wasKeyPressed('s') && lastKey != 's') { 
				rammusPosX += humanSpeed;
				lastKey = 's';			
			}
			// check if racers has finished, and whether that racer won
			if (!hecarimFinished && hecarimPosX > finishLineX) {
				if (!winner) {
					victorySound.play();
					winnerText.setMsg("Winner: Hecarim");
				}
				hecarimFinished = true;
				winner = true;
			}
			
			if (!zileanFinished && zileanPosX > finishLineX) {
				if (!winner) {
				victorySound.play();	
					winnerText.setMsg("Winner: Ziliean");
				}
				zileanFinished = true;
				winner = true;
			}
			
			if (!rammusFinished && rammusPosX > finishLineX) {
				if (!winner) {
					victorySound.play();
					winnerText.setMsg("Winner: Rammus");
				}
				rammusFinished = true;
				winner = true;
			}
			// check if the race is over
			if (hecarimFinished && zileanFinished && rammusFinished) {
				EZ.refreshScreen(); // refreshes graphics
				break;
			}
			
			EZ.refreshScreen(); // refreshes graphics
		}
	}

} //end class
