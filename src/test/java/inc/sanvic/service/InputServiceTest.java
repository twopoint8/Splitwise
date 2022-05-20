package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.exception.InvalidAmountException;
import inc.sanvic.exception.InvalidInputFormatException;
import inc.sanvic.exception.NullOrEmptyStringException;
import inc.sanvic.helper.Utility;
import inc.sanvic.repository.ExpenseRepository;

@SpringBootTest
public class InputServiceTest {
	@InjectMocks
	@Spy
	private InputService inputService;

	@Mock
	private Utility utility;
	@Mock
	private ExpenseRepository expenseRepository;

	@Test
	public void shouldTakeUserInput() {

		doNothing().when(inputService).takeInputFromConsole();

		inputService.takeInputFromConsole();

		verify(inputService, times(1)).takeInputFromConsole();

	}

	@Test
	void shouldCallExtractValuesAndValidate() throws InvalidInputFormatException, NumberFormatException,
			InvalidAmountException, NullOrEmptyStringException {
		final String inputValue = "A,200";
		doCallRealMethod().when(inputService).extractNameAndAmount(Mockito.anyString());
		doCallRealMethod().when(inputService).pasrseAndValidateAmount(Mockito.anyString());
		doCallRealMethod().when(utility).checkForNullOrEmpty(Mockito.anyString());
		doNothing().when(expenseRepository).addExpense(Mockito.any(), Mockito.any());
		
		inputService.extractValuesAndValidate(inputValue);

		verify(inputService, times(1)).extractNameAndAmount(Mockito.anyString());
		verify(inputService, times(1)).pasrseAndValidateAmount(Mockito.anyString());
		verify(utility, times(1)).checkForNullOrEmpty(Mockito.anyString());
		verify(expenseRepository,times(1)).addExpense(Mockito.any(), Mockito.any());
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

	@Test
	void shouldPrintErrorMessageForInvalidInputFormat(){
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final PrintStream originalError = System.err;
		final String expectedValue = "Input format is not valid, Should be like [Payer Name, Amount]\nPlease make this entry again";

		System.setErr(new PrintStream(outputStream));

		inputService.extractValuesAndValidate("testUser 2500");

		assertEquals(expectedValue, outputStream.toString().trim());

		System.setErr(originalError);
	}
	
	@Test
	void shouldPrintErrorMessageForInvalidAmountException(){
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final PrintStream originalError = System.err;
		final String expectedValue = "Amount must be equal or greater than 0 \nPlease make this entry again";

		System.setErr(new PrintStream(outputStream));

		inputService.extractValuesAndValidate("testUser,-20");

		assertEquals(expectedValue, outputStream.toString().trim());

		System.setErr(originalError);
	}
	
	@Test
	void shouldPrintErrorMessageForNumberFormatException(){
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final PrintStream originalError = System.err;
		final String expectedValue = "Amount must be number\nPlease make this entry again";

		System.setErr(new PrintStream(outputStream));

		inputService.extractValuesAndValidate("testUser, invalidAmount");

		assertEquals(expectedValue, outputStream.toString().trim());

		System.setErr(originalError);
	}
	
	@Test
	void shouldPrintErrorMessageForNullOrEmptyStringException() throws NullOrEmptyStringException{
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		final PrintStream originalError = System.err;
		final String expectedValue = "Name should not be empty\nPlease make this entry again";
		
		doCallRealMethod().when(utility).checkForNullOrEmpty(Mockito.anyString());
		System.setErr(new PrintStream(outputStream));

		inputService.extractValuesAndValidate(" ,200");

		assertEquals(expectedValue, outputStream.toString().trim());

		System.setErr(originalError);
	}
}