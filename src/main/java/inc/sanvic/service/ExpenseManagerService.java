package inc.sanvic.service;

import java.util.List;

import inc.sanvic.model.Expense;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

public class ExpenseManagerService {

	private ExpenseRepository expenseRepository;
	private Double[][] balanceSheetMatrix;
	private int totalExpenses;
	private List<Expense> expenses;
	private SettleUpExpenseService settleUpService;
	private IndexingService indexingService;
	private IndexRepository indexRepository;
	private UtilityService utilityService;

	public ExpenseManagerService(ExpenseRepository expenseRepository, IndexRepository indexRepository) {
		this.expenseRepository = expenseRepository;
		this.indexRepository = indexRepository;
		settleUpService = new SettleUpExpenseService(expenseRepository, indexRepository);
		indexingService = new IndexingService(indexRepository, expenseRepository);
		utilityService = new UtilityService();
	}

	public void splitExpenses() {
		expenses = expenseRepository.getExpenses();
		totalExpenses = expenses.size();
		balanceSheetMatrix = new Double[totalExpenses][totalExpenses];

		balanceSheetMatrix = utilityService.initializeArrayWithZeros(balanceSheetMatrix);
		indexingService.setIndexes();

		expenses.forEach(expense -> {
			Integer currentExpensePayingUserIndex = indexRepository.getIndexByUser(expense.getPaidBy());

			for (int currentUser = 0; currentUser < totalExpenses; currentUser++) {
				if (currentExpensePayingUserIndex != currentUser)
					balanceSheetMatrix[currentUser][currentExpensePayingUserIndex] += utilityService
							.roundOfValueUptoTwoDecimal(expense.getAmount() / totalExpenses);
				;
			}
		});

		settleUpService.calculateEachUserTotalAmountToPayOrGet(balanceSheetMatrix);
	}

}
