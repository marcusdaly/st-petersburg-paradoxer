/**
 * A program to empirically test the results of playing the St. Petersburg Paradox with large numbers
 * of plays. Inspired by ECON 309 class at Case Western Reserve University.
 * 
 * @author Marcus Daly
 *
 */
public class Paradox {
	/** The money in the player's account */
	private double balance;
	/** The cost to play one game */
	private double cost;
	/** The largest winnings from a play of the game */
	private int largestGain;
	/** The average winnings from a play of the game */
	private double averageGain;
	/** The average Utility from a play of the game */
	private double averageUtility;
	/** The total plays of the game */
	private int plays;
	
	/**
	 * Creates a new space to play the game
	 * @param balance The starting balance of the player
	 * @param cost The cost to play the game
	 */
	public Paradox(double balance, double cost) {
		this.balance = balance;
		this.cost = cost;
	}
	
	/**
	 * Run a gamble with the player's current account and the game's current cost
	 * @return The Balance after playing this game
	 */
	public double gamble() {
		//Make sure player has enough money to play:
		if (balance < cost) {
			return 0;
		}
		balance -= cost;
		
		int i = 1;
		while (Math.random() >= 0.5) {
			i++;
		}
		int gain  = (int)Math.pow(2, i);
		addGain(gain);
		return (balance += gain);
	}
	
	/**
	 * Updates statistics for gains
	 * @param gain The gain from the gamble just played
	 */
	private void addGain(int gain) {
		if (gain > largestGain) {
			largestGain = gain;
		}
		averageGain = (averageGain * plays + gain) / ++plays; //avg = (oldavg*oldplays + gain)/newplays
		averageUtility = (averageUtility * (plays - 1) + utility(gain)) / plays;
	}
	
	/**
	 * A Utility Function U(x)
	 * @param x Dollar value finding utility of
	 * @return The utility given the dollar value
	 */
	private double utility(double x) {
//		return Math.sqrt(x);
		return Math.log(x);
//		return 1 - Math.exp(-x);
	}
	
	/**
	 * The Main method to run the program (currently must edit in source code)
	 * @param args (Number plays, Cost, Starting Balance)
	 */
	public static void main(String[] args) {
		
		if (args.length != 3) {
			System.out.println("Please enter 3 Parameters: Number plays, Cost, and Starting balance.");
			return;
		}
		int plays;
		double cost;
		double startingBalance;
		
		//Plays:
		try {
			plays = Integer.parseInt(args[0]);
		}
		catch (NumberFormatException e){
			System.out.println("Please enter an integer value for Number of plays!");
			return;
		}
		
		//Cost:
		try {
			cost = Double.parseDouble(args[1]);
		}
		catch (NumberFormatException e){
			System.out.println("Please enter a double value for Cost!");
			return;
		}
		
		//Balance:
		try {
			startingBalance = Double.parseDouble(args[2]);
		}
		catch (NumberFormatException e){
			System.out.println("Please enter an double value for Starting Balance");
			return;
		}
		
		if (plays < 0 || cost < 0 || startingBalance < 0) {
			System.out.println("Please enter non-negative values for all parameters!");
			return;
		}
		
		Paradox p = new Paradox(startingBalance, cost);
		System.out.println("Updated Balances: ");
		for (int i = 0; i < plays; i++) {
			System.out.println(p.gamble());
		}
		System.out.println("Largest Gain: " + p.largestGain);
		System.out.println("Average Gain: " + p.averageGain);
		System.out.println("Average Utility: " + p.averageUtility);
	}
}