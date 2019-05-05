import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PlayerProfile {
	private String name;
	private int totalActions;
	private int totalTime;
	private int totalGames;
	
	//Constructor for new player.
	public PlayerProfile(String name) {
		this.name = name;
		this.totalActions = 0;
		this.totalTime = 0;
		this.totalGames = 0;
	}
	
	//Constructor for loaded player.
	public PlayerProfile(String name, int actions, int time, int games) {
		this.name = name;
		this.totalActions = actions;
		this.totalTime = time;
		this.totalGames = games;
	}
		
	public int getAverageAPM() {
		if(totalGames > 0) {
			return (int)((totalActions / totalGames) / (getAverageTime() / 60.0));
		} else {
			return 0;
		}
	}
	
	public int getAverageTime() {
		if(totalGames > 0) {
			return totalTime / totalGames;
		} else {
			return 0;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getTotalActions() {
		return totalActions;
	}
	
	public int getTotalGames() {
		return totalGames;
	}
	
	public int getTotalTime() {
		return totalTime;
	}
	
	//Loads profile data from a text file or creates a new one if it does not exist.
	public static PlayerProfile loadProfile(String name) {
		Scanner input;
		try {
			input = new Scanner(new File(name + ".txt"));
			PlayerProfile loadedProfile = new PlayerProfile(name, 
					Integer.parseInt(input.next()),
					Integer.parseInt(input.next()), 
					Integer.parseInt(input.next()));
			input.close();
			return loadedProfile;
		} catch (FileNotFoundException e) {
			return new PlayerProfile(name);
		}
	}
	
	//Saves profile data to a text file. Returns 1 if successful and -1 if not.
	public int saveProfile() {
		try {
			PrintWriter output;
			
			if (new File(name + ".txt").exists()) {
				File file = new File(name + ".txt");
				file.delete();
				file.createNewFile();
				output = new PrintWriter(name + ".txt");
			} else {
				File file = new File(name + ".txt");
				file.createNewFile();
				output = new PrintWriter(name + ".txt");
			}
			
			output.print(totalActions + " ");
			output.print(totalTime + " ");
			output.print(totalGames);
		
			output.close();
			return 1;
		} catch(IOException ioe) {
			return -1;
		}
	}

	public void setTotalActions(int actions) {
		totalActions = actions;
	}
	
	public void setTotalGames(int games) {
		totalGames = games;
	}
	
	public void setTotalTime(int time) {
		totalTime = time;
	}
	
	public String toString() {
		return getName() + " | Avg Time: " + getAverageTime() + " | Avg APM: " + getAverageAPM() + " | Games Played: " + getTotalGames();
	}
}
