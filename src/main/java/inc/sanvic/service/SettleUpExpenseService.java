package inc.sanvic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sanvic.helper.Utility;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

@Service
public class SettleUpExpenseService {

	ExpenseRepository expenseRepository;
	IndexRepository indexRepository;

	@Autowired
	public SettleUpExpenseService(ExpenseRepository expenseRepository, IndexRepository indexRepository,
			Utility utility) {
		super();
		this.expenseRepository = expenseRepository;
		this.indexRepository = indexRepository;
		this.utility = utility;
	}

	private Integer totalExpenses;
	@Autowired
	Utility utility;

	private void settleAmountAmongUsers(Double totalAmountPerUserList[]) {
		int indexOfMaximumAmount = utility.getInexOfMaximumValue(totalAmountPerUserList),
				indexOfMinimumAmount = utility.getInexOfMinimumValue(totalAmountPerUserList);
		if (totalAmountPerUserList[indexOfMaximumAmount] == 0 && totalAmountPerUserList[indexOfMinimumAmount] == 0)
			return;

		Double minimumAmountToPay = utility.findMinimumOfTwoValues(-totalAmountPerUserList[indexOfMinimumAmount],
				totalAmountPerUserList[indexOfMaximumAmount]);
		totalAmountPerUserList[indexOfMaximumAmount] -= minimumAmountToPay;
		totalAmountPerUserList[indexOfMinimumAmount] += minimumAmountToPay;
		printOutput(indexRepository.getUserByIndex(indexOfMinimumAmount).getName(),
				indexRepository.getUserByIndex(indexOfMaximumAmount).getName(), minimumAmountToPay);

		settleAmountAmongUsers(totalAmountPerUserList);
	}

	public void calculateEachUserTotalAmountToPayOrGet(Double balanceSheetMatrix[][]) {
		totalExpenses = expenseRepository.getExpenses().size();
		Double[] totalAmountPerUserList = new Double[totalExpenses];
		utility.initializeArrayWithZeros(totalAmountPerUserList);
		for (int indexOfCurrentUser = 0; indexOfCurrentUser < totalExpenses; indexOfCurrentUser++)
			for (int indexOfOtherUser = 0; indexOfOtherUser < totalExpenses; indexOfOtherUser++)
				totalAmountPerUserList[indexOfCurrentUser] = utility
						.roundOfValueUptoTwoDecimal((totalAmountPerUserList[indexOfCurrentUser] + utility
								.roundOfValueUptoTwoDecimal(balanceSheetMatrix[indexOfOtherUser][indexOfCurrentUser]
										- balanceSheetMatrix[indexOfCurrentUser][indexOfOtherUser])));

		settleAmountAmongUsers(totalAmountPerUserList);
	}

	private void printOutput(String whoPays, String whomToPay, Double howMuchToPay) {
		System.out.println(whoPays + " pays " + howMuchToPay + " to Person " + whomToPay);
	}
}
