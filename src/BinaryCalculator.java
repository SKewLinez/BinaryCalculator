import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Collections;

/**
 * The BinaryCalculator program implements an application to calculate addition and subtraction of arbitrary length binary numbers
 * based on the user input of the type of operation (+ or -), the program then asks for two binary numbers with arbitrary number of bits as strings. 
 * Each input string is converted into an array of numbers with each element representing a bit (0 or 1), 
 * then the program calculates the sum or difference, depending on the chosen operation, and give an output of the results in the format specified.
 * 
 * @author Yuxin Xiang
 * @version 1.0
 * @since 13/09/2020
 */

public class BinaryCalculator {
	private BitOperations bitOperations;


	/**
	 * The constructor of the BinaryCalculator class with a BitOperations instance 
	 * so the calculator can call its inner functions defined in another class.
	 * @param bo the BitOperations instance
	 */
	public BinaryCalculator(BitOperations bo) {
		this.bitOperations = bo;
	}


	/**
	 * This is the method to run the entire calculation logic for addition and subtraction of arbitrary length binary numbers 
	 * based on the operation selected.
	 */
	public void processCalculation() {
		boolean startProcessing = true;    // to mark the start and termination of the program

		while(startProcessing) {
			boolean opIsValid = false;     // to initialise the operation 
			boolean inputBinaryX = false, inputBinaryY = false;   // to initialise the input binary numbers X and Y

			while (!opIsValid) {   // If the operation entered is not valid, continue to ask.
				System.out.print("Choose operation [+, -, q]: ");
				Scanner scanner = new Scanner(System.in);
				String inputOp = scanner.nextLine();

				if (validateOperation(inputOp).equals("invalid")) {   // when the user enters an invalid operation
					System.out.println("Invalid operation.");
					System.out.println();
				}
				else {       // when the user enters a valid operation
					opIsValid = true;    // change the operation state

					if (!inputOp.equals("q")) {     // If the operation is not "q", continue to ask for inputs.

						while (!inputBinaryX) {     // If the input X is not a binary number, continue to ask.
							System.out.print("X: ");
							String inputStrX = scanner.nextLine();						
							if (validateBinary(inputStrX) == -1) {  // when the user enters a non-binary X
								System.out.println("Not a binary number!");
							}
							else {     // when the user enters a binary X, then the program can proceed to ask for Y
								inputBinaryX = true;							
								while (!inputBinaryY) {   // If the input Y is not a binary number, continue to ask.
									System.out.print("Y: ");
									String inputStrY = scanner.nextLine();
									if (validateBinary(inputStrY) == -1) {   // when the user enters a non-binary Y
										System.out.println("Not a binary number!");
									}
									else {   // when the user enters a binary Y
										inputBinaryY = true;
										System.out.println();
										String conformedStrX = removeLeadingZeros(inputStrX);   // to get the X without leading zeros
										String conformedStrY = removeLeadingZeros(inputStrY);   // to get the Y without leading zeros
										displayBinaryCalculation(conformedStrX, conformedStrY, inputOp);  // do the addition/subtraction using validated binary numbers according to user command
										System.out.println("\n");
									}
								}
							}
						}
					}
					else {     // If the operation is "q", terminate the program.
						startProcessing = false;
						scanner.close();
					}
				}
			}
		}
	}

	
	/**
	 * This method is to validate the entered operation command.
	 * @param inputOp the input operation string 
	 * @return the operation string if the operation is valid. Otherwise, return "invalid"
	 */
	private static String validateOperation(String inputOp) {
		if (!inputOp.equals("+") && !inputOp.equals("-") && !inputOp.equals("q")) {
			return "invalid";
		}
		return inputOp;
	}

	
	/**
	 * This method is to validate if the input string is a binary number (i.e., it includes characters other than '0' or '1').
	 * @param inputStr the input string
	 * @return 0 if the string is a binary number. Otherwise, return -1
	 */
	private static int validateBinary(String inputStr) {		
		if (inputStr.matches("[01]+")) {   // to verify if the input string only contains one or more 0/1 using regular expression 
			return 0;
		}
		return -1;
	}

	
	/**
	 * This method is convert a string into an array under the assumption that the string to convert represents a binary number 
	 * (i.e., it only contains characters 0 or 1)
	 * @param strToConvert the string that represents a. binary number
	 * @return the array after the conversion
	 */
	private static int[] convertStringtoIntArray(String strToConvert) {
		final int[] intArray = strToConvert.chars()
				.map(x -> x - '0')  // This can map corresponding numbers but in this case we only need 0 or 1.
				.toArray();
		return intArray;
	}

	
	/**
	 * This method is to remove the leading zeros from a string.
	 * @param oriStr
	 * @return
	 */
	private static String removeLeadingZeros(String oriStr) {
		oriStr = oriStr.replaceAll("^0+(?=.)", "");   // to replace the leading zeros with empty strings using regular expression
		return oriStr;
	}

	
	/**
	 * This method is to display the calculation of addition or subtraction and its result.
	 * @param str1 the input string X
	 * @param str2 the input string Y
	 * @param op the operation chosen (i.e., + or -)
	 */
	private void displayBinaryCalculation(String str1, String str2, String op) {		
		int maxLen = Math.max(str1.length(), str2.length());
		int colLen = maxLen + 2;   // the full column length

		String columnFormat1 = "%" + colLen + "s";       // right align the string based on the maxLen
		System.out.printf(columnFormat1, str1);
		System.out.print("\n" + op);
		String columnFormat2 = "%" + (colLen-1) + "s";   // -1 because it includes the operator
		System.out.printf(columnFormat2, str2);
		System.out.println();
		for (int i = 0; i < colLen; i++) {
			System.out.print("-");
		}
		System.out.println();

		int[] xDigits = convertStringtoIntArray(str1);   // the X array used for calculation
		int[] yDigits = convertStringtoIntArray(str2);   // the Y array used for calculation

		if (op.equals("+")) {   
			ArrayList<Integer>additionResult = binaryAddition(xDigits, yDigits);
			String additionResultStr = additionResult.stream().map(Object::toString)   // to convert an ArrayList to a String using a joining collector
					.collect(Collectors.joining(""));
			System.out.printf(columnFormat1, additionResultStr);
		}
		else if (op.equals("-")) {
			int comparisonResult = binaryComparison(xDigits, yDigits);
			String subtractionTempStr = "";
			String subtractionResultStr = "";			
			ArrayList<Integer>subtractionResult;
			if (comparisonResult == 0) {      // When the first number is no less than the second number, no need to swap the sequence
				subtractionResult = binarySubstraction(xDigits, yDigits);
				subtractionTempStr = subtractionResult.stream().map(Object::toString)
						.collect(Collectors.joining(""));
				subtractionResultStr = removeLeadingZeros(subtractionTempStr);   // to remove the redundant zeros in the front
			}
			else if (comparisonResult == 1) {  // When the first number is less than the second number, swap the sequence and add a negative sign in the front of the result
				subtractionResult = binarySubstraction(yDigits, xDigits);
				subtractionTempStr = subtractionResult.stream().map(Object::toString)
						.collect(Collectors.joining(""));
				subtractionResultStr = "-" + removeLeadingZeros(subtractionTempStr);
			}
			System.out.printf(columnFormat1, subtractionResultStr);
		}
	}


