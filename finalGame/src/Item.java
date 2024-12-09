import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class Item {
	private String name,description;
	private HashMap<String,Item> usableWith;
	private HashMap<String,String> usabilityDescription;
	
	Item(String n, String d){
		name=n;
		description=d;
		usableWith= new HashMap<String,Item>();
		usabilityDescription= new HashMap<String,String>();
	}
	Item(String n){//For an item that is also a room
		name=n;
		description="";
		usableWith= new HashMap<String,Item>();
		usabilityDescription= new HashMap<String,String>();
	}
	Item(){
		name="";
		description="";
		usableWith= new HashMap<String,Item>();
		usabilityDescription= new HashMap<String,String>();
	}
	String getDescription() {//Return description
		return description;
	}
	String getName() {//Return name
		return name;
	}
	public void addUsableCommandPair(String command, String name) {
		Item i= new Item(name);
		usableWith.put(command, i);
	}
	public void addUsableDescriptionPair(String command, String s) {
		usabilityDescription.put(command, s);
	}
	public String getItemInUsePair(String command) {
		return usableWith.get(command).getName();
	}
	public String getDescriptionInPair(String command) {
		return usabilityDescription.get(command);
	}
	public Boolean checkIfUsable(String command) {
		if(usableWith.containsKey(command.toLowerCase())) {
			return true;
		}
		return false;
	}
	public Boolean checkIfCommand(String command) {
		if(usabilityDescription.containsKey(command.toLowerCase())) {
			return true;
		}
		return false;
	}
	String[] getPossibleCommands(){
//		int i=0;
//		for(String s:usableWith.keySet()) {//Count how many options
//			i++;
//		}
//		String[] possibleCommands= new String[i+1];//Create array of correct size
//		for(String s:usableWith.keySet()) {//Add options to array
//			possibleCommands[i]=s;
//			i--;
//		}
		String[] possibleCommands= new String[1];
		for(String s:usableWith.keySet()) {
			possibleCommands[0]=s;
			System.out.print(s);
		}
	
		return possibleCommands;
		
	}
	
}
