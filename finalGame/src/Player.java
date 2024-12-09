import java.awt.*;
import javax.swing.*;

public class Player {
	//Features of player
	private String name;
	private Room currentRoom;
	private Item[] inventory;
	private int healthCount;
	private int maxItemCount=3;
	//Features of game
	private Room plate, napkins, baguette,juice, apple;
	private Item knife,straw, crumb;
	private String introText;
	
	Player(String n){
		initializeGame();//Initialize rooms and games 
		name=n;
		healthCount=10;//Initial health
		inventory= new Item[maxItemCount];
		for(int i=0;i<inventory.length;i++) {
			inventory[i]= new Item();
		}
	}
	void initializeGame() {
		//**********************************************************************************************
		//Create tools
		//**********************************************************************************************
		knife =new Item("Knife","It is very sharp. Perfect for cutting a sandwich... or fruit maybe.");
		straw= new Item("Straw","Kind of firm, had a bit of Juice stuck inside");
		crumb= new Item("Crumb","It looks unbelievably tasty. Its hard for you believe it came from the stale Bagguete");
		
		
		
		//**********************************************************************************************
		//Create rooms
		//**********************************************************************************************
		plate=new Room("Plate","A vast round disk.It reminds you of a UFO","mapArt\\plateLocal.png");
		napkins=new Room("Napkins","A pillowy stack of off white rectangles."
				+ " With a single silvery shard peaking out of a fold","mapArt\\napkinLocal.png",knife);
		baguette= new Room("Baguette","Brown slab with crumbs scattered about.","mapArt\\baguetteLocal.png",crumb);
		juice= new Room("Juice Spill","A orange blob with a multi colored pole on its side","mapArt\\juiceLocal.png",straw);
		apple= new Room("Apple","There is a tiny hole that you cannot quite reach into","mapArt\\appleLocal.png");
		//Add description for empty room
		napkins.addEmptyDescription("A white pillowy stack.");
		baguette.addEmptyDescription("Brown slab with only crusted unedible crumbs left.");
		juice.addEmptyDescription("A orange sticky splotch");
		apple.addEmptyDescription("You are in a tunnel inside of the apple.");
		apple.allowMiniGame();//Let apple play mini-game
		apple.changeRoomLock();///Lock apple
		//Create room connections
		plate.addRoomDirectionPair("north",juice);
		plate.addRoomDirectionPair("east", napkins);
		napkins.addRoomDirectionPair("north", baguette);
		napkins.addRoomDirectionPair("west", juice);
		baguette.addRoomDirectionPair("south", juice);
		baguette.addRoomDirectionPair("north", apple);
		juice.addRoomDirectionPair("south", plate);
		juice.addRoomDirectionPair("east", napkins);
		apple.addRoomDirectionPair("south", baguette);
		//Create description for going in direction
		plate.addEntranceDirectionPair("north", "You carefully climb down into a puddle.");//for plates
		plate.addEntranceDirectionPair("east", "You hop onto a white pillow.");
		plate.addEntranceDirectionPair("west", "You look onto the table. There is nothing as far as you can see. You stay put.");
		plate.addEntranceDirectionPair("south", "You approach the edge of the plate,"
				+ " if you go any farther you fear you might fall to your doom.");
		napkins.addEntranceDirectionPair("north", "You carefully climb up the rugged surface of the brown mound.");//for napkins
		napkins.addEntranceDirectionPair("east", "You fear you might fall to your doom, you stay put.");
		napkins.addEntranceDirectionPair("west", "As you approach the splotch in the distance the napkins seem to get squisher. "
				+ "They are wet.");
		napkins.addEntranceDirectionPair("south", "You fear you might fall to your doom, you stay put.");
		baguette.addEntranceDirectionPair("north", "You climb up to the apple.");//for baguettes
		baguette.addEntranceDirectionPair("east", "You fear you might fall to your doom, you stay put.");
		baguette.addEntranceDirectionPair("west", "There are ants eating some crumbs that have fallen. "
				+ "They look viscous. You stay put.");
		baguette.addEntranceDirectionPair("south", "You hop down with a grand splash. \"Cannonball!\"");
		juice.addEntranceDirectionPair("north", "The side of the bread is too slippery to climb.");//for juice
		juice.addEntranceDirectionPair("east", "You swim through the puddle until you reach the edge of the napkin.");
		juice.addEntranceDirectionPair("west", "There are ants eagerly slurping at some of the juice."
				+ " You fear what they might do if your limbs were in their way. You stay put.");
		juice.addEntranceDirectionPair("south", "You climb back to the cold familiar disk now drenching");
		apple.addEntranceDirectionPair("north", "You dont want to risk not being able to come back. You stay put.");//for apples
		apple.addEntranceDirectionPair("east", "You fear you might fall to your doom, you stay put.");
		apple.addEntranceDirectionPair("west", "You see ants marching away. You decide not to face them.");
		apple.addEntranceDirectionPair("south", "You climb back down from where you came.");
	
		//**********************************************************************************************
		//Create tool use
		//**********************************************************************************************
		napkins.getItem().addUsableCommandPair("cut","Apple");//item is knife
		baguette.getItem().addUsableCommandPair("eat","healthCount+");//item is crumb. Healthcount+ item increases health
		juice.getItem().addUsableCommandPair("drink", "healthCount+");
		//Add description to use
		napkins.getItem().addUsableDescriptionPair("cut", "You carefully carve away at the apple "
				+ "making it wide enough to crawl through.");
		baguette.getItem().addUsableDescriptionPair("eat","You gobble down the fluffy lump. Your health has increased!");
		
		//*******************************************************
		//Set start conditions for game
		//*******************************************************
		//TODO Add intro text in mainPlayer class

		currentRoom= plate;
		
	}