	/**
	 * This method is to perform the addition of two binary numbers by calculating the sum of each digit from the lowest digit.
	 * @param add1 the binary number to add in the array form
	 * @param add2 the binary number to add in the array form
	 * @return fullResult the result of addition in the ArrayList form
	 */
	private ArrayList<Integer> binaryAddition(int[]add1, int[]add2) {
		int len1 = add1.length;
		int len2 = add2.length;
		int shorterLen = Math.min(len1, len2);
		int bitToCarry = 0;
		int r = -1;      // to represent the value of a single digit of the result moving from the lowest to highest
		ArrayList<Integer> fullResult = new ArrayList<>();

		for (int i = 0; i < shorterLen; i++) {   // to traverse the arrays first within the shorter array length
			int j = len2-1 - i;
			r = bitOperations.addBit(add1[len1-1-i], add2[j], bitToCarry);		
			fullResult.add(r);
			bitToCarry = bitOperations.carryBit(add1[len1-1-i], add2[j], bitToCarry);

			if (len1-1-i == 0 || j == 0) {     // when the index is moved at 0 of at least one array
				if (len1-1-i == 0 && j == 0) {}      // both arrays are finished, do nothing
				else if (len1-1-i == 0 && j != 0) {  // add1 is shorter, continue to move the index on add2
					for (int k = j-1; k >= 0; k--) {
						r = bitOperations.addBit(0, add2[k], bitToCarry);
						fullResult.add(r);
						bitToCarry = bitOperations.carryBit(0, add2[k], bitToCarry);
					}
				}
				else if (len1-1-i != 0 && j == 0) {  // add2 is shorter, continue to move the index on add1
					for (int k = len1-1-i-1; k >= 0; k--) {
						r = bitOperations.addBit(add1[k], 0, bitToCarry);
						fullResult.add(r);
						bitToCarry = bitOperations.carryBit(add1[k], 0, bitToCarry);					
					}
				}
				if (bitToCarry == 1) {    // the final result needs carry one 1 and add in the very front
					r = bitToCarry;
					fullResult.add(r);
				}
			}
		}
		Collections.reverse(fullResult);  // reverse the sequence of the digits in the fullResult so it is highest --> lowest
		return fullResult;		
	}


