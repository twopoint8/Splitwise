package inc.sanvic.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sanvic.exception.InvalidAmountException;
import inc.sanvic.exception.InvalidInputFormatException;
import inc.sanvic.exception.NullOrEmptyStringException;
import inc.sanvic.helper.Utility;
import inc.sanvic.repository.ExpenseRepository;

@Service
public class InputService {
	private String name;
	private BigDecimal amount;
	private String CONSOLE_INPUT_BREAK_STATEMENT = "done";
	private Scanner scanner;
	private Integer NAME_INDEX_IN_INPUT = 0;
	private Integer AMOUNT_INDEX_IN_INPUT = 1;
	private ExpenseRepository expenseRepository;
	private Utility utility;

	@Autowired
	public InputService(ExpenseRepository expenseRepository, Utility utility) {
		this.expenseRepository = expenseRepository;
		this.utility = utility;
	}

	public void takeInputFromConsole() {
		scanner = new Scanner(System.in);
		String inputFromConsole;
		while (true) {
			inputFromConsole = scanner.next();
			if (CONSOLE_INPUT_BREAK_STATEMENT.equals(inputFromConsole))
				break;
			extractValuesAndValidate(inputFromConsole);
		}
		scanner.close();
	}

	public void extractValuesAndValidate(String inputFromConsole) {
		List<String> nameAndAmount;
		try {
			nameAndAmount = extractNameAndAmount(inputFromConsole);
			name = nameAndAmount.get(NAME_INDEX_IN_INPUT).trim();
			utility.checkForNullOrEmpty(name);
			amount = pasrseAndValidateAmount(nameAndAmount.get(AMOUNT_INDEX_IN_INPUT).trim());
			expenseRepository.addExpense(amount, name);
		} catch (InvalidAmountException invalidAmountException) {
			System.err.println("Amount must be equal or greater than 0 \nPlease make this entry again");
		} catch (NumberFormatException numberFormatException) {
			System.err.println("Amount must be number\nPlease make this entry again");
		} catch (InvalidInputFormatException invalidInputFormatException) {
			System.err.println(
					"Input format is not valid, Should be like [Payer Name, Amount]\nPlease make this entry again");
		} catch (NullOrEmptyStringException nullOrEmptyNameException) {
			System.err.println("Name should not be empty\nPlease make this entry again");
		}
	}

	public List<String> extractNameAndAmount(String inputFromConsole) throws InvalidInputFormatException {
		String[] extractedValues = inputFromConsole.split(",");
		if (extractedValues.length != 2)
			throw new InvalidInputFormatException("Input format is not valid, Should be like [Payer Name, Amount]");
		return Arrays.asList(extractedValues);
	}

	public BigDecimal pasrseAndValidateAmount(String amount) throws InvalidAmountException, NumberFormatException {
		BigDecimal amountInDecimal = new BigDecimal(amount);

		if (amountInDecimal.compareTo(BigDecimal.ZERO) < 0)
			throw new InvalidAmountException("Amount must be equal or greater than 0");

		return amountInDecimal;
	}
}