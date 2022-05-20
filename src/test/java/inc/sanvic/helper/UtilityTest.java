package inc.sanvic.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.exception.NullOrEmptyStringException;
@SpringBootTest
class UtilityTest {
	@InjectMocks
	Utility utility;

	@Test
	void shouldInitializeWholeArrayWithZero() {
		final BigDecimal[] testArray = new BigDecimal[5];
		final BigDecimal expectedValue = BigDecimal.ZERO;

		final BigDecimal[] returnedArray = utility.initializeArrayWithZeros(testArray);

		assertEquals(expectedValue, returnedArray[2]);
	}

	@Test
	void shouldInitializeWhole2DArrayWithZero() {
		final BigDecimal[][] testArray = new BigDecimal[2][2];
		final BigDecimal expectedValue = BigDecimal.ZERO;

		final BigDecimal[][] returnedArray = utility.initialize2DArrayWithZeros(testArray);

		assertEquals(expectedValue, returnedArray[1][0]);
	}

	@Test
	void shouldReturnIndexOfMinimumValueInTheGivenArray() {
		final BigDecimal[] testArray = { BigDecimal.TEN, BigDecimal.valueOf(203.5), BigDecimal.valueOf(502.5), BigDecimal.ONE, BigDecimal.valueOf(452.3) };
		final Integer expectedValue = 3;

		final Integer actualValue = utility.getIndexOfMinimumValue(testArray);

		assertEquals(expectedValue, actualValue);
	}

	@Test
	void shouldReturnIndexOfMaximumValueInTheGivenArray() {
		final BigDecimal[] testArray = { BigDecimal.TEN, BigDecimal.valueOf(203.5), BigDecimal.valueOf(502.5), BigDecimal.ONE, BigDecimal.valueOf(452.3) };
		final Integer expectedValue = 2;

		final Integer actualValue = utility.getIndexOfMaximumValue(testArray);

		assertEquals(expectedValue, actualValue);
	}

	@Test
	void shouldReturnMinimumValueWhenFirstIsMaximum() {
		final BigDecimal first = BigDecimal.valueOf(203.5);
		final BigDecimal second = BigDecimal.valueOf(13.5);
		final BigDecimal expectedValue = BigDecimal.valueOf(13.5);

		final BigDecimal actualValue = utility.findMinimumOfTwoValues(first, second);

		assertEquals(expectedValue, actualValue);
	}
	
	@Test
	void shouldReturnMinimumValueWhenSecondIsMaximum() {
		final BigDecimal first = BigDecimal.valueOf(10.2);
		final BigDecimal second = BigDecimal.valueOf(12.5);
		final BigDecimal expectedValue = BigDecimal.valueOf(10.2);
		final BigDecimal actualValue = utility.findMinimumOfTwoValues(first, second);

		assertEquals(expectedValue, actualValue);
	}

	@Test
	void shouldThrowExceptionWhenGivesNullString(){
		final String nullValue = null;	
		assertThrows(NullOrEmptyStringException.class,()-> utility.checkForNullOrEmpty(nullValue));
	}
	
	@Test
	void shouldThrowExceptionWhenGivesEmptyString(){
		final String emptyString = "";	
		assertThrows(NullOrEmptyStringException.class,()-> utility.checkForNullOrEmpty(emptyString));
	}
	
	@Test
	void shouldPrintMessage() {
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final PrintStream originalOutput = System.out;
		final String expectedValue = "testUser pays 50.0 to dummyUser\r\n";

		System.setOut(new PrintStream(outputStream));

		utility.printOutput("testUser", "dummyUser", BigDecimal.valueOf(50.0));

		assertEquals(expectedValue, outputStream.toString());

		System.setOut(originalOutput);
	}
}
