package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UtilityServiceTest {

	private UtilityService utilityService;

	@BeforeEach
	void init() {
		utilityService = new UtilityService();
	}

	@Test
	void shouldReturnValueUptoTwoDecimal() {
		final Double testValue = 120.246452;

		final Double actualValue = utilityService.roundOfValueUptoTwoDecimal(testValue);

		assertTrue(BigDecimal.valueOf(actualValue).scale() <= 2);
	}

	@Test
	void shouldInitializeWholeArrayWithZero() {
		final Double[] testArray = new Double[5];
		final Double expectedValue = 0.0;

		final Double[] returnedArray = utilityService.initializeArrayWithZeros(testArray);

		assertEquals(expectedValue, returnedArray[2]);
	}

	@Test
	void shouldInitializeWhole2DArrayWithZero() {
		final Double[][] testArray = new Double[2][2];
		final Double expectedValue = 0.0;

		final Double[][] returnedArray = utilityService.initializeArrayWithZeros(testArray);

		assertEquals(expectedValue, returnedArray[1][0]);
	}

	@Test
	void shouldReturnIndexOfMinimumValueInTheGivenArray() {
		final Double[] testArray = { 102.0, 203.2, 100.3, 50.5, 452.3 };
		final Integer expectedValue = 3;

		final Integer actualValue = utilityService.getInexOfMinimumValue(testArray);

		assertEquals(expectedValue, actualValue);
	}

	@Test
	void shouldReturnIndexOfMaximumValueInTheGivenArray() {
		final Double[] testArray = { 102.0, 2013.2, 100.3, 50.5, 452.3 };
		final Integer expectedValue = 1;

		final Integer actualValue = utilityService.getInexOfMaximumValue(testArray);

		assertEquals(expectedValue, actualValue);
	}

	@Test
	void shouldReturnMinimumValue() {
		final Double first = 105.2;
		final Double second = 12.6;
		final Double expectedValue = 12.6;

		final Double actualValue = utilityService.findMinimumOfTwoValues(first, second);

		assertEquals(expectedValue, actualValue);
	}
}
