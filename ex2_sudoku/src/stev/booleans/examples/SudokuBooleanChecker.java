package stev.booleans.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import stev.booleans.And;
import stev.booleans.BooleanFormula;
import stev.booleans.Implies;
import stev.booleans.Not;
import stev.booleans.Or;
import stev.booleans.PropositionalVariable;

public class SudokuBooleanChecker {
	static final int length = 3;
	static final int lengthSqr = length * length;
	
	public PropositionalVariable varList[][][];
	public BooleanFormula sudokuFormula = null;
	
	public SudokuBooleanChecker() {
		varList =  new PropositionalVariable[lengthSqr][lengthSqr][lengthSqr];
		for(int i = 0; i < lengthSqr; ++i) {
			for(int j = 0; j < lengthSqr; ++j) {
				for(int k = 0; k < lengthSqr; ++k) {
					String name = "" + (i+1) + (j+1) + (k+1);
					varList[i][j][k] = new PropositionalVariable(name);
					System.out.println(name);
				}
			}
		}
		
		sudokuFormula = new And(formula1(), formula2(), formula3(), formula4());
	}
	
	/**
	 * 
	 * @param operands
	 * @return a formula corresponding to the Xor of operands
	 */
	public static BooleanFormula Xor(/*@ non_null @*/ List<BooleanFormula> operands) {
		//the or represent the "at least one"
		Or or = new Or(operands);
		List<BooleanFormula> exclusions = new ArrayList<BooleanFormula>();
		//copy
		List<BooleanFormula> others = new LinkedList<BooleanFormula>();
		for (BooleanFormula b : operands) {
			others.add(b);
		}
		for (BooleanFormula b : operands) {
			others.remove(b);
			//b -> !(bi v bj v bk...)
			exclusions.add(new Implies(b, new Not(new Or(others))));
			others.add(b);
		}
		exclusions.add(or);
		return new And(exclusions);
	}
	
	/**
	 * Generate the formula corresponding to having only one digit per cell
	 */
	BooleanFormula formula1() {
		List<BooleanFormula> results = new ArrayList<BooleanFormula>();
		List<BooleanFormula> consideredVariables = new ArrayList<BooleanFormula>();
		for(int i = 0; i < lengthSqr; ++i) {
			for(int j = 0; j < lengthSqr; ++j) {
				consideredVariables.clear();
				for(int k = 0; k < lengthSqr; ++k) {
					consideredVariables.add(varList[i][j][k]);
				}
				results.add(Xor(consideredVariables));
			}
		}
		return new And(results);
	}
	
	/**
	 * Generate the formula corresponding to having each digit on each line
	 */
	BooleanFormula formula2() {
		List<BooleanFormula> results = new ArrayList<BooleanFormula>();
		List<BooleanFormula> consideredVariables = new ArrayList<BooleanFormula>();
		//for each line
		for(int i = 0; i < lengthSqr; ++i) {
			//for each digit
			for(int k = 0; k < lengthSqr; ++k) {
				consideredVariables.clear();
				//for each column
				for(int j = 0; j < lengthSqr; ++j) {
					consideredVariables.add(varList[i][j][k]);
				}
				results.add(Xor(consideredVariables));
			}
		}
		return new And(results);
	}
	
	/**
	 * Generate the formula corresponding to having each digit on each column
	 */
	BooleanFormula formula3() {
		List<BooleanFormula> results = new ArrayList<BooleanFormula>();
		List<BooleanFormula> consideredVariables = new ArrayList<BooleanFormula>();
		//for each column
		for(int j = 0; j < lengthSqr; ++j) {
			//for each digit
			for(int k = 0; k < lengthSqr; ++k) {
				consideredVariables.clear();
				//for each line
				for(int i = 0; i < lengthSqr; ++i) {
					consideredVariables.add(varList[i][j][k]);
				}
				results.add(Xor(consideredVariables));
			}
		}
		return new And(results);
	}
	
	/**
	 * Generate the formula corresponding to having each digit on each square
	 */
	BooleanFormula formula4() {
		List<BooleanFormula> results = new ArrayList<BooleanFormula>();
		List<BooleanFormula> consideredVariables = new ArrayList<BooleanFormula>();
		//for each square
		for(int s = 0; s < lengthSqr; ++s) {
			//for each digit
			for(int k = 0; k < lengthSqr; ++k) {
				consideredVariables.clear();
				//for each cell of the square
				for(int id = 0; id < lengthSqr; ++id) {
					//square 5 -> line 1 and column 2 (between 0 and 2) -> line 3 and column 6
					//id 3 -> line 1 and column 0
					int i = length * (s / length) + id / length;
					int j = length * (s % length) + id % length;
					consideredVariables.add(varList[i][j][k]);
				}
				results.add(Xor(consideredVariables));
			}
		}
		return new And(results);
	}
	
	public static void main(String[] args) 
	{
		SudokuBooleanChecker sbc = new SudokuBooleanChecker();
		BooleanFormula big_formula = sbc.sudokuFormula;
		// Convert this formula to CNF
		BooleanFormula cnf = big_formula.toCnf();
		
		// Let's print it again
		System.out.println(cnf);
		
		// Export this formula as an array of clauses
		int[][] clauses = cnf.getClauses();
		
		// What's in that array? First element corresponds to first clause: [1, -2, 3]
		System.out.println(Arrays.toString(clauses[0]));
		// Second element corresponds to second clause: [1, -3, 1, 2]
		System.out.println(Arrays.toString(clauses[1]));
		
		// What is the integer associated to variable q?
		Map<String,Integer> associations = cnf.getVariablesMap();
		System.out.println("Variable 999 is associated to number " + associations.get("999"));
	}
}
