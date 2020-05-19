package stev.bowling;

import static org.junit.Assert.*;

import org.junit.*;


public class bowlingTest
{
	private static boolean NORMAL_FRAME = false;
	private static boolean LAST_FRAME   = true;
	
	public Frame normalFrame;
	public Frame lastFrame;

	@Before
	public void setUp() throws Exception 
	{
		normalFrame = new NormalFrame(1);
		lastFrame   = new LastFrame(10);
	}

	@After
	public void tearDown() throws Exception
	{
		
	}
	
	
	//----------------------------------------- FRAME CONSTRUCTOR -----------------------------------------
			
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
	 *   NOTE: No specification given. Since an exception is only thrown outside [0:10] 
	 *   range for both Normal and Last Frame this make no sense what to expect;
	 *   So let's just say you are only allowed to build a standard 1 to 10 frame game
	 */
	@Test
	public void testNormalFrameConstructor_classicValue5_noException() {
		assertFalse("NormalFrame(5) : exception is thrown (shouldn't)", isExceptionFrameConstructor(5,  NORMAL_FRAME));
	}
	@Test
	public void testNormalFrameConstructor_classicValue8_noException() {
		assertFalse("NormalFrame(8) : exception is thrown (shouldn't)", isExceptionFrameConstructor(8,  NORMAL_FRAME));
	}
	@Test
	public void testNormalFrameConstructor_minValue_noException() {
		assertFalse("NormalFrame(1) : exception is thrown (shouldn't)", isExceptionFrameConstructor(1,  NORMAL_FRAME));
	}
	@Test
	public void testNormalFrameConstructor_maxValue_noException() {
		assertTrue("NormalFrame(9) : exception is thrown (shouldn't)",  isExceptionFrameConstructor(9,  NORMAL_FRAME));
	}
	@Test
	public void testNormalFrameConstructor_zeroValue_throwBowlingException() {
		assertTrue("NormalFrame(0) : should throw an exception",        isExceptionFrameConstructor(0,  NORMAL_FRAME));
	}
	@Test
	public void testNormalFrameConstructor_negativeValue_throwBowlingException() {
		assertTrue("NormalFrame(-9) : should throw an exception",       isExceptionFrameConstructor(-9, NORMAL_FRAME));
	}
	@Test
	public void testNormalFrameConstructor_wrongValue10_throwBowlingException() {
		assertTrue("NormalFrame(10) : should throw an exception",       isExceptionFrameConstructor(10, NORMAL_FRAME));
	}
	@Test
	public void testNormalFrameConstructor_wrongValue20_throwBowlingException() {
		assertTrue("NormalFrame(20) : should throw an exception",       isExceptionFrameConstructor(20, NORMAL_FRAME));
	}
	@Test
	public void testLastFrameConstructor_classicValue_noException() {
		assertFalse("LastFrame(10) : exception is thrown (shouldn't)",   isExceptionFrameConstructor(10, LAST_FRAME));
	}
	@Test
	public void testLastFrameConstructor_zeroValue_throwBowlingException() {
		assertTrue("LastFrame(0) : should throw an exception",          isExceptionFrameConstructor(0,  LAST_FRAME));
	}
	@Test
	public void testLastFrameConstructor_negativeValue_shouldThrowBowlingException() {
		assertTrue("LastFrame(-5) : should throw an exception",         isExceptionFrameConstructor(-5, LAST_FRAME));
	}
	@Test
	public void testLastFrameConstructor_wrongValue9_throwBowlingException() {
		assertTrue("LastFrame(9) : should throw an exception",          isExceptionFrameConstructor(9,  LAST_FRAME));
	}
	@Test
	public void testLastFrameConstructor_wrongValue11_throwBowlingException() {
		assertTrue("LastFrame(11) : should throw an exception",         isExceptionFrameConstructor(11, LAST_FRAME));
	}
	// All of this could be automatic with parameters of what to test and what to expect but we didn't know how to do so in JUNIT
	// (and we only have one class to parameterize so we use it for something else)

	
	//----------------------------------------- FRAME COUNTROLLS METHOD -----------------------------------------
	
	/**
	 * Test normal and last frame countRoll() return value to be 0 at initialization 
	 */
	@Test
	public void testCountRolls_newFrame() {
		assertEquals("new NormalFrame countRolls should be 0 : ", 0, normalFrame.countRolls());
		assertEquals("new LastFrame countRolls should be 0 : ",   0, lastFrame.countRolls());
	}
	
	/**
	 * Test normal and last frame countRoll() return value to be 0 after reset 
	 */
	@Test
	public void testCountRolls_resetFrame() {
		normalFrame.setPinsDown(1, 1);
		normalFrame.reset();
		assertEquals("reseted NormalFrame countRolls should be 0 : ", 0, normalFrame.countRolls());
		lastFrame.setPinsDown(1, 1);
		lastFrame.reset();
		assertEquals("reseted LastFrame countRolls should be 0 : ",   0, lastFrame.countRolls());
	}
	
	/**
	 * Test normal and last frame countRoll() return value to be 1 after one setPinsDown(1,1)
	 */
	@Test
	public void testCountRolls_oneRoll() {
		normalFrame.setPinsDown(1, 1);
		assertEquals("after 1 roll NormalFrame countRolls should be 1 : ", 1, normalFrame.countRolls());
		lastFrame.setPinsDown(1, 1);
		assertEquals("after 1 roll LastFrame countRolls should be 1 : ", 1, lastFrame.countRolls());
	}
	
	/**
	 * Test normal and last frame countRoll() return value to be 2 after two setPinsDown(1:2, 1) 
	 */
	@Test
	public void testCountRolls_twoRolls() {
		normalFrame.setPinsDown(1, 1);
		normalFrame.setPinsDown(2, 1);
		assertEquals("after 2 rolls NormalFrame countRolls should be 2 : ", 2, normalFrame.countRolls());
		lastFrame.setPinsDown(1, 1);
		lastFrame.setPinsDown(2, 1);
		assertEquals("after 2 rolls LastFrame countRolls should be 2 : ", 2, lastFrame.countRolls());
	}
	
	/**
	 * Test last frame countRoll() return value to be 3 after three setPinsDown(1:3, 5) (allowed by a spare)
	 */
	@Test
	public void testCountRolls_threeRolls() {
		lastFrame.setPinsDown(1, 5);
		lastFrame.setPinsDown(2, 5);
		lastFrame.setPinsDown(3, 5);
		assertEquals("after 3 rolls LastFrame countRolls should be 3", 3, lastFrame.countRolls());
	}
	
	
	//----------------------------------------- FRAME GETFRAMENUMBER METHOD -----------------------------------------
	
	/**
	 * Test the result of the method getFrameNumber for frame 1 to 10 (10 is LastFrame type of Frame)
	 */
	@Test
	public void testFrameNumber() {
		for(int i=1; i < 11; i++) {
			Frame f;
			if (i == 10)
				f = new LastFrame(i);
			else
				f = new NormalFrame(i);
			assertEquals("Method Frame Number give wrong number", i, f.getFrameNumber());
		}
	}
	
	
	//----------------------------------------- NORMALFRAME SETPINSDOWN METHOD -----------------------------------------
	
	//----------------------------------------- LASTFRAME SETPINSDOWN METHOD -----------------------------------------


}