	//Features of player
	int getHealth() {
		return healthCount;
	}
	Room getCurrentRoom() {
		return currentRoom;
	}
	String[] getInventoryAsString() {
		String[] inventoryNames= new String[inventory.length];
		try {
		for(int i=0;i<inventory.length;i++) {
			inventoryNames[i]=inventory[i].getName();
		}
		}
		catch(NullPointerException e) {
			System.out.println("Player has no items");
		}
		return inventoryNames;
	}
	String getName() {
		return name;
	}
	
	//Features of game
	//Features of game
	String[] getAllPossibleCommand(int invenIndex) {
		int i=currentRoom.getPossibleCommands().length;
		if(invenIndex!=-1) {//Only add if item is selected
			i+=inventory[invenIndex].getPossibleCommands().length;
		}
		String possibleCommands[]= new String[i];
		i--;
		for(String s:currentRoom.getPossibleCommands()) {//Add all commands for room
			possibleCommands[i]=s;
			i--;
		}
		if(invenIndex!=-1) {
			for(int j=0;j<inventory[invenIndex].getPossibleCommands().length;j++) {
				String s= inventory[invenIndex].getPossibleCommands()[0];
				possibleCommands[j]=s;
				
			}
		}
		return possibleCommands;
	}
	String getIntroText() {//TODO part of making it so no text needs to be directly enterend into gui 
		return introText;
	}
	String checkCommand(String input,int invenIndex) {
		String resultingText=input+" is not something "+ name +" can do right now.";
		if(currentRoom.checkIfValidDirection(input)) {//Check if direction is possible
			resultingText=name+" enters:"+currentRoom.getRoomEnroute(input).getName().toUpperCase()+"."
					+currentRoom.getEnrouteDescription(input);//Show room in and description for journey
			currentRoom=currentRoom.getRoomEnroute(input);//Update room
			healthCount--;
		}
		else if(currentRoom.checkIfAnyDirection(input)) {//If valid direction but able to go into
			resultingText=currentRoom.getEnrouteDescription(input);//Show description of what is in direction
			healthCount--;
		}
		else if(!currentRoom.getItem().getName().isBlank()&&input.equalsIgnoreCase("grab")) {//If user wants to grab item from room
			for(int i=0; i<inventory.length;i++) {
				if(inventory[i].getName().isBlank()) {//Add item to inventory
					inventory[i]=currentRoom.getItem();
					resultingText=name+" has obtained "+currentRoom.getItem().getName().toUpperCase()
							+"."+currentRoom.getItem().getDescription();
					currentRoom.updateToEmpty();
					healthCount--;
					break;
				}
			}		
		
		}
		else if(invenIndex!=-1) {//If there is a selected item
			if(inventory[invenIndex].checkIfUsable(input)) {//If valid input for item
				if(currentRoom.getName().equals(inventory[invenIndex].getItemInUsePair(input))) {//If room is what item can be used with
					
					if(!currentRoom.getUnlockedStatus()) {//Can only unlock rooms
						resultingText=name+" has unlocked "+currentRoom.getName().toUpperCase()+"!";
						currentRoom.updateToEmpty();//TODO change to "updateToUnlocked" for more advanced rooms
						inventory[invenIndex]=new Item("");//Use item
					}
					currentRoom.changeRoomLock();
				}
				else if(inventory[invenIndex].getItemInUsePair(input).equals("healthCount+")) {//If item is meant to increase health
					healthCount+=3;//Item increases health by 3
					if(healthCount>10) {//Do not allow health to exceed 10
						healthCount=10;
					}
					resultingText= inventory[invenIndex].getDescriptionInPair(input);
					inventory[invenIndex]=new Item("");//Use item
				}
			}
		}
		
		if(healthCount<0) {//Do not allow health to go negative
			healthCount=0;
		}
		return resultingText;
	}
}


