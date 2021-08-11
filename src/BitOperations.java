/**
 * The BitOperations implements bit operation functions that performs logical operations on binary digits, 
 * considering the value 1 as True and 0 as False. The truth tables of the functions are shown on the screen.
 * 
 * @author Yuxin Xiang
 * @version 1.0
 * @since 13/09/2020
 *
 */
public class BitOperations {

	/**
	 * This method takes one binary digit input x and returns the result of performing NOT operation on the input value.
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @return the result of executing NOT operation
	 */
	private static int notOP(int x) {
		return (x == 0)? 1 : 0;
	}

	
	/**
	 * This method takes two input arguments x and y as binary digits and returns a binary digit which is a result of performing AND operation with the input values.
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @param y the input binary digit (i.e., either 0 or 1)
	 * @return the result of executing AND operation
	 */
	private static int andOP(int x, int y) {
		return (x == 1 && y ==1)? 1 : 0;
	}

	
	/**
	 * This method takes two input arguments x and y as binary digits and returns a binary digit which is a result of performing OR operation with the input values.
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @param y the input binary digit (i.e., either 0 or 1)
	 * @return the result of executing OR operation
	 */
	private static int orOP(int x, int y) {
		return (x == 0 && y == 0)? 0 : 1;	
	}

	
	/**
	 * This method takes two input arguments x and y as binary digits and returns a binary digit which is a result of performing Exclusive OR operation with the input values. 
	 * This function is implemented by calling the functions described above using the conjunctive normal form.
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @param y the input binary digit (i.e., either 0 or 1)
	 * @return the result of executing Exclusive OR operation
	 */
	private static int xorOP(int x, int y) {
		return andOP(orOP(x, y), orOP(notOP(x), notOP(y)));
	}


	/**
	 * This method performs a single-bit addition operation by taking two input arguments x and y as binary digits, 
	 * and returns a binary digit which is a result of adding the two input arguments x and y  
	 * while considering c which is carried over from the result of addition in the lower digit (i.e. output of CARRY_BIT() function at the lower digit).
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @param y the input binary digit (i.e., either 0 or 1)
	 * @param c the carry bit which is carried over from the result of addition in the lower digit
	 * @return the result of adding the two input arguments x and y (i.e. x + y) 
	 */
	public int addBit(int x, int y, int c) {
		int r1 = xorOP(x, y);
		return xorOP(r1, c);
	}

	
	/**
	 * This method returns a value to be carried over to the higher digit 
	 * when calculating single-bit addition of x and y while considering 
	 * c which is carried over from the lower digit (i.e. output of CARRY_BIT() function at the lower digit).
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @param y the input binary digit (i.e., either 0 or 1)
	 * @param c the carry bit which is carried over from the lower digit
	 * @return the result to be carried over to the higher digit 
	 */
	public int carryBit(int x, int y, int c) {
		int r1 = andOP(x, y);
		int r2 = xorOP(x, y);
		int r3 = andOP(c, r2);
		return orOP(r1, r3);
	}

	
	/**
	 * This method performs single-bit subtraction operation by taking two input arguments x and y as binary digits, 
	 * and returns a binary digit which is a result of subtracting y from x 
	 * considering b which indicates it has lent to the lower digit subtraction (i.e. output of BORROW_BIT()function at the lower digit).
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @param y the input binary digit (i.e., either 0 or 1)
	 * @param b the borrow bit which has been lent to the lower digit subtraction
	 * @return the result of subtracting y from x (i.e. x �C y) 
	 */
	public int subBit(int x, int y, int b) {
		int r1 = xorOP(x, y);
		return xorOP(r1, b);
	}

	
	/**
	 * This method returns whether if it had to borrow from the higher digit when calculating the single-bit subtraction of x and y
	 * considering b which indicates it has lent to the lower digit (i.e. output of BORROW_BIT() function at the lower digit).
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @param y the input binary digit (i.e., either 0 or 1)
	 * @param b the borrow bit which has been lent to the lower digit
	 * @return the result of whether if it had to borrow from the higher digit
	 */
	public int borrowBit(int x, int y, int b) {
		int r1 = andOP(notOP(x), b);
		int r2 = andOP(notOP(x), y);
		int r3 = andOP(y, b);
		return orOP(orOP(r1, r2), r3);		
	}

	
	/**
	 * This method performs single-bit comparison by taking two input arguments x and y as binary digits, 
	 * and returns a binary digit which indicates whether if x is less than y 
	 * considering l which was the result of comparing the lower digit (i.e. output of LESS_THAN() function at the lower digit).
	 * @param x the input binary digit (i.e., either 0 or 1)
	 * @param y the input binary digit (i.e., either 0 or 1)
	 * @param l the result of comparing the lower digit
	 * @return the result of whether if x is less than y 
	 */
	public int lessThan(int x, int y, int l) {
		int r1 = andOP(y, l);
		int r2 = andOP(notOP(x), y);
		int r3 = andOP(notOP(x), l);
		return  orOP(orOP(r1, r2), r3) ;
	}

	
	/**
	 * This method generates a dynamic size truth table consider the input size n and the specified type.
	 * @param n the size of the truth table which refers to the input section
	 * @param type the type of the truth table to be generated (i.e., addition, subtraction, and less_than)
	 */
	private void displayTruthTable(int n, String type) {
		int rows = (int) Math.pow(2,n);

		for (int i = 0; i < rows; i++) {
			String rowStr = "";
			for (int j = n-1; j >= 0; j--) { 
				rowStr += (i/(int) Math.pow(2, j))%2 + " ";     // to get 0's and 1's from the loop indices
				System.out.print((i/(int) Math.pow(2, j))%2 + " ");
			}
			String[] bitStr = rowStr.split(" "); // to store the binary digits that are ready be processed thought relevant bit operations
			
			switch(type) {
			case "Addition":				
				int c = Integer.parseInt(bitStr[0]);
				int xAdd = Integer.parseInt(bitStr[1]);
				int yAdd = Integer.parseInt(bitStr[2]);
				int ZAdd = addBit(xAdd, yAdd, c);
				int C = carryBit(xAdd, yAdd, c);
				System.out.println(ZAdd+ " " + C);
				break;
				
			case "Subtraction":
				int b = Integer.parseInt(bitStr[0]);
				int xSub = Integer.parseInt(bitStr[1]);
				int ySub = Integer.parseInt(bitStr[2]);
				int ZSub = subBit(xSub, ySub, b);
				int B = borrowBit(xSub, ySub, b);
				System.out.println(ZSub+ " " + B);
				break;
				
			case "LessThan":
				int l = Integer.parseInt(bitStr[0]);
				int xLes = Integer.parseInt(bitStr[1]);
				int yLes = Integer.parseInt(bitStr[2]);				
				int L = lessThan(xLes, yLes, l);
				System.out.println(L);
				break;
			}
		}
	}

	
	/**
	 * This method is to print the truth tables of the ADDITION(), SUBTACTION() and LESS_THAN() including the header.
	 */
	public void processTruthTable() {
		System.out.println("ADDITION" + "\n" + "c x y Z C" + "\n" + "---------");
		displayTruthTable(3, "Addition"); 

		System.out.println();
		System.out.println("SUBTRACTION" + "\n" + "b x y Z B" + "\n" + "---------");
		displayTruthTable(3, "Subtraction");
		
		System.out.println();
		System.out.println("LESS_THAN" + "\n" + "l x y L" + "\n" + "-------");
		displayTruthTable(3, "LessThan");
	}
}
