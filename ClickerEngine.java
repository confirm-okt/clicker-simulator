public class ClickerEngine {
	private int activeMod;
	private boolean t1Passive;
	private boolean t2Passive;
	private boolean t3Passive;
	private boolean t4Passive;
	private boolean t5Passive;
	private int points;
	private int time;
	private int actions;
	
	public ClickerEngine() {
		this.activeMod = 1;
		this.t1Passive = false;
		this.t2Passive = false;
		this.t3Passive = false;
		this.t4Passive = false;
		this.t5Passive = false;
		this.points = 0;
		this.time = 0;
		this.actions = 0;
	}
	
	public void addPoints() {
		points += activeMod;
		actions++;
	}
	
	public void buyUpgrade(int upgrade) {
		switch(upgrade) {
			case 0: if(activeMod < 100 && points >= (int)(Math.pow(1.2, activeMod))) {
				points -= (int)(Math.pow(1.2, activeMod));
				activeMod++;
				actions++;
			} break;
			case 1: if(!t1Passive && points >= 5) {
				points -= 5;
				t1Passive = true;
				actions++;
			} break;
			case 2: if(!t2Passive && points >= 25) {
				points -= 25;
				t2Passive = true;
				actions++;
			} break;
			case 3: if(!t3Passive && points >= 125) {
				points -= 125;
				t3Passive = true;
				actions++;
			} break;
			case 4: if(!t4Passive && points >= 625) {
				points -= 625;
				t4Passive = true;
				actions++;
			} break;
			case 5: if(!t5Passive && points >= 3125) {
				points -= 3125;
				t5Passive = true;
				actions++;
			} break;
			default: System.out.println("Error: Incorrect usage of method buyUpgrade(int)");
				break;
		}
	}
	
	public boolean hasPassive(int tier) {
		if(tier == 1) {
			return t1Passive;
		} else if(tier == 2) {
			return t2Passive;
		} else if(tier == 3) {
			return t3Passive;
		} else if(tier == 4) {
			return t4Passive;
		} else if(tier == 5) {
			return t5Passive;
		} else {
			System.out.println("Error: Incorrect usage of method hasPassive()");
			return false;
		}
	}
	
	public void gamble() {
		if((Math.random() * 100.0) >= 50.0) {
			points *= 2;
			actions++;
		} else {
			points = 0;
			actions++;
		}
	}
	
	public int getActions() {
		return actions;
	}
	
	public int getActiveMod() {
		return activeMod;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getTime() {
		return time;
	}
	
	public void step() {
		if(t1Passive) points += 1;
		if(t2Passive) points += 2;
		if(t3Passive) points += 7;
		if(t4Passive) points += 32;
		if(t5Passive) points += 157;
		time++;
	}
}
