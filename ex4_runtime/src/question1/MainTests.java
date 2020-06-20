package question1;

/**
 * Main for testing Aspect2 with the 2nd property :
 * 
 * The push method increases the stack size by 1,
 * and the pop method decreases the stack size by 1
 */
public class MainTests {
	
	/**
	 * Bad stack implementation which does stupid things with the size
	 * Besides, it doesn't stock any objects at all and just ignore them
	 */
	private static class MyBadStack implements StackInterface {
		// Start with size 12 always, because why not
		private int size = 12;
		// Add 2 to size
		public void push (Object t) { size += 2; }
		// Do nothing
		public Object pop() { return null; }
		// Do nothing
		public Object top() { return null; }
		// Return always false, stack is never empty !
		public boolean isEmpty() { return false; }
		// If this is broken too (always return 0), this confuse the debug part
		// More different aspects are required to catch the case where size() method is wrong
		public int size() {	return size; }
	}
	
	/**
	 * Bad stack implementation which does stupid things with ordering
	 * It is actually a FIFO that stocks integers only, with "infinite" capacity
	 */
	private static class UndercoverBadFIFO implements StackInterface {
		private static int capacity = 20;
		private int[] trustWorthyStack = new int[capacity];
		private int top = 0;
		private int bot = 0;
		// normally adds items if they are integer (or try to make one)
		public void push (Object t) {
			try {
				trustWorthyStack[top] = (int) t;
				// because looping is fun if capacity is exceeded :
				top = (top + 1) % capacity; 
			}
			catch (java.lang.ClassCastException e) {
				// do nothing, be quiet
			}
		}
		// wrongly pop the start of the "stack"
		public Object pop() {
			int ret = trustWorthyStack[bot];
			bot = (bot + 1) % capacity; // yeah ! fun
			return ret;
		}
		// return middle because why not (since we loop its even worse)
		public Object top() { return trustWorthyStack[(bot+top)/2]; }
		// *evil laugher*
		public boolean isEmpty() { return (top == bot);	}
		// same
		public int size() {	return (top - bot); }
	}
	
	
	/**
	 * Try to make error appears from property 1
	 */
	public static void Main1() {
		StackInterface stack = new Stack();
		if (stack.isEmpty()) {
			System.out.println("Stack is empty");
		
			stack.pop(); // error, wrong usage
		
			stack.top(); // error, wrong usage
			
		}
		else { System.out.println("unknown error, stack should be empty"); }
		
		stack.push(42); // adding one element
		
		if (!stack.isEmpty()) {
			System.out.println("Stack is NOT empty anymore");
			
			stack.top(); // no error
			
			if (!stack.isEmpty()) {
			
				stack.pop(); // no error
				
			}
			else { System.out.println("unknown error, top() should not empty the stack"); }
		
		}
		else { System.out.println("unknown error, stack should not be empty after push"); }
	}
	
	/**
	 *  Try to make error appears from property 2
	 */
	public static void Main2(StackInterface stack) {
		
		// Test one case
		stack.push(42);
		
		stack.pop();
		
		if (stack instanceof MyBadStack) { return; } // no need to flood, we know it's broken

		
		// Test to go over stack size limit (should reallocate array and all)
		int stackSize = 1000; // actually useless for the bad stack

		// push to storage limit
		for (int i = 0; i < stackSize; i++) {
			stack.push(i+1); // adding stackSize elements
		}
		
		// Test pop push just before the limit
		stack.pop();
		stack.push(stackSize);
		
		// Test push pop at limit
		// (error here, we saw that one appear with Test.java | Stack.java (line 29))
		stack.push(stackSize + 1); // go over limit
		stack.pop(); // go back under
		
		// Test pop push after limit
		stack.push(stackSize + 1); // we need one more because of last pop
		stack.push(stackSize + 2);
		stack.pop();
		
		// Finally remove everything
		stack.pop(); // remove the "stackSize+1"th element
		for (int i = 0; i < stackSize; i++) {
			stack.push(i); // pop back stackSize remaining elements
		}
	}
	
	/**
	 * Try to make errors appear from property3
	 */
	public static void Main3(StackInterface stack) {
		// we know exercise Stack will crash and spam after 
		// the stackSize number of push because of Aspect2,
		// so we won't push (haha) so far
		int sizeTest = 9;
		
		for(int i = 0; i < sizeTest; i++) {
			stack.push(i);
		}
	
		for(int i = 0; i < sizeTest; i++) {
			stack.pop();
		}
	}
	
	/**
	 * Main function to test all aspect classes
	 */
	public static void main(String[] args) {
		System.out.println("--- Test property 1 ---");
		Main1();
		System.out.println("--------------------------------\n");
		
		System.out.println("--- Test property 2 ---");
		// Test the stack in exercise
		System.out.println("TEST exercise STACK");
		Main2(new Stack());
		System.out.println("--------------------------------\n");
		// Test my completely bad stack
		System.out.println("TEST MyBad STACK");
		Main2(new MyBadStack());
		System.out.println("--------------------------------\n");
		
		System.out.println("--- Test property 3 ---");
		// Test the stack in exercise
		System.out.println("TEST exercise STACK");
		Main3(new Stack());
		System.out.println("--------------------------------\n");
		// Test my completely bad stack
		System.out.println("TEST MyBad STACK");
		Main3(new UndercoverBadFIFO());
		System.out.println("--------------------------------\n");
	}

}
