package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.exception.InvalidAmountException;
import inc.sanvic.exception.InvalidInputFormatException;

@SpringBootTest
public class InputServiceTest {
	@InjectMocks
	InputService inputService;

	@Test
	public void shouldTakeUserInput() {
		InputService inputService = mock(InputService.class);
		doNothing().when(inputService).takeUserInput();
		
		inputService.takeUserInput();
		
		verify(inputService,times(1)).takeUserInput();
		
	}
	
	@Test
	public void shouldReturnValidAmountValue() throws NumberFormatException, InvalidAmountException {
		final String amount = "100";
		final Double expectedValue = 100D;

		assertEquals(expectedValue, inputService.pasrseAndValidateAmount(amount));
	}

	@Test
	public void shouldThrowNumberFormatExceptionForInvalidAmountValue() {
		final String amount = "invalidValue";

		assertThrows(NumberFormatException.class, () -> {
			inputService.pasrseAndValidateAmount(amount);
		});
	}

	@Test
	public void shouldThrowInvalidAmountExceptionForAmountLessThanZero() {
		final String amount = "-25";

		assertThrows(InvalidAmountException.class, () -> {
			inputService.pasrseAndValidateAmount(amount);
		});
	}

	@Test
	public void shouldThrowInvalidInputFormatExceptionForInvalidUserInput() {
		final String testConsoleInput = "testUser 244";

		assertThrows(InvalidInputFormatException.class, () -> {
			inputService.extractValuesFromInput(testConsoleInput);
		});
	}

	@Test
	public void shouldGiveValidValuesForValidUserInput() throws InvalidInputFormatException {
		final String testConsoleInput = "testUser,244";
		final Integer expectedValue = 2;
		final String expectedTestUserName = "testUser";

		assertEquals(expectedValue, inputService.extractValuesFromInput(testConsoleInput).size());
		assertEquals(expectedTestUserName, inputService.extractValuesFromInput(testConsoleInput).get(0));
	}

}