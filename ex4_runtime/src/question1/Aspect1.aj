package question1;

/**
 * Aspect checking property 1 :
 * Top and pop methods should never be never called on an empty stack
 */
public aspect Aspect1 {

	pointcut call_pop_top(StackInterface stack) :
		(call(* StackInterface.pop()) || call(* StackInterface.top())) && target(stack)
		// only using following code so that the mainX tests doesn't print too much of the same error
		&& !withincode(void MainTests.Main2(..)) && !withincode(void MainTests.Main2(..));
	
	before(StackInterface stack): call_pop_top(stack) {
		if (stack.isEmpty()) {
			String line = "" + thisJoinPointStaticPart.getSourceLocation().getLine();
			String sourceName = thisJoinPointStaticPart.getSourceLocation()
					.getWithinType().getCanonicalName();
			String functionName = thisJoinPointStaticPart.getSignature().getName();
			System.err.println("ERROR PROPRIETE 1 (line " + line + "): in " +
					sourceName + " calling " + functionName + "() on an empty stack");
		}
	}
	
}
