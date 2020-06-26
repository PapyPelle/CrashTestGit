package question1;

/**
 * Aspect checking property 1 :
 * Top and pop methods should never be never called on an empty stack
 */
public aspect Aspect1 {
	
	private int size = 0;
	
	after() returning() : call(* StackInterface.push(..)) {
		size += 1;
	}

	after() returning() : call(* StackInterface.pop()) {
		if (size > 0) size -= 1; // else it should print an error before anyway
	}
	
	pointcut call_pop_top() :
		(call(* StackInterface.pop()) || call(* StackInterface.top()))
		// only using following code so that the mainX tests doesn't print too much of the same error
		&& !withincode(void MainTests.Main2(..)) && !withincode(void MainTests.Main3(..));
	
	before(): call_pop_top() {
		if (size <= 0) {
			String line = "" + thisJoinPointStaticPart.getSourceLocation().getLine();
			String sourceName = thisJoinPointStaticPart.getSourceLocation()
					.getWithinType().getCanonicalName();
			String functionName = thisJoinPointStaticPart.getSignature().getName();
			System.err.println("ERROR PROPRIETE 1 (line " + line + "): in " +
					sourceName + " calling " + functionName + "() on an empty stack");
		}
	}
	
	
}