	/**
	 * This method is to perform the subtraction of two binary numbers by calculating the difference of each digit from the lowest digit.
	 * Note: this only calculate the non-negative subtraction result (i.e., the default sequence is sub1 - sub2) regarding sub1 is no less than sub2,
	 * in all cases, you should compare sub1 and sub2 first before deciding whether to swap the sequence.
	 * @param sub1 the binary number to be subtracted from in the array form
	 * @param sub2 the binary number to subtract in the array form
	 * @return fullResult the result of subtraction (i.e., sub1 - sub2) in the ArrayList form, might have leading zeros
	 */
	private ArrayList<Integer> binarySubstraction(int[]sub1, int[]sub2) {
		int len1 = sub1.length;
		int len2 = sub2.length;
		int shorterLen = Math.min(len1, len2);
		int bitToBorrow = 0;
		int r = -1;
		ArrayList<Integer> fullResult = new ArrayList<>();

		for (int i = 0; i < shorterLen; i++) { // to traverse the arrays first within the shorter array length
			int j = len2-1-i;
			r = bitOperations.subBit(sub1[len1-1-i], sub2[j], bitToBorrow);
			bitToBorrow = bitOperations.borrowBit(sub1[len1-1-i], sub2[j], bitToBorrow);
			fullResult.add(r);

			if (j == 0) {    // the sub2 is finished, meanwhile the sub1 might be or might not be finished
				for (int k = len1-1-i-1; k >= 0; k--) {    // sub1 is bigger, continue to move the index on sub1
					r = bitOperations.subBit(sub1[k], 0, bitToBorrow);
					bitToBorrow = bitOperations.borrowBit(sub1[k], 0, bitToBorrow);
					fullResult.add(r);
				}
			}
		}
		Collections.reverse(fullResult);	// reverse the sequence of the digits in the fullResult so it is highest --> lowest		
		return fullResult;
	}

	
	/**
	 * This method is to compare two binary numbers to decide whether to swap the sequence or not.
	 * Useful when conducting the subtraction defined above.
	 * @param bin1 the binary number to compare in the array form
	 * @param bin2 the binary number to be compare in the array form
	 * @return L 1 if bin1 is smaller than bin2 (needs to consider the swap). Otherwise, return 0 (no need to consider the swap)
	 */
	private int binaryComparison(int[]bin1, int[]bin2) {
		int len1 = bin1.length;
		int len2 = bin2.length;
		int L = -1;

		if (len1 == len2) {   // bin1 and bin2 have an equivalent length, might be or might not be equal
			int len = len1 = len2;
			int l = 0;
			for (int i = 0; i < len; i++) {
				l = bitOperations.lessThan(bin1[len-1-i], bin2[len-1-i], l);				
			}
			L = (l == 1)? 1:0; // L=1 if bin1 is smaller, L=0 if bin1 is equal or bigger
		}
		else {                // bin1 and bin2 have different lengths, must be unequal
			L = (len1 < len2)? 1:0; // L=1 if bin1 is smaller, L=0 if bin1 is bigger
		}
		return L;
	}
}
