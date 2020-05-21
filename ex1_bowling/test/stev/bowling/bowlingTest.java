package stev.bowling;

import static org.junit.Assert.*;

import org.junit.*;


public class bowlingTest
{
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
	
	//----------------------------------------------------------------------------------------------------
	//------------------------------------------- NormalFrames -------------------------------------------
	//----------------------------------------------------------------------------------------------------	
	
	//--------------------------------------- new NormalFrame(int) ----------------------------------------
	
	/*
	 * A standard bowling game is composed of 9 NormalFrame (1 to 9) and a LastFrame (10)
	 * All other initializer values are expected to throw a BowlingException
	 */
	
	/**
	 * Test that new NormalFrame(5) does not throw.
	 */
	@Test
	public void testNormalFrame_Constructor_validValue5_noException() {
		new NormalFrame(5);
	}

	/**
	 * Test that new NormalFrame(8) does not throw.
	 */
	@Test
	public void testNormalFrame_Constructor_validValue8_noException() {
		new NormalFrame(8);
	}

	/**
	 * Test that new NormalFrame(1) does not throw.
	 */
	@Test
	public void testNormalFrame_Constructor_minValue_noException() {
		new NormalFrame(1);
	}

	/**
	 * Test that new NormalFrame(9) does not throw.
	 */
	@Test
	public void testNormalFrame_Constructor_maxValue_noException() {
		new NormalFrame(9);
	}

	/**
	 * Test that new NormalFrame(0) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_Constructor_zeroValue_throwBowlingException() {
		new NormalFrame(0);
	}

	/**
	 * Test that new NormalFrame(-9) throws a BownlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_Constructor_negativeValue_throwBowlingException() {
		new NormalFrame(-9);
	}

	/**
	 * Test that new NormalFrame(10) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_Constructor_wrongValue10_throwBowlingException() {
		new NormalFrame(10);
	}

	/**
	 * Test that new NormalFrame(20) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_Constructor_wrongValue20_throwBowlingException() {
		new NormalFrame(20);
	}

	//------------------------------------- NormalFrame.countRolls() -------------------------------------
	
	/**
	 * Test NormalFrame.countRoll() return value to be 0 at initialization 
	 */
	@Test
	public void testNormalFrame_CountRolls_onNewFrame() {
		assertEquals("Invalid rolls count : ", 0, normalFrame.countRolls());
	}

	/**
	 * Test NormalFrame.countRoll() return value to be 0 after reset 
	 */
	@Test
	public void testNormalFrame_CountRolls_afterReset() {
		normalFrame.setPinsDown(1, 1);
		normalFrame.reset();
		assertEquals("Invalid rolls count : ", 0, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 1 after one setPinsDown(1,1)
	 */
	@Test
	public void testNormalFrame_CountRolls_afterOneRoll() {
		normalFrame.setPinsDown(1, 1);
		assertEquals("Invalid rolls count : ", 1, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 2 after two setPinsDown(1:2, 1) 
	 */
	@Test
	public void testNormalFrame_CountRolls_afterTwoRolls() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 1);
		assertEquals("Invalid rolls count : ", 2, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 1 after one setPinsDown(1,0)
	 */
	@Test
	public void testNormalFrame_CountRolls_afterGutterOnFirstRoll() {
		normalFrame.setPinsDown(1, 0);
		assertEquals("Invalid rolls count : ", 1, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 2 after one setPinsDown(1,1) and one setPinsDown(2,0)
	 */
	@Test
	public void testNormalFrame_CountRolls_afterGutterOnSecondRoll() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 0);
		assertEquals("Invalid rolls count : ", 2, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 2 after one setPinsDown(1,0) and one setPinsDown(2,0)
	 */
	@Test
	public void testNormalFrame_CountRolls_afterTwoGutters() {
		normalFrame.setPinsDown(1, 0).setPinsDown(2, 0);
		assertEquals("Invalid rolls count : ", 2, normalFrame.countRolls());
	}
	
	//----------------------------------- NormalFrame.getFrameNumber() -----------------------------------
	
	/**
	 * Test the result of the method NormalFrame.getFrameNumber() for frame 1 to 9
	 */
	@Test
	public void testNormalFrame_FrameNumber_returnsIntializationValue() {
		int begin = 1, end = 9;
		
		int[] expected = new int[end - begin + 1];
		int[] result = new int[expected.length];
		
		for(int i = begin; i <= end; i++) {
			expected[i-begin] = i;
			result[i-begin] = new NormalFrame(i).getFrameNumber();
		}
		
		assertArrayEquals("Results don't match with expected values : ", expected, result);
	}
	
	//------------------------------------- NormalFrame.setPinDown() -------------------------------------
	
	//------------------------------------ NormalFrame.setPinDown(int) -----------------------------------
	
	//----------------------------------- NormalFrame.countPinsDown(int) ---------------------------------
	
	
	//----------------------------------------------------------------------------------------------------
	//-------------------------------------------- LastFrame ---------------------------------------------
	//----------------------------------------------------------------------------------------------------
	
	//---------------------------------------- new LastFrame(int) ----------------------------------------

	/**
	 * A standard bowling game is composed of 9 NormalFrame (1 to 9) and a LastFrame (10)
	 * All other initializer values are expected to throw a BowlingException
	 */
	
	/**
	 * Test that new LastFrame(10) does not throw.
	 */
	@Test
	public void testLastFrame_Constructor_validValue10_noException() {
		new LastFrame(10);
	}

	/**
	 * Test that new LastFrame(0) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_Constructor_zeroValue_throwBowlingException() {
		new LastFrame(0);
	}

	/**
	 * Test that new LastFrame(-5) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_Constructor_negativeValue_throwBowlingException() {
		new LastFrame(-5);
	}

	/**
	 * Test that new LastFrame(9) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_Constructor_wrongValue9_throwBowlingException() {
		new LastFrame(9);
	}

	/**
	 * Test that new LastFrame(11) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_Constructor_wrongValue11_throwBowlingException() {
		new LastFrame(11);
	}

	//-------------------------------------- LastFrame.countRolls() --------------------------------------
	
	/**
	 * Test LastFrame.countRoll() return value to be 0 at initialization 
	 */
	@Test
	public void testLastFrame_CountRolls_onNewFrame() {
		assertEquals("Invalid rolls count : ",   0, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 0 after reset 
	 */
	@Test
	public void testLastFrame_CountRolls_afterReset() {
		lastFrame.setPinsDown(1, 1);
		lastFrame.reset();
		assertEquals("Invalid rolls count : ",   0, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 1 after one setPinsDown(1,1)
	 */
	@Test
	public void testLastFrame_CountRolls_afterOneRoll() {
		lastFrame.setPinsDown(1, 1);
		assertEquals("Invalid rolls count : ", 1, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 2 after two setPinsDown(1:2, 1) 
	 */
	@Test
	public void testLastFrame_CountRolls_afterTwoRolls() {
		lastFrame.setPinsDown(1, 1).setPinsDown(2, 1);
		assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 1 after one setPinsDown(1,0)
	 */
	@Test
	public void testLastFrame_CountRolls_afterGutterOnFirstRoll() {
		lastFrame.setPinsDown(1, 0);
		assertEquals("Invalid rolls count : ", 1, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 2 after one setPinsDown(1,1) and one setPinsDown(2,0)
	 */
	@Test
	public void testLastFrame_CountRolls_afterGutterOnSecondRoll() {
		lastFrame.setPinsDown(1, 1).setPinsDown(2, 0);
		assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 2 after one setPinsDown(1,0) and one setPinsDown(2,0)
	 */
	@Test
	public void testLastFrame_CountRolls_afterTwoGutters() {
		lastFrame.setPinsDown(1, 0).setPinsDown(2, 0);
		assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 3 after three setPinsDown(1:3, 5) (allowed by a spare)
	 */
	@Test
	public void testLastFrame_CountRolls_afterThreeRolls() {
		lastFrame.setPinsDown(1, 5);
		lastFrame.setPinsDown(2, 5);
		lastFrame.setPinsDown(3, 5);
		assertEquals("Invalid rolls count : ", 3, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 3 after two setPinsDown(1:2,5) and one setPinsDown(3,0)
	 */
	@Test
	public void testLastFrame_CountRolls_afterGutterOnThirdRoll() {
		lastFrame.setPinsDown(1, 5).setPinsDown(2, 5).setPinsDown(3, 0);
		assertEquals("Invalid rolls count : ", 3, lastFrame.countRolls());
	}

	//----------------------------------- NormalFrame.getFrameNumber() -----------------------------------
	
	/**
	 * Test the result of the method LastFrame.getFrameNumber() for frame 10
	 */
	@Test
	public void testLastFrame_FrameNumber() {
		assertEquals("Invalid frame number : ", 10, new LastFrame(10).getFrameNumber());
	}
	
	//-------------------------------------- LastFrame.setPinDown() --------------------------------------
	
	//------------------------------------- LastFrame.setPinDown(int) ------------------------------------
	
	//------------------------------------ LastFrame.countPinsDown(int) ----------------------------------
	
}
