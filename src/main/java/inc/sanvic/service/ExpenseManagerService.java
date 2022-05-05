package inc.sanvic.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;

public class ExpenseManagerService {

	private Map<User, Integer> userMappingWithIndex;
	private ExpenseRepository expenseRepository;
	private Double[][] balanceSheetMatrix;
	private int totalExpenses;
	private List<Expense> expenses;

	public ExpenseManagerService(ExpenseRepository expenseRepository) {
		this.expenseRepository = expenseRepository;
	}

	public void splitExpenses() {
		expenses = expenseRepository.getExpenses();
		totalExpenses = expenses.size();
		balanceSheetMatrix = new Double[totalExpenses][totalExpenses];
		
		balanceSheetMatrix = fill2DArrayWithZeros(balanceSheetMatrix);
		assignIndexToUser();

		expenses.forEach(expense -> {
			Integer currentExpensePayingUserIndex = userMappingWithIndex.get(expense.getPaidBy());

			for (int currentUser = 0; currentUser < totalExpenses; currentUser++) {
				if (currentExpensePayingUserIndex != currentUser)
					balanceSheetMatrix[currentUser][currentExpensePayingUserIndex] += expense.getAmount()
							/ totalExpenses;
			}
		});
	}

	private Double[][] fill2DArrayWithZeros(Double balanceSheetMatrix[][]) {
		Arrays.stream(balanceSheetMatrix).forEach(a -> Arrays.fill(a, 0.0));
		return balanceSheetMatrix;
	}

	private void assignIndexToUser() {
		userMappingWithIndex = new HashMap<>();
		List<Expense> expenses = expenseRepository.getExpenses();
		Integer currentIndex = 0;
		for (Expense expense : expenses) {
			userMappingWithIndex.put(expense.getPaidBy(), currentIndex++);
		}
	}
}
