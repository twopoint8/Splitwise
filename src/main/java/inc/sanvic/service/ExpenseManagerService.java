package inc.sanvic.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sanvic.helper.Utility;
import inc.sanvic.model.Expense;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;


@Service
public class ExpenseManagerService {

	private BigDecimal[][] balanceSheetMatrix;
	private List<Expense> expenses;

	@Autowired
	private Utility utility;

	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private IndexRepository indexRepository;
	@Autowired
	private SettleUpExpenseService settleUpService;
	@Autowired
	private IndexingService indexingService;

	public void splitExpenses() {
		expenses = expenseRepository.getExpenses();	
		indexingService.setIndexes();
		balanceSheetMatrix = initialiseAndFillBalanceSheet(expenses);	
		settleUpService.settleUpAmount(balanceSheetMatrix);
	}

	public BigDecimal[][] initialiseAndFillBalanceSheet(List<Expense> expenses) {
		Integer totalNumberOfExpenses = expenses.size();
		BigDecimal [][] balanceSheetMatrix = new BigDecimal[totalNumberOfExpenses][totalNumberOfExpenses];
		
		balanceSheetMatrix = utility.initialize2DArrayWithZeros(balanceSheetMatrix);
		
		for(Expense expense: expenses) {	
			Integer currentExpensePayingFriendIndex = indexRepository.getIndexByFriend(expense.getPaidBy());

			for (int currentFriendIndex = 0; currentFriendIndex < totalNumberOfExpenses; currentFriendIndex++) {
				if (currentExpensePayingFriendIndex != currentFriendIndex)
				balanceSheetMatrix[currentFriendIndex][currentExpensePayingFriendIndex] = balanceSheetMatrix[currentFriendIndex][currentExpensePayingFriendIndex].add((expense.getAmount().divide(new BigDecimal(totalNumberOfExpenses))));
					
			}
		};
		return balanceSheetMatrix;
	}
}
