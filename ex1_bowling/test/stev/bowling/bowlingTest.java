package stev.bowling;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.util.Arrays;

import org.junit.*;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Enclosed.class)
public class bowlingTest
{
	public static class FrameTest {

		public Frame normalFrame;
		public Frame lastFrame;
		public Game game;
	
		@Before
		public void setUp() throws Exception 
		{
			normalFrame = new NormalFrame(1);
			lastFrame   = new LastFrame(10);
			game = new Game();
			for (int i=1; i<10; i++) {
				game.addFrame(new NormalFrame(i));
			}
			game.addFrame(new LastFrame(10));	
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
		 * Test that NormalFrame.getPinsDown(1)
		 */
		@Test
		public void testNormalFrame_getPinsDown_withInvalidRollIndex1_onNewFrame_returnsMinus1()
		{
			assertEquals("Invalid result : ", -1, normalFrame.getPinsDown(1));
		}
		
		/**
		 * Test that NormalFrame.getPinsDown(1)
		 */
		@Test
		public void testNormalFrame_getPinsDown_withInvalidRollIndex2_afterOneRoll_returnsMinus1()
		{
			normalFrame.setPinsDown(1, 1);
			assertEquals("Invalid result : ", -1, normalFrame.getPinsDown(2));
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
		public void testLastFrame_countRolls_onNewFrame() {
			assertEquals("Invalid rolls count : ",   0, lastFrame.countRolls());
		}
		
		/**
		 * Test LastFrame.countRoll() return value to be 0 after reset 
		 */
		@Test
		public void testLastFrame_countRolls_afterReset() {
			lastFrame.setPinsDown(1, 1);
			lastFrame.reset();
			assertEquals("Invalid rolls count : ",   0, lastFrame.countRolls());
		}
		
		/**
		 * Test LastFrame.countRoll() return value to be 1 after one setPinsDown(1,1)
		 */
		@Test
		public void testLastFrame_countRolls_afterOneRoll() {
			lastFrame.setPinsDown(1, 1);
			assertEquals("Invalid rolls count : ", 1, lastFrame.countRolls());
		}
		
		/**
		 * Test LastFrame.countRoll() return value to be 2 after two setPinsDown(1:2, 1) 
		 */
		@Test
		public void testLastFrame_countRolls_afterTwoRolls() {
			lastFrame.setPinsDown(1, 1).setPinsDown(2, 1);
			assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
		}
		
		/**
		 * Test LastFrame.countRoll() return value to be 1 after one setPinsDown(1,0)
		 */
		@Test
		public void testLastFrame_countRolls_afterGutterOnFirstRoll() {
			lastFrame.setPinsDown(1, 0);
			assertEquals("Invalid rolls count : ", 1, lastFrame.countRolls());
		}
		
		/**
		 * Test LastFrame.countRoll() return value to be 2 after one setPinsDown(1,1) and one setPinsDown(2,0)
		 */
		@Test
		public void testLastFrame_countRolls_afterGutterOnSecondRoll() {
			lastFrame.setPinsDown(1, 1).setPinsDown(2, 0);
			assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
		}
		
		/**
		 * Test LastFrame.countRoll() return value to be 2 after one setPinsDown(1,0) and one setPinsDown(2,0)
		 */
		@Test
		public void testLastFrame_countRolls_afterTwoGutters() {
			lastFrame.setPinsDown(1, 0).setPinsDown(2, 0);
			assertEquals("Invalid rolls count : ", 2, lastFrame.countRolls());
		}
		
		/**
		 * Test LastFrame.countRoll() return value to be 3 after three setPinsDown(1:3, 5) (allowed by a spare)
		 */
		@Test
		public void testLastFrame_countRolls_afterThreeRolls() {
			lastFrame.setPinsDown(1, 5);
			lastFrame.setPinsDown(2, 5);
			lastFrame.setPinsDown(3, 5);
			assertEquals("Invalid rolls count : ", 3, lastFrame.countRolls());
		}
		
		/**
		 * Test LastFrame.countRoll() return value to be 3 after two setPinsDown(1:2,5) and one setPinsDown(3,0)
		 */
		@Test
		public void testLastFrame_countRolls_afterGutterOnThirdRoll() {
			lastFrame.setPinsDown(1, 5).setPinsDown(2, 5).setPinsDown(3, 0);
			assertEquals("Invalid rolls count : ", 3, lastFrame.countRolls());
		}

		
		//-------------------------------------- LastFrame.setPinDown() --------------------------------------
		
		//------------------------------------- LastFrame.countPinsDown() ------------------------------------
		
		//------------------------------------- LastFrame.getPinDown(int) ------------------------------------
		
		//----------------------------------------------------------------------------------------------------
		//----------------------------------------------- Game -----------------------------------------------
		//----------------------------------------------------------------------------------------------------
		
		
		/**
		 * Test odd game initialization (full NormalFrame)
		 */
		@Test (expected = BowlingException.class)
		public void testGame_CreationFullNormalFrame() {
			Game g = new Game();
			for (int i=1; i<11; i++) {
				g.addFrame(new NormalFrame(i));
			}			
		}
		
		/**
		 * Test odd game initialization (full LastFrame)
		 */
		@Test (expected = BowlingException.class)
		public void testGame_CreationFullLastFrame() {
			Game g = new Game();
			for (int i=1; i<11; i++) {
				g.addFrame(new NormalFrame(10));
			}			
		}
		
		/**
		 * Test getCumulativeScore return value (0) for empty game first frame
		 */
		@Test
		public void testGame_CumulativeScoreEmpty() {
			assertEquals(0, game.getCumulativeScore(1));
		}
		
		
		
	}
	
	/**
	 * Test class to use parameters to help test the getFrameNumber method
	 */
	@RunWith(Parameterized.class)
	public static class ParameterizedTest_getFrameNumber {
		
		public Frame frame;
		public int expected_num;
		
		public ParameterizedTest_getFrameNumber(int i) {
			if (i == 10)
				frame = new LastFrame(10);
			else
				frame = new NormalFrame(i);
			expected_num = i;
		}
		
		@Parameters
		public static Iterable<Object[]> data() throws Throwable {
			return Arrays.asList(new Object[][] {
				{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}
			});
		}
		
		/**
		 * Test the result of the method Frame.getFrameNumber() for frame 1 to 10
		 * (frame 1-9 are NormalFrame, frame 10 is LastFrame)
		 */		
		@Test
		public void test() {
			assertEquals(expected_num, frame.getFrameNumber());
		}
		
		
	}
}
