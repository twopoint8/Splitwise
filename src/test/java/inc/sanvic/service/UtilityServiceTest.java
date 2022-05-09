package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.*;

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
		
		final Double actualValue = utilityService.roundOfAmountToTwoDecimal(testValue);
		
		assertTrue(BigDecimal.valueOf(actualValue).scale()<=2);
	}
	
	@Test
	void shouldInitializeWholeArrayWithZero() {
		final Double[] testArray = new Double[5];
		
		final Double[] returnedArray = utilityService.initializeArrayWithZeros(testArray);
		final Double expectedValue = 0.0;
		assertEquals(expectedValue, returnedArray[2]);
	}

	@Test
	void shouldInitializeWhole2DArrayWithZero() {
		final Double[][] testArray = new Double[2][2];
		
		final Double[][] returnedArray = utilityService.initializeArrayWithZeros(testArray);
		final Double expectedValue = 0.0;
		assertEquals(expectedValue, returnedArray[1][0]);
	}
}
