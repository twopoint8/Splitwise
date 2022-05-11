package inc.sanvic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sanvic.helper.Utility;
import inc.sanvic.model.Expense;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;


@Service
public class ExpenseManagerService {

	private Double[][] balanceSheetMatrix;
	private List<Expense> expenses;

	@Autowired
	Utility utility;

	@Autowired
	ExpenseRepository expenseRepository;
	@Autowired
	IndexRepository indexRepository;
	@Autowired
	SettleUpExpenseService settleUpService;
	@Autowired
	IndexingService indexingService;

	public void splitExpenses() {
		expenses = expenseRepository.getExpenses();
		
		
		indexingService.setIndexes();
		balanceSheetMatrix = distributeAmountAmongUsers(expenses);	
		settleUpService.calculateEachUserTotalAmountToPayOrGet(balanceSheetMatrix);
	}

	public Double[][] distributeAmountAmongUsers(List<Expense> expenses) {
		int totalNumberOfExpenses = expenses.size();
		Double [][] balanceSheetMatrix = new Double[totalNumberOfExpenses][totalNumberOfExpenses];
		balanceSheetMatrix = utility.initialize2DArrayWithZeros(balanceSheetMatrix);
		for(Expense expense: expenses) {
		
			Integer currentExpensePayingUserIndex = indexRepository.getIndexByUser(expense.getPaidBy());

			for (int currentUser = 0; currentUser < totalNumberOfExpenses; currentUser++) {
				if (currentExpensePayingUserIndex != currentUser)
					balanceSheetMatrix[currentUser][currentExpensePayingUserIndex] += utility
							.roundOfValueUptoTwoDecimal(expense.getAmount() / totalNumberOfExpenses);
				;
			}
		};
		return balanceSheetMatrix;
	}
}
