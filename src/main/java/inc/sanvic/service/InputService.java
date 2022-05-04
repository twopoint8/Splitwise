package inc.sanvic.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import inc.sanvic.exception.InvalidAmountException;

public class InputService {

	public void takeInput() {
		Scanner scanner = new Scanner(System.in);
		String userName = "";
		Double amount = -1D;
		String input;
		while (true) {
			input = scanner.next();
			if ("done".equals(input))
				break;
			try {
				List<String> inputList = extractValuesFromInput(input);
				userName = inputList.get(0);
				amount = Double.parseDouble(inputList.get(1));
				if (amount < 0)
					throw new InvalidAmountException("Amount must be equal or greater than 0");
			} catch (InvalidAmountException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("Amount must be a number");
				System.exit(0);
			}
		}
		scanner.close();		
	}

	public List<String> extractValuesFromInput(String userInput) {
		return Arrays.asList(userInput.split(","));
	}
}
