import java.util.HashMap;
import java.awt.*;
import javax.swing.*;

public class Room {
	private HashMap<String,Room> connectedRooms;
	private HashMap<String,String> entranceText;
	private String name, description,emptyDescription, mapImagePath;
	private boolean unlocked=true,miniGameRoom=false;
	private Item itemInside;
	
	
	Room(String n, String d,String iP,Item i){//Initialize room
		name=n;
		description=d;
		mapImagePath=iP;	
		itemInside=i;
		connectedRooms=new HashMap<String,Room>();
		entranceText= new HashMap<String,String>();
	}
	Room(String n, String d,String iP){//Initialize room
		name=n;
		description=d;
		mapImagePath=iP;
		itemInside= new Item();
		connectedRooms=new HashMap<String,Room>();
		entranceText= new HashMap<String,String>();
	}
	void allowMiniGame() {
		miniGameRoom=true;
	}
	void addEmptyDescription(String d) {
		emptyDescription=d;
	}
	void addRoomDirectionPair(String dir, Room r) {//Add direction room pair
		connectedRooms.put(dir, r);
	}
	void addEntranceDirectionPair(String dir, String desc) {//Add description into how player gets into room
		entranceText.put(dir,desc);
	}
	Boolean checkIfValidDirection(String dir) {//Check if direction entered is valid
		if(connectedRooms.containsKey(dir.toLowerCase()))
			return true;
		else
			return false;
	}
	Boolean checkIfAnyDirection(String dir) {
		if(entranceText.containsKey(dir.toLowerCase()))
			return true;
		else
			return false;
	}
	Boolean checkIfMiniGame() {
		return miniGameRoom;
	}
	String getDescription() {//Return description
		return description;
	}
	String getImagePath() {//Return imagePath
		return mapImagePath;
	}
	String getName() {//Return name
		return name;
	}
	Item getItem() {//getItem in room
		return itemInside;
	}
	Room getRoomEnroute(String dir) {//Get room in direction of player
		return connectedRooms.get(dir);
	}
	String getEnrouteDescription(String dir) {//Get description of path into room
		return entranceText.get(dir);
	}
	void updateToEmpty() {//Should only be called for unlocked room or room with items
		description=emptyDescription;//Remove description of item from room
		itemInside= new Item();//Remove item from room
	}
	void updateItem(Item i) {//Should only be used for updating item
		itemInside =i;
	}
	void changeRoomLock() {//Change room to locked or unlocked
		if(unlocked)
			unlocked=false;
		else
			unlocked=true;
	}
	boolean getUnlockedStatus() {//Returns true when room is unlocked 
		return unlocked;
	}
	String[] getPossibleCommands(){
		int i=0;
		for(String s:entranceText.keySet()) {//Count how many options
			i++;
		}
		String[] possibleCommands= new String[i+1];
		for(String s:entranceText.keySet()) {//Add options to array
			possibleCommands[i]=s;
			i--;
		}
		return possibleCommands;
		
	}
}
