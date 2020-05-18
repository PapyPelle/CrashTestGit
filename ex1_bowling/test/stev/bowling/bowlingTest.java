package stev.bowling;

import static org.junit.Assert.*;

import org.junit.*;


public class bowlingTest
{

	@Before
	public void setUp() throws Exception 
	{
		
	}

	@After
	public void tearDown() throws Exception
	{
		
	}
			
	/**
	 * Return "true" if a BowlingException is thrown while creating the Frame object
	 * @param number The number used to initialize Frame(int)
	 * @param isLastFrame True if we are testing a LastFrame type of Frame (false is NormalFrame)
	 */
	private boolean isExceptionFrameConstructor(int number, boolean isLastFrame) {
		try {
			@SuppressWarnings("unused")
			Frame f;
			if (isLastFrame)
				f = new LastFrame(number);
			else
				f = new NormalFrame(number);
			return false;
		} catch (BowlingException e) {
			return true;
		}
	}

	/**
	 * Several tests on different Frame initializations
	 */
	@Test
	public void testFrameConstructor1() {
		assertFalse("NormalFrame(0) : throw exception",  isExceptionFrameConstructor(0,  false));
	}
	@Test
	public void testFrameConstructor2() {
		assertFalse("NormalFrame(5) : throw exception",  isExceptionFrameConstructor(5,  false));
	}
	@Test
	public void testFrameConstructor3() {
		assertFalse("NormalFrame(8) : throw exception",  isExceptionFrameConstructor(8,  false));
	}
	@Test
	public void testFrameConstructor4() {
		assertTrue("NormalFrame(9) : throw no exception", isExceptionFrameConstructor(9,  false));
	}
	@Test
	public void testFrameConstructor5() {
		assertTrue("NormalFrame(10) : throw no exception", isExceptionFrameConstructor(10, false));
	}
	@Test
	public void testFrameConstructor6() {
		assertTrue("NormalFrame(20) : throw no exception", isExceptionFrameConstructor(20, false));
	}
	@Test
	public void testFrameConstructor7() {
		assertTrue("NormalFrame(-9) : throw no exception", isExceptionFrameConstructor(-9, false));
	}
	@Test
	public void testFrameConstructor8() {
		assertFalse("LastFrame(9) : throw exception", isExceptionFrameConstructor(9, true));
	}
	@Test
	public void testFrameConstructor9() {
		assertTrue("LastFrame(8) : throw no exception",    isExceptionFrameConstructor(8,  true));
	}
	@Test
	public void testFrameConstructor10() {
		assertTrue("LastFrame(0) : throw no exception",    isExceptionFrameConstructor(0,  true));
	}
	@Test
	public void testFrameConstructor11() {
		assertTrue("LastFrame(-5) : throw no exception",   isExceptionFrameConstructor(-5, true));
	}
	@Test
	public void testFrameConstructor12() {
		assertTrue("LastFrame(11) : throw no exception",   isExceptionFrameConstructor(10, true));
	}


}
