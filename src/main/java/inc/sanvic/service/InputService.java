package inc.sanvic.service;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import inc.sanvic.exception.InvalidAmountException;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;

public class InputService {
	private String userName;
	private Double amount;
	private ExpenseService expenseService;

	public InputService(ExpenseRepository expenseRepository, UserRepository userRepository) {
		expenseService = new ExpenseService(expenseRepository, userRepository);
	}

	public void takeUserInput() {
		Scanner scanner = new Scanner(System.in);
		String inputFromConsole;
		while (true) {
			inputFromConsole = scanner.next();
			if ("done".equals(inputFromConsole))
				break;
			try {
				List<String> inputList = extractValuesFromInput(inputFromConsole);
				userName = inputList.get(0);
				amount = Double.parseDouble(inputList.get(1));
				if (amount < 0)
					throw new InvalidAmountException("Amount must be equal or greater than 0");
			} catch (InvalidAmountException invalidAmountException) {
				invalidAmountException.printStackTrace();
			} catch (NumberFormatException e) {
				throw new NumberFormatException("Amount must be number");
			}
			expenseService.createExpense(userName, amount);
		}
		scanner.close();
	}

	public List<String> extractValuesFromInput(String inputFromConsole) {
		return Arrays.asList(inputFromConsole.split(","));
	}
}