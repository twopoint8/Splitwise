package inc.sanvic.service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sanvic.exception.InvalidAmountException;
import inc.sanvic.exception.InvalidInputFormatException;
import inc.sanvic.repository.UserRepository;

@Service
public class InputService {
	private String userName;
	private Double amount;
	private String USER_INPUT_BREAK_STATEMENT = "done";

	UserRepository userRepository;
	ExpenseService expenseService;

	@Autowired
	public InputService(UserRepository userRepository, ExpenseService expenseService) {
		super();
		this.userRepository = userRepository;
		this.expenseService = expenseService;
	}

	public void takeUserInput() {
		Scanner scanner = new Scanner(System.in);
		String inputFromConsole;
		while (true) {
			inputFromConsole = scanner.next();
			if (USER_INPUT_BREAK_STATEMENT.equals(inputFromConsole))
				break;
			try {
				List<String> inputList = extractValuesFromInput(inputFromConsole);
				userName = inputList.get(0).trim();
				amount = pasrseAndValidateAmount(inputList.get(1).trim());

				expenseService.createExpense(userName, amount);
			} catch (InvalidAmountException invalidAmountException) {
				System.err.println("Amount must be equal or greater than 0 \nPlease make this entry again");
			} catch (NumberFormatException numberFormatException) {
				System.err.println("Amount must be number\nPlease make this entry again");
			} catch (InvalidInputFormatException invalidInputFormatException) {
				System.err.println(
						"Input format is not valid, Should be like [Payee Name, Amount]\nPlease make this entry again");
			}
		}
		scanner.close();
	}

	public List<String> extractValuesFromInput(String inputFromConsole) throws InvalidInputFormatException {
		String[] extractedValues = inputFromConsole.split(",");
		if (extractedValues.length != 2)
			throw new InvalidInputFormatException("Input format is not valid, Should be like [Payee Name, Amount]");
		return Arrays.asList(extractedValues);
	}

	public Double pasrseAndValidateAmount(String amount) throws InvalidAmountException, NumberFormatException {

		if (Double.parseDouble(amount) < 0)
			throw new InvalidAmountException("Amount must be equal or greater than 0");

		return Double.parseDouble(amount);
	}
}