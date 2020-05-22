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
	public void testNormalFrame_Constructor_withValidValue5_noException() {
		new NormalFrame(5);
	}

	/**
	 * Test that new NormalFrame(8) does not throw.
	 */
	@Test
	public void testNormalFrame_Constructor_withValidValue8_noException() {
		new NormalFrame(8);
	}

	/**
	 * Test that new NormalFrame(1) does not throw.
	 */
	@Test
	public void testNormalFrame_Constructor_withMinValue1_noException() {
		new NormalFrame(1);
	}

	/**
	 * Test that new NormalFrame(9) does not throw.
	 */
	@Test
	public void testNormalFrame_Constructor_withMaxValue9_noException() {
		new NormalFrame(9);
	}

	/**
	 * Test that new NormalFrame(0) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_Constructor_withZeroValue_throwsBowlingException() {
		new NormalFrame(0);
	}

	/**
	 * Test that new NormalFrame(-9) throws a BownlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_Constructor_withNegativeValueMinus9_throwsBowlingException() {
		new NormalFrame(-9);
	}

	/**
	 * Test that new NormalFrame(10) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_Constructor_withIllegalValue10_throwsBowlingException() {
		new NormalFrame(10);
	}

	/**
	 * Test that new NormalFrame(20) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_Constructor_withIllegalValue20_throwsBowlingException() {
		new NormalFrame(20);
	}

	//------------------------------------- NormalFrame.countRolls() -------------------------------------
	
	/**
	 * Test NormalFrame.countRoll() return value to be 0 at initialization 
	 */
	@Test
	public void testNormalFrame_countRolls_onNewFrame_returns0() {
		assertEquals("Invalid rolls count : ", 0, normalFrame.countRolls());
	}

	/**
	 * Test NormalFrame.countRoll() return value to be 0 after reset 
	 */
	@Test
	public void testNormalFrame_countRolls_afterReset_returns0() {
		normalFrame.setPinsDown(1, 1);
		normalFrame.reset();
		assertEquals("Invalid rolls count : ", 0, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 1 after one setPinsDown(1,1)
	 */
	@Test
	public void testNormalFrame_countRolls_afterOneRoll_returns1() {
		normalFrame.setPinsDown(1, 1);
		assertEquals("Invalid rolls count : ", 1, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 2 after two setPinsDown(1:2, 1) 
	 */
	@Test
	public void testNormalFrame_countRolls_afterTwoRolls_returns2() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 1);
		assertEquals("Invalid rolls count : ", 2, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 1 after one setPinsDown(1,0)
	 */
	@Test
	public void testNormalFrame_countRolls_afterGutterOnFirstRoll_returns1() {
		normalFrame.setPinsDown(1, 0);
		assertEquals("Invalid rolls count : ", 1, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 2 after one setPinsDown(1,1) and one setPinsDown(2,0)
	 */
	@Test
	public void testNormalFrame_countRolls_afterGutterOnSecondRoll_returns2() {
		normalFrame.setPinsDown(1, 1).setPinsDown(2, 0);
		assertEquals("Invalid rolls count : ", 2, normalFrame.countRolls());
	}
	
	/**
	 * Test NormalFrame.countRoll() return value to be 2 after one setPinsDown(1,0) and one setPinsDown(2,0)
	 */
	@Test
	public void testNormalFrame_countRolls_afterTwoGutters_returns2() {
		normalFrame.setPinsDown(1, 0).setPinsDown(2, 0);
		assertEquals("Invalid rolls count : ", 2, normalFrame.countRolls());
	}
	
	//----------------------------------- NormalFrame.getFrameNumber() -----------------------------------
	
	/**
	 * Test the result of the method NormalFrame.getFrameNumber() for frame 1 to 9
	 */
	@Test
	public void testNormalFrame_getFrameNumber_returnsInitializationValue() {
		int begin = 1, end = 9;
		
		int[] expected = new int[end - begin + 1];
		int[] result = new int[expected.length];
		
		for(int i = begin; i <= end; i++) {
			expected[i-begin] = i;
			result[i-begin] = new NormalFrame(i).getFrameNumber();
		}
		
		assertArrayEquals("Results don't match with expected values : ", expected, result);
	}
	
	//--------------------------------- NormalFrame.setPinDown(int, int) ---------------------------------
	
	/**
	 * Test that setting some pins down in an invalid order (2nd throw before 1st) throws a BowlingException 
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_whenWrongRollOrder_throwsBowlingException() {
		normalFrame.setPinsDown(2, 1).setPinsDown(1, 1);
	}
	
	/**
	 * Test that setting 0 pins down in an invalid order (2nd throw before 1st) throws a BowlingException 
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_whenWrongRollOrderWithGutterRolls_throwsBowlingException() {
		normalFrame.setPinsDown(2, 0).setPinsDown(1, 0);
	}
	
	/**
	 * Test that trying to set pins down for a negative roll value throws a BowlingException 
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_withNegativeRollValueMinus1_throwsBowlingException()
	{
		normalFrame.setPinsDown(-1, 1);
	}
	
	/**
	 * Test that trying to set pins down for a 0th roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_withIllegalRollValue0_throwsBowlingException()
	{
		normalFrame.setPinsDown(0, 1);
	}
	
	/**
	 * Test that trying to set pins down for a 3rd roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_withIllegalRollValue3_throwsBowlingException()
	{
		normalFrame.setPinsDown(3, 1);
	}
	
	/**
	 * Test that setting a negative number of pins down on 1st roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_withNegativePinsValueOnFirstRollMinus1_throwsBowlingException()
	{
		normalFrame.setPinsDown(1, -1);
	}

	/**
	 * Test that setting a negative number of pins down on 2nd roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_withNegativePinsValueOnSecondRollMinus1_throwsBowlingException()
	{
		normalFrame.setPinsDown(1, 5);
		normalFrame.setPinsDown(2, -1);
	}
	
	/**
	 * Test that setting an invalid number of pins down (11) in one roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_withIllegalPinsValueInOneRol11_throwsBowlingException()
	{
		normalFrame.setPinsDown(1, 11);
	}
	
	/**
	 * Test that setting an invalid number of pins down (11) in two rolls throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_withInvalidPinsValueInTwoRolls11_throwsBowlingException()
	{
		normalFrame.setPinsDown(1, 5).setPinsDown(2, 6);
	}
	
	/**
	 * Test that calling setPinsDown twice for the same roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_setPinsDown_whenCalledTwiceWithSameRollValue_throwsBowlingException()
	{
		normalFrame.setPinsDown(1, 1).setPinsDown(1, 1);
	}
	
	/**
	 * Test that when setPinsDown throws, the frame is left untouched
	 */
	@Test
	public void testNormaltFrame_setPinsDown_whenBowlingExceptionThrownFromWrongCallOrder_frameIsUnchanged()
	{
		try
		{
			normalFrame.setPinsDown(2, 2);	
		}
		catch (BowlingException e)
		{
			assertEquals(-1, normalFrame.getPinsDown(2));
			return;
		}
		fail("Calling setPinsDown(2, _) before setPinsDown(1, _) should throw BowlingException, but it did not.");
	}
	
	/**
	 * Test that when setPinsDown throws, the frame is left untouched
	 */
	@Test
	public void testNormalFrame_setPinsDown_whenBowlingExceptionThrown_frameIsUnchanged()
	{
		normalFrame.setPinsDown(1, 1);
		try
		{
			normalFrame.setPinsDown(1, 2);	
		}
		catch (BowlingException e)
		{
			assertEquals(1, normalFrame.getPinsDown(1));
			return;
		}
		fail("Calling setPinsDown twice for the same roll should throw BowlingException, but it did not.");
	}
	
	//------------------------------------ NormalFrame.countPinsDown() -----------------------------------
	
	/**
	 * Test that NormalFrame.countPinsDown() returns 0 after initialization 
	 */
	@Test
	public void testNormalFrame_countPinsDown_onNewFrame_returns0() {
		assertEquals("Invalid pins down count : ", 0, normalFrame.countPinsDown());
	}

	/**
	 * Test that NormalFrame.countPinsDown() returns 0 after a call to reset() 
	 */
	@Test
	public void testNormalFrame_countPinsDown_afterReset_returns0() {
		normalFrame.setPinsDown(1, 1);
		normalFrame.reset();
		assertEquals("Invalid pins down count : ", 0, normalFrame.countPinsDown());
	}
	
	/**
	 * Test that NormalFrame.countPinsDown() returns 6 after scoring 3+3
	 */
	@Test
	public void testNormalFrame_countPinsDown_after3DownAnd3DownRolls_returns6()
	{
		normalFrame.setPinsDown(1, 3).setPinsDown(2, 3);
		assertEquals("Invalid pins down count : ", 6, normalFrame.countPinsDown());
	}
	
	/**
	 * Test that NormalFrame.countPinsDown() returns 3 after scoring 3+0
	 */
	@Test
	public void testNormalFrame_countPinsDown_after3DownAndGutterRolls_returns3()
	{
		normalFrame.setPinsDown(1, 3).setPinsDown(2, 0);
		assertEquals("Invalid pins down count : ", 3, normalFrame.countPinsDown());
	}
	
	/**
	 * Test that NormalFrame.countPinsDown() returns 3 after scoring 0+3
	 */
	@Test
	public void testNormalFrame_countPinsDown_afterGutterAnd3DownRolls_returns6()
	{
		normalFrame.setPinsDown(1, 0).setPinsDown(2, 3);
		assertEquals("Invalid pins down count : ", 3, normalFrame.countPinsDown());
	}
	
	/**
	 * Test that NormalFrame.countPinsDown() returns 10 after scoring a strike
	 */
	@Test
	public void testNormalFrame_countPinsDown_onStrike_returns10()
	{
		normalFrame.setPinsDown(1, 10);
		assertEquals("Invalid pins down count : ", 10, normalFrame.countPinsDown());
	}
	
	/**
	 * Test that NormalFrame.countPinsDown() returns 10 after scoring a spare (5+5)
	 */
	@Test
	public void testNormalFrame_countPinsDown_onSpare_returns10()
	{
		normalFrame.setPinsDown(1, 5).setPinsDown(2, 5);
		assertEquals("Invalid pins down count : ", 10, normalFrame.countPinsDown());
	}
	
	/**
	 * Test that NormalFrame.countPinsDown() returns 10 after scoring a spare with a gutter (0+10)
	 */
	@Test
	public void testNormalFrame_countPinsDown_onSpareAfterGutter_returns10()
	{
		normalFrame.setPinsDown(1, 0).setPinsDown(2, 10);
		assertEquals("Invalid pins down count : ", 10, normalFrame.countPinsDown());
	}
	
	//------------------------------------ NormalFrame.getPinsDown(int) ----------------------------------
	
	/**
	 * Test that NormalFrame.getPinsDown(-1) throws BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_getPinsDown_withNegativeRollIndexMinus1_throwsBowlingException()
	{
		normalFrame.getPinsDown(-1);
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(0) throws BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_getPinsDown_withZeroRollIndex_throwsBowlingException()
	{
		normalFrame.getPinsDown(0);
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(3) throws BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testNormalFrame_getPinsDown_withIllegalRollIndex3_throwsBowlingException()
	{
		normalFrame.getPinsDown(3);
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(1) returns -1 when no roll have been registered
	 */
	@Test
	public void testNormalFrame_getPinsDown_withInvalidRollIndex1_onNewFrame_returnsMinus1()
	{
		assertEquals("Invalid result : ", -1, normalFrame.getPinsDown(1));
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(1) returns -1 when the frame have been reset
	 */
	@Test
	public void testNormalFrame_getPinsDown_withInvalidRollIndex1_afterReset_returnsMinus1()
	{
		normalFrame.setPinsDown(1, 1).reset();
		assertEquals("Invalid result : ", -1, normalFrame.getPinsDown(1));
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(2) returns -1 when only one roll have been registered
	 */
	@Test
	public void testNormalFrame_getPinsDown_withInvalidRollIndex2_afterOneRoll_returnsMinus1()
	{
		normalFrame.setPinsDown(1, 1);
		assertEquals("Invalid result : ", -1, normalFrame.getPinsDown(2));
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(1) returns 0 after a 1-down on 1st roll
	 */
	@Test
	public void testNormalFrame_getPinsDown_withValidRollValue1_after1DownOnFirstRoll_returns1()
	{
		normalFrame.setPinsDown(1, 1);
		assertEquals("Invalid result : ", 1, normalFrame.getPinsDown(1));
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(1) returns 0 after a 1-down on 2nd roll
	 */
	@Test
	public void testNormalFrame_getPinsDown_withValidRollValue1_after1DownOnSecondRoll_returns0()
	{
		normalFrame.setPinsDown(1, 5).setPinsDown(2, 1);
		assertEquals("Invalid result : ", 1, normalFrame.getPinsDown(2));
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(1) returns 0 after a gutter on 1st roll
	 */
	@Test
	public void testNormalFrame_getPinsDown_withValidRollValue1_afterGutterOnFirstRoll_returns0()
	{
		normalFrame.setPinsDown(1, 0);
		assertEquals("Invalid result : ", 0, normalFrame.getPinsDown(1));
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(1) returns 0 after a gutter on 2nd roll
	 */
	@Test
	public void testNormalFrame_getPinsDown_withValidRollValue1_afterGutterOnSecondRoll_returns0()
	{
		normalFrame.setPinsDown(1, 5).setPinsDown(2, 0);
		assertEquals("Invalid result : ", 0, normalFrame.getPinsDown(2));
	}

	/**
	 * Test that NormalFrame.getPinsDown(1) returns 10 after a strike
	 */
	@Test
	public void testNormalFrame_getPinsDown_withValidRollValue1_afterStrike_returns10()
	{
		normalFrame.setPinsDown(1, 10);
		assertEquals("Invalid result : ", 10, normalFrame.getPinsDown(1));
	}
	
	/**
	 * Test that NormalFrame.getPinsDown(2) returns 0 after a strike
	 */
	@Test
	public void testNormalFrame_getPinsDown_withValidRollValue2_afterStrike_returns0()
	{
		normalFrame.setPinsDown(1, 10);
		assertEquals("Invalid result : ", 0, normalFrame.getPinsDown(2));
	}
	
	//-------------------------------------- NormalFrame.toString() --------------------------------------

	/**
	 * Test that NormalFrame.toString() returns "  " right after initialization
	 */
	@Test
	public void testNormalFrame_toString_onNewFrame_returnsStringSpaceSpace()
	{
		assertEquals("Invalid result : ", "  ", normalFrame.toString());
	}

	/**
	 * Test that NormalFrame.toString() returns "  " right after initialization
	 */
	@Test
	public void testNormalFrame_toString_afterReset_returnsStringSpaceSpace()
	{
		normalFrame.setPinsDown(1, 1).reset();
		assertEquals("Invalid result : ", "  ", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "3 " after scoring a 3 in one roll
	 */
	@Test
	public void testNormalFrame_toString_with3DownInOneRoll_returnsString3Space()
	{
		normalFrame.setPinsDown(1, 3);
		assertEquals("Invalid result : ", "3 ", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "- " after a gutter roll in one roll
	 */
	@Test
	public void testNormalFrame_toString_withGutterInOneRoll_returnsStringDashSpace()
	{
		normalFrame.setPinsDown(1, 0);
		assertEquals("Invalid result : ", "- ", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "33" after scoring a 3+3
	 */
	@Test
	public void testNormalFrame_toString_with3DownAnd3DownRolls_returnsString33()
	{
		normalFrame.setPinsDown(1, 3).setPinsDown(2, 3);
		assertEquals("Invalid result : ", "33", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "3-" after scoring 3+0
	 */
	@Test
	public void testNormalFrame_toString_with3DownAndGutterRolls_returnsString3Dash()
	{
		normalFrame.setPinsDown(1, 3).setPinsDown(2, 0);
		assertEquals("Invalid result : ", "3-", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "-3" after scoring 0+3
	 */
	@Test
	public void testNormalFrame_toString_withGutterAnd3DownRolls_returnsStringDash3()
	{
		normalFrame.setPinsDown(1, 0).setPinsDown(2, 3);
		assertEquals("Invalid result : ", "-3", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "5/" after scoring 5+5 (spare)
	 */
	@Test
	public void testNormalFrame_toString_withSpare_returnsString5Slash()
	{
		normalFrame.setPinsDown(1, 5).setPinsDown(2, 5);
		assertEquals("Invalid result : ", "5/", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "-/" after gutter into spare (0+10)
	 */
	@Test
	public void testNormalFrame_toString_withSpareAfterGutter_returnsStringDashSlash()
	{
		normalFrame.setPinsDown(1, 0).setPinsDown(2, 10);
		assertEquals("Invalid result : ", "-/", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "X " after a strike
	 */
	@Test
	public void testNormalFrame_toString_withStrike_returnsStringXSpace()
	{
		normalFrame.setPinsDown(1, 10);
		assertEquals("Invalid result : ", "X ", normalFrame.toString());
	}
	
	/**
	 * Test that NormalFrame.toString() returns "--" after double gutter rolls
	 */
	@Test
	public void testNormalFrame_toString_withTwoGutterRolls_returnsStringDashDash()
	{
		normalFrame.setPinsDown(1, 0).setPinsDown(2, 0);
		assertEquals("Invalid result : ", "--", normalFrame.toString());
	}
	
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
	public void testLastFrame_Constructor_withValidValue10_noException() {
		new LastFrame(10);
	}

	/**
	 * Test that new LastFrame(0) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_Constructor_withZeroValue_throwsBowlingException() {
		new LastFrame(0);
	}

	/**
	 * Test that new LastFrame(-5) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_Constructor_withNegativeValueMinus5_throwsBowlingException() {
		new LastFrame(-5);
	}

	/**
	 * Test that new LastFrame(9) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_Constructor_withIllegalValue9_throwsBowlingException() {
		new LastFrame(9);
	}

	/**
	 * Test that new LastFrame(11) throws a BowlingException.
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_Constructor_withIllegalValue11_throwsBowlingException() {
		new LastFrame(11);
	}

	//-------------------------------------- LastFrame.countRolls() --------------------------------------
	
	/**
	 * Test LastFrame.countRoll() return value to be 0 at initialization 
	 */
	@Test
	public void testLastFrame_countRolls_onNewFrame_returns0() {
		assertEquals("Invalid rolls count : ",   0, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 0 after reset 
	 */
	@Test
	public void testLastFrame_countRolls_afterReset_returns0() {
		lastFrame.setPinsDown(1, 1);
		lastFrame.reset();
		assertEquals("Invalid rolls count : ",   0, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 1 after one setPinsDown(1,1)
	 */
	@Test
	public void testLastFrame_countRolls_afterOneRoll_returns1() {
		lastFrame.setPinsDown(1, 1);
		assertEquals("Invalid rolls count : ", 1, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 2 after two setPinsDown(1:2, 1) 
	 */
	@Test
	public void testLastFrame_countRolls_afterTwoRolls_returns2() {
		lastFrame.setPinsDown(1, 1).setPinsDown(2, 1);
		assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 1 after one setPinsDown(1,0)
	 */
	@Test
	public void testLastFrame_countRolls_afterGutterOnFirstRoll_returns1() {
		lastFrame.setPinsDown(1, 0);
		assertEquals("Invalid rolls count : ", 1, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 2 after one setPinsDown(1,1) and one setPinsDown(2,0)
	 */
	@Test
	public void testLastFrame_countRolls_afterGutterOnSecondRoll_returns2() {
		lastFrame.setPinsDown(1, 1).setPinsDown(2, 0);
		assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 2 after one setPinsDown(1,0) and one setPinsDown(2,0)
	 */
	@Test
	public void testLastFrame_countRolls_afterTwoGutters_returns2() {
		lastFrame.setPinsDown(1, 0).setPinsDown(2, 0);
		assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 3 after three setPinsDown(1:3, 5) (allowed by a spare)
	 */
	@Test
	public void testLastFrame_countRolls_afterThreeRolls_returns3() {
		lastFrame.setPinsDown(1, 5);
		lastFrame.setPinsDown(2, 5);
		lastFrame.setPinsDown(3, 5);
		assertEquals("Invalid rolls count : ", 3, lastFrame.countRolls());
	}
	
	/**
	 * Test LastFrame.countRoll() return value to be 3 after two setPinsDown(1:2,5) and one setPinsDown(3,0)
	 */
	@Test
	public void testLastFrame_countRolls_afterGutterOnThirdRoll_returns3() {
		lastFrame.setPinsDown(1, 5).setPinsDown(2, 5).setPinsDown(3, 0);
		assertEquals("Invalid rolls count : ", 3, lastFrame.countRolls());
	}

	//----------------------------------- NormalFrame.getFrameNumber() -----------------------------------
	
	/**
	 * Test the result of the method LastFrame.getFrameNumber() for frame 10
	 */
	@Test
	public void testLastFrame_getFrameNumber_returnsInitializationValue() {
		assertEquals("Invalid frame number : ", 10, new LastFrame(10).getFrameNumber());
	}
	
	//-------------------------------------- LastFrame.setPinDown() --------------------------------------
	
	/**
	 * Test that setting some pins down in an invalid order (2nd throw before 1st) throws a BowlingException 
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_whenWrongRollOrder_throwsBowlingException() {
		lastFrame.setPinsDown(3, 1).setPinsDown(2, 5).setPinsDown(1, 5);
	}
	
	/**
	 * Test that setting 0 pins down in an invalid order (2nd throw before 1st) throws a BowlingException 
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_whenWrongRollOrderWithGutterRolls_throwsBowlingException() {
		lastFrame.setPinsDown(3, 0).setPinsDown(2, 10).setPinsDown(1, 0);
	}
	
	/**
	 * Test that trying to set pins down for a negative roll value throws a BowlingException 
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_withNegativeRollValueMinus1_throwsBowlingException()
	{
		lastFrame.setPinsDown(-1, 1);
	}
	
	/**
	 * Test that trying to set pins down for a 0th roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_withIllegalRollValue0_throwsBowlingException()
	{
		lastFrame.setPinsDown(0, 1);
	}
	
	/**
	 * Test that trying to set pins down for a 3rd roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_withIllegalRollValue3_throwsBowlingException()
	{
		lastFrame.setPinsDown(3, 1);
	}
	
	/**
	 * Test that setting a negative number of pins down on 1st roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_withNegativePinsValueOnFirstRollMinus1_throwsBowlingException()
	{
		lastFrame.setPinsDown(1, -1);
	}

	/**
	 * Test that setting a negative number of pins down on 2nd roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_withNegativePinsValueOnSecondRollMinus1_throwsBowlingException()
	{
		lastFrame.setPinsDown(1, 5);
		lastFrame.setPinsDown(2, -1);
	}

	/**
	 * Test that setting a negative number of pins down on 3rd roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_withNegativePinsValueOnThirdRollMinus1_throwsBowlingException()
	{
		lastFrame.setPinsDown(1, 5);
		lastFrame.setPinsDown(2, 5);
		lastFrame.setPinsDown(3, -1);
	}
	
	/**
	 * Test that setting an invalid number of pins down (11) in one roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_withIllegalPinsValueInOneRol11_throwsBowlingException()
	{
		lastFrame.setPinsDown(1, 11);
	}
	
	/**
	 * Test that setting an invalid number of pins down (11) in two rolls throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_withInvalidPinsValueInTwoRolls11_throwsBowlingException()
	{
		lastFrame.setPinsDown(1, 5).setPinsDown(2, 6);
	}
	
	/**
	 * Test that calling setPinsDown twice for the same roll throws a BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_setPinsDown_whenCalledTwiceWithSameRollValue_throwsBowlingException()
	{
		lastFrame.setPinsDown(1, 1).setPinsDown(1, 1);
	}
	
	/**
	 * Test that when setPinsDown throws, the frame is left untouched
	 */
	@Test
	public void testLastFrame_setPinsDown_whenBowlingExceptionThrownFromWrongCallOrder_frameIsUnchanged()
	{
		try
		{
			lastFrame.setPinsDown(2, 2);	
		}
		catch (BowlingException e)
		{
			assertEquals(-1, lastFrame.getPinsDown(2));
			return;
		}
		fail("Calling setPinsDown(2, _) before setPinsDown(1, _) should throw BowlingException, but it did not.");
	}
	
	/**
	 * Test that when setPinsDown throws, the frame is left untouched
	 */
	@Test
	public void testLastFrame_setPinsDown_whenBowlingExceptionThrownFromDuplicateCall_frameIsUnchanged()
	{
		lastFrame.setPinsDown(1, 1);
		try
		{
			lastFrame.setPinsDown(1, 2);	
		}
		catch (BowlingException e)
		{
			assertEquals(1, lastFrame.getPinsDown(1));
			return;
		}
		fail("Calling setPinsDown twice for the same roll should throw BowlingException, but it did not.");
	}
	
	//------------------------------------- LastFrame.countPinsDown() ------------------------------------

	/**
	 * Test that NormalFrame.countPinsDown() returns 0 after initialization 
	 */
	@Test
	public void testLastFrame_countPinsDown_onNewFrame_returns0() {
		assertEquals("Invalid pins down count : ", 0, lastFrame.countPinsDown());
	}

	/**
	 * Test that LastFrame.countPinsDown() returns 0 after a call to reset() 
	 */
	@Test
	public void testLastFrame_countPinsDown_afterReset_returns0() {
		lastFrame.setPinsDown(1, 1);
		lastFrame.reset();
		assertEquals("Invalid pins down count : ", 0, lastFrame.countPinsDown());
	}
	
	/**
	 * Test that LastFrame.countPinsDown() returns 6 after scoring 3+3
	 */
	@Test
	public void testLastFrame_countPinsDown_after3DownAnd3DownRolls_returns6()
	{
		lastFrame.setPinsDown(1, 3).setPinsDown(2, 3);
		assertEquals("Invalid pins down count : ", 6, lastFrame.countPinsDown());
	}
	
	/**
	 * Test that LastFrame.countPinsDown() returns 3 after scoring 3+0
	 */
	@Test
	public void testLastFrame_countPinsDown_after3DownAndGutterRolls_returns3()
	{
		lastFrame.setPinsDown(1, 3).setPinsDown(2, 0);
		assertEquals("Invalid pins down count : ", 3, lastFrame.countPinsDown());
	}
	
	/**
	 * Test that LastFrame.countPinsDown() returns 3 after scoring 0+3
	 */
	@Test
	public void testLastFrame_countPinsDown_afterGutterAnd3DownRolls_returns6()
	{
		lastFrame.setPinsDown(1, 0).setPinsDown(2, 3);
		assertEquals("Invalid pins down count : ", 3, lastFrame.countPinsDown());
	}
	
	/**
	 * Test that LastFrame.countPinsDown() returns 10 after scoring a strike
	 */
	@Test
	public void testLastFrame_countPinsDown_onStrike_returns10()
	{
		lastFrame.setPinsDown(1, 10);
		assertEquals("Invalid pins down count : ", 10, lastFrame.countPinsDown());
	}
	
	/**
	 * Test that LastFrame.countPinsDown() returns 10 after scoring a spare (5+5)
	 */
	@Test
	public void testLastFrame_countPinsDown_onSpare_returns10()
	{
		lastFrame.setPinsDown(1, 5).setPinsDown(2, 5);
		assertEquals("Invalid pins down count : ", 10, lastFrame.countPinsDown());
	}
	
	/**
	 * Test that LastFrame.countPinsDown() returns 10 after scoring a spare with a gutter (0+10)
	 */
	@Test
	public void testLastFrame_countPinsDown_onSpareAfterGutter_returns10()
	{
		lastFrame.setPinsDown(1, 0).setPinsDown(2, 10);
		assertEquals("Invalid pins down count : ", 10, lastFrame.countPinsDown());
	}
	
	//------------------------------------- LastFrame.getPinDown(int) ------------------------------------

	/**
	 * Test that LastFrame.getPinsDown(-1) throws BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_getPinsDown_withNegativeRollIndexMinus1_throwsBowlingException()
	{
		lastFrame.getPinsDown(-1);
	}
	
	/**
	 * Test that LastFrame.getPinsDown(0) throws BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_getPinsDown_withZeroRollIndex_throwsBowlingException()
	{
		lastFrame.getPinsDown(0);
	}
	
	/**
	 * Test that LastFrame.getPinsDown(3) throws BowlingException
	 */
	@Test (expected = BowlingException.class)
	public void testLastFrame_getPinsDown_withIllegalRollIndex3_throwsBowlingException()
	{
		lastFrame.getPinsDown(3);
	}
	
	/**
	 * Test that LastFrame.getPinsDown(1) returns -1 when no roll have been registered
	 */
	@Test
	public void testLastFrame_getPinsDown_withInvalidRollIndex1_onNewFrame_returnsMinus1()
	{
		assertEquals("Invalid result : ", -1, lastFrame.getPinsDown(1));
	}
	
	/**
	 * Test that LastFrame.getPinsDown(1) returns -1 when the frame have been reset
	 */
	@Test
	public void testLastFrame_getPinsDown_withInvalidRollIndex1_afterReset_returnsMinus1()
	{
		lastFrame.setPinsDown(1, 1).reset();
		assertEquals("Invalid result : ", -1, lastFrame.getPinsDown(1));
	}
	
	/**
	 * Test that LastFrame.getPinsDown(2) returns -1 when only one roll have been registered
	 */
	@Test
	public void testLastFrame_getPinsDown_withInvalidRollIndex2_afterOneRoll_returnsMinus1()
	{
		lastFrame.setPinsDown(1, 1);
		assertEquals("Invalid result : ", -1, lastFrame.getPinsDown(2));
	}
	
	/**
	 * Test that LastFrame.getPinsDown(1) returns 0 after a 1-down on 1st roll
	 */
	@Test
	public void testLastFrame_getPinsDown_withValidRollValue1_after1DownOnFirstRoll_returns1()
	{
		lastFrame.setPinsDown(1, 1);
		assertEquals("Invalid result : ", 1, lastFrame.getPinsDown(1));
	}
	
	/**
	 * Test that LastFrame.getPinsDown(1) returns 0 after a 1-down on 2nd roll
	 */
	@Test
	public void testLastFrame_getPinsDown_withValidRollValue1_after1DownOnSecondRoll_returns0()
	{
		lastFrame.setPinsDown(1, 5).setPinsDown(2, 1);
		assertEquals("Invalid result : ", 1, lastFrame.getPinsDown(2));
	}
	
	/**
	 * Test that LastFrame.getPinsDown(1) returns 0 after a gutter on 1st roll
	 */
	@Test
	public void testLastFrame_getPinsDown_withValidRollValue1_afterGutterOnFirstRoll_returns0()
	{
		lastFrame.setPinsDown(1, 0);
		assertEquals("Invalid result : ", 0, lastFrame.getPinsDown(1));
	}
	
	/**
	 * Test that LastFrame.getPinsDown(1) returns 0 after a gutter on 2nd roll
	 */
	@Test
	public void testLastFrame_getPinsDown_withValidRollValue1_afterGutterOnSecondRoll_returns0()
	{
		lastFrame.setPinsDown(1, 5).setPinsDown(2, 0);
		assertEquals("Invalid result : ", 0, lastFrame.getPinsDown(2));
	}

	/**
	 * Test that LastFrame.getPinsDown(1) returns 10 after a strike
	 */
	@Test
	public void testLastFrame_getPinsDown_withValidRollValue1_afterStrike_returns10()
	{
		lastFrame.setPinsDown(1, 10);
		assertEquals("Invalid result : ", 10, lastFrame.getPinsDown(1));
	}
	
	/**
	 * Test that LastFrame.getPinsDown(2) returns 0 after a strike
	 */
	@Test
	public void testLastFrame_getPinsDown_withValidRollValue2_afterStrike_returns0()
	{
		lastFrame.setPinsDown(1, 10);
		assertEquals("Invalid result : ", 0, lastFrame.getPinsDown(2));
	}
	
	//-------------------------------------- LastFrame.toString() --------------------------------------

	/**
	 * Test that LastFrame.toString() returns "  " right after initialization
	 */
	@Test
	public void testLastFrame_toString_onNewFrame_returnsStringSpaceSpace()
	{
		assertEquals("Invalid result : ", "  ", lastFrame.toString());
	}

	/**
	 * Test that LastFrame.toString() returns "  " right after initialization
	 */
	@Test
	public void testLastFrame_toString_afterReset_returnsStringSpaceSpace()
	{
		lastFrame.setPinsDown(1, 1).reset();
		assertEquals("Invalid result : ", "  ", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "3 " after scoring a 3 in one roll
	 */
	@Test
	public void testLastFrame_toString_with3DownInOneRoll_returnsString3Space()
	{
		lastFrame.setPinsDown(1, 3);
		assertEquals("Invalid result : ", "3 ", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "- " after a gutter roll in one roll
	 */
	@Test
	public void testLastFrame_toString_withGutterInOneRoll_returnsStringDashSpace()
	{
		lastFrame.setPinsDown(1, 0);
		assertEquals("Invalid result : ", "- ", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "33" after scoring a 3+3
	 */
	@Test
	public void testLastFrame_toString_with3DownAnd3DownRolls_returnsString33()
	{
		lastFrame.setPinsDown(1, 3).setPinsDown(2, 3);
		assertEquals("Invalid result : ", "33", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "3-" after scoring 3+0
	 */
	@Test
	public void testLastFrame_toString_with3DownAndGutterRolls_returnsString3Dash()
	{
		lastFrame.setPinsDown(1, 3).setPinsDown(2, 0);
		assertEquals("Invalid result : ", "3-", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "-3" after scoring 0+3
	 */
	@Test
	public void testLastFrame_toString_withGutterAnd3DownRolls_returnsStringDash3()
	{
		lastFrame.setPinsDown(1, 0).setPinsDown(2, 3);
		assertEquals("Invalid result : ", "-3", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "5/" after scoring 5+5 (spare)
	 */
	@Test
	public void testLastFrame_toString_withSpare_returnsString5Slash()
	{
		lastFrame.setPinsDown(1, 5).setPinsDown(2, 5);
		assertEquals("Invalid result : ", "5/", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "-/" after gutter into spare (0+10)
	 */
	@Test
	public void testLastFrame_toString_withSpareAfterGutter_returnsStringDashSlash()
	{
		lastFrame.setPinsDown(1, 0).setPinsDown(2, 10);
		assertEquals("Invalid result : ", "-/", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "X " after a strike
	 */
	@Test
	public void testLastFrame_toString_withStrike_returnsStringXSpace()
	{
		lastFrame.setPinsDown(1, 10);
		assertEquals("Invalid result : ", "X ", lastFrame.toString());
	}
	
	/**
	 * Test that LastFrame.toString() returns "--" after double gutter rolls
	 */
	@Test
	public void testLastFrame_toString_withTwoGutterRolls_returnsStringDashDash()
	{
		lastFrame.setPinsDown(1, 0).setPinsDown(2, 0);
		assertEquals("Invalid result : ", "--", lastFrame.toString());
	}
	
	//----------------------------------------------------------------------------------------------------
	//----------------------------------------------- Game -----------------------------------------------
	//----------------------------------------------------------------------------------------------------
	
}
