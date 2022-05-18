package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

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
		doNothing().when(inputService).takeInputFromConsole();
		
		inputService.takeInputFromConsole();
		
		verify(inputService,times(1)).takeInputFromConsole();
		
	}
	
	@Test
	public void shouldReturnValidAmountValue() throws NumberFormatException, InvalidAmountException {
		final String amount = "100";
		final BigDecimal expectedValue = BigDecimal.valueOf(100);

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
			inputService.extractNameAndAmount(testConsoleInput);
		});
	}

	@Test
	public void shouldGiveValidValuesForValidUserInput() throws InvalidInputFormatException {
		final String testConsoleInput = "testUser,244";
		final Integer expectedValue = 2;
		final String expectedTestUserName = "testUser";

		assertEquals(expectedValue, inputService.extractNameAndAmount(testConsoleInput).size());
		assertEquals(expectedTestUserName, inputService.extractNameAndAmount(testConsoleInput).get(0));
	}

}