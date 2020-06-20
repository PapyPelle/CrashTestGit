package question1;

/**
 * Aspect checking property 2 :
 * Stack elements are removed in reverse order of insertion
 */
public aspect Aspect3 {
	
	private java.util.Stack<Object> observerStack = new java.util.Stack<Object>();

	// POP CUT
	pointcut call_pop() :
		call(Object StackInterface.pop())
		// only using following code so that the mainX tests doesn't print too much of the same error
		&& !withincode(void MainTests.Main1(..)) && !withincode(void MainTests.Main2(..));
	
	after() returning(Object object) : call_pop() {
		if (object == null) {
			System.err.println("Unkown error"); // actually we know one reason : when pop is used on empty list. Not my task
		}
		else {
			// It should be the same object reference, so we can use (not)equal here
			Object observerObject = observerStack.pop();
			if (object != observerObject) {
				String line = "" + thisJoinPointStaticPart.getSourceLocation().getLine();
				System.err.println("ERROR PROPERTY 3 (line " + line + "): method pop() do not remove in the correct order ( "
						+ object.toString() + " != " + observerObject.toString() + " )");
			}
		}
	}
	
	// PUSH CUT
	pointcut call_push(Object object) :
		call(void StackInterface.push(Object)) && args(object)
		// only using following code so that the mainX tests doesn't print too much of the same error
		&& !withincode(void MainTests.Main1(..)) && !withincode(void MainTests.Main2(..));
		
	after(Object object) returning() : call_push(object) {
		observerStack.push(object);
	}
		
	
}
