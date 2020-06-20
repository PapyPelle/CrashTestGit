package question1;

/**
 * Aspect checking property 2 :
 * The push method increases the stack size by 1,
 * and the pop method decreases the stack size by 1
 */
public aspect Aspect2 {
	
	// POP CUT
	int size_before_pop = -1;

	pointcut call_pop(StackInterface stack) :
		call(* StackInterface.pop()) && target(stack)
		// only using following code so that the mainX tests doesn't print too much of the same error
		&& !withincode(void MainTests.Main1(..)) && !withincode(void MainTests.Main3(..));
	
	before(StackInterface stack): call_pop(stack) {
		size_before_pop = stack.size();
	}
	
	after(StackInterface stack) returning(Object obj) : call_pop(stack) {
		if (size_before_pop == -1) { // should never happen, maybe if stack.size() is broken
			System.err.println("Unkown error");
		}
		else {
			if (stack.size() != size_before_pop - 1) {
				String line = "" + thisJoinPointStaticPart.getSourceLocation().getLine();
				System.err.println("ERROR PROPERTY 2 (line " + line +
						"): method pop() not correctly reducing size by 1" );
			}
		}
		size_before_pop = -1;
	}
	
	// PUSH CUT
	int size_before_push = -1;

	pointcut call_push(StackInterface stack) :
		call(* StackInterface.push(..)) && target(stack)
		// only using following code so that the mainX tests doesn't print too much of the same error
		&& !withincode(void MainTests.Main1(..)) && !withincode(void MainTests.Main3(..));
	
	before(StackInterface stack): call_push(stack) {
		size_before_push = stack.size();
	}
	
	after(StackInterface stack) returning(Object obj) : call_push(stack) {
		if (size_before_push == -1) { // should never happen, maybe if stack.size() is broken
			System.err.println("Unkown error");
		}
		else {
			if (stack.size() != size_before_push + 1) {
				String line = "" + thisJoinPointStaticPart.getSourceLocation().getLine();
				System.err.println("ERROR PROPERTY 2 (line " + line +
						"): method push() not correctly increasing size by 1" );
			}
		}
		size_before_push = -1;
	}
	
}
