package stev.booleans.examples;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import stev.booleans.And;
import stev.booleans.BooleanFormula;
import stev.booleans.Implies;
import stev.booleans.Not;
import stev.booleans.Or;
import stev.booleans.PropositionalVariable;


/**
 * The Sudoku checker class. Also give one possible solution if there is one
 * @author {Timtohée Corsini , Yann Royant} CORT20069704  ROYY27029808
 *
 */
public class SudokuBooleanChecker {
	static final int length = 3;
	static final int lengthSqr = length * length;
	
	// All propositional variables you need for sudoku (lengthSqr^3)
	private PropositionalVariable varList[][][];
	// The general Sudoku formula representing the rules
	private BooleanFormula sudokuFormula = null;
	// List of formula for the boxes already filled
	private List<BooleanFormula> filledVariables = null;
	// One solution of the Sudoku if it exist, relative to filledVariables
	private int[] solution = null;
	
	/**
	 * Create a Sudoku solver with the general rules and variables
	 */
	public SudokuBooleanChecker() {
		// Create all variables
		varList =  new PropositionalVariable[lengthSqr][lengthSqr][lengthSqr];
		for(int i = 0; i < lengthSqr; ++i) {
			for(int j = 0; j < lengthSqr; ++j) {
				for(int k = 0; k < lengthSqr; ++k) {
					String name = getVariableName(i,j,k);
					varList[i][j][k] = new PropositionalVariable(name);
				}
			}
		}
		// Create the general sudoku formula
		sudokuFormula = new And(formula1(), formula2(), formula3(), formula4());
	}
	
	
	/**
	 * Method for string variable conversion
	 * @return the name of the literal 
	 */
	private String getVariableName(int row, int column, int number) {
		return "r" + (row+1) + "c" + (column+1) + "d" + (number+1);
	}
	
	
	/**
	 * Return the Xor of the operands, it is evaluated to true iff only one operand is true
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
	private BooleanFormula formula1() {
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
	private BooleanFormula formula2() {
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
	private BooleanFormula formula3() {
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
	private BooleanFormula formula4() {
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
	
	/**
	 * Parse the inputData and adapt it for the Sudoku checker
	 * @param inputData
	 */
	public void addVariables(String inputData) {
		filledVariables = new ArrayList<BooleanFormula>();
		for(int id = 0; id < inputData.length(); ++id) {
			char c = inputData.charAt(id);
			if(c == '#')
				continue;
			else {
				int i = id / lengthSqr;
				int j = id % lengthSqr;
				int k = Character.getNumericValue(c) - 1;
				filledVariables.add(varList[i][j][k]);
				// System.out.println(id + ": " + c + ", [" + i + ", " + j + ", " + k + "]");
			}
		}
	}
	
	/**
	 * Method used to resolve a Sudoku grid considering a set of known variables
	 * @return true if satisfiable, also fill the solution attribute
	 */
	public boolean solveSudoku() throws Exception {
		// If no data, do nothing
		if (filledVariables == null) {
			System.out.println("No data filled : please use the addVariables method before calling this one, else it's always satisfiable.");
			return true;
		};
		// Add the sudoku rules into the one-literal clause data
		filledVariables.add(sudokuFormula);
		// Create the big formula
		BooleanFormula big_formula = new And(filledVariables);
		// Convert this formula to CNF
		BooleanFormula cnf = big_formula.toCnf();
		// Get solver specifications
		int numVar = lengthSqr*lengthSqr*lengthSqr;
		int clauses[][] = cnf.getClauses();
		int numClauses = clauses.length;
		// Create and initialize solver
		ISolver solver = SolverFactory.newDefault();
		solver.newVar(numVar);
		solver.setExpectedNumberOfClauses(numClauses);
		// Add clauses (integers arrays) one by one
		for (int i=0; i < numClauses; i++) {
			try {
				solver.addClause(new VecInt(clauses[i]));
			}
			catch (ContradictionException e) {
				return false;
			}
		}
		// Use problem instance to get results
		IProblem problem = solver;
		if(problem.isSatisfiable()) {
			Map<String,Integer> varMap = cnf.getVariablesMap();
			solution = new int[lengthSqr*lengthSqr];
			// Extract model and map back the correct values
			int[] model = problem.findModel();
			for(int i=0; i < lengthSqr; i++) {
				for(int j=0; j < lengthSqr; j++) {
					for(int k=0; k < lengthSqr; k++) {
						String var = getVariableName(i,j,k);
						int varId = varMap.get(var) - 1;
						if (model[varId] > 0) {
							solution[i*lengthSqr + j] = k+1;
						}
					}
				}
			}
			return true;
		}
		// else
		solution = null;
		return false;
	}
	
	/**
	 * Allow to reuse the class with another set of inputs
	 */
	public void resetData() {
		solution = null;
		filledVariables = null;
	}
	
	/**
	 * Print Sudoku grid solution (if there is one)
	 */
	public void printSolution() {
		if (solution == null) {
			System.out.println("No Solution.");
		} else {
			for(int i=0; i < solution.length; i++) {
				if (i != 0 && i%lengthSqr == 0)
					System.out.println("|");
				if (i%(lengthSqr*length) == 0)
					System.out.println("-------------------------");
				if (i%length == 0)
					System.out.print("| ");
				System.out.print(solution[i]+ " ");
			}
			System.out.println("|");
			System.out.println("-------------------------");
		}
	}
	
	
	/**
	 * Check the Sudoku passed as argument and print one result if it's solvable
	 * @param args Format xxxxxxxxxx where each x is either 1 to 9 or #; lengthSqr ^ 2 x
	 */
	public static void main(String[] args) 
	{
		String input = "#################################################################################";
		if (args.length > 0) {
			input = args[0];
		}
		else {
			input = "123456789769138245548792136436219578271845963895673412654321897312987654987564321";
		}
		System.out.println("input : " + input);
		
		SudokuBooleanChecker sbc = new SudokuBooleanChecker();
		sbc.addVariables(input);
		try {
			sbc.solveSudoku();
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}
		sbc.printSolution();
		return;
	}
}
