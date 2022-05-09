package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import inc.sanvic.exception.InvalidAmountException;
import inc.sanvic.exception.InvalidInputFormatException;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;

public class InputServiceTest {

	private InputService inputService;
	private UserRepository userRepository;
	private ExpenseRepository expenseRepository;

	@BeforeEach
	void init() {
		expenseRepository = new ExpenseRepository();
		userRepository = new UserRepository();
		inputService = new InputService(expenseRepository, userRepository);
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