
public class Main {
	private final static String P_NAME = "Your Name";
	private final static String P_STUDENTID = "Your ID";
	private final static String P_EMAILID = "Your EmailS";	
	
	/**
	 * This is the method to print personal details.
	 */
	private static void printPersonalDetails() {		
		System.out.println("Author: " + P_NAME + "\n" 
		+ "Student ID: " + P_STUDENTID + "\n" 
				+ "Email ID: " + P_EMAILID + "\n" 
		+ "This is my own work as defined by the University's Academic Misconduct Policy.");
	}
	
	
	public static void main(String[] args) {
		printPersonalDetails();
		System.out.println();
		BitOperations bOperations = new BitOperations();
		bOperations.processTruthTable();
		System.out.println();
		BinaryCalculator bCalculator = new BinaryCalculator(bOperations);
		bCalculator.processCalculation();	
	}
}
