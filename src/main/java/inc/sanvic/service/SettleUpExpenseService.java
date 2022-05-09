package inc.sanvic.service;

import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

public class SettleUpExpenseService {

	private ExpenseRepository expenseRepository;
	private IndexRepository indexRepository;
	private Integer totalExpenses;
	private UtilityService utilityService;
	public SettleUpExpenseService(ExpenseRepository expenseRepository, IndexRepository indexRepository) {
		this.expenseRepository = expenseRepository;
		this.indexRepository = indexRepository;
		utilityService = new UtilityService();
	}

	private Integer getInexOfMinimumValue(Double arr[]) {
		int indexOfMinimumValue = 0;
		for (int currentIndex = 1; currentIndex < totalExpenses; currentIndex++)
			if (arr[currentIndex] < arr[indexOfMinimumValue])
				indexOfMinimumValue = currentIndex;
		return indexOfMinimumValue;
	}

	private Integer getInexOfMaximumValue(Double arr[]) {
		int indexOfMaximumValue = 0;
		for (int currentIndex = 1; currentIndex < totalExpenses; currentIndex++)
			if (arr[currentIndex] > arr[indexOfMaximumValue])
				indexOfMaximumValue = currentIndex;
		return indexOfMaximumValue;
	}

	private Double findMinimumOfTwoValues(Double first, Double second) {
		return (first < second) ? first : second;
	}

	private void settleAmountAmongUsers(Double totalAmountPerUserList[]) {
		int indexOfMaximumAmount = getInexOfMaximumValue(totalAmountPerUserList),
				indexOfMinimumAmount = getInexOfMinimumValue(totalAmountPerUserList);
		if (totalAmountPerUserList[indexOfMaximumAmount] == 0 && totalAmountPerUserList[indexOfMinimumAmount] == 0)
			return;

		Double minimumAmountToPay = findMinimumOfTwoValues(-totalAmountPerUserList[indexOfMinimumAmount],
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
		utilityService.initializeArrayWithZeros(totalAmountPerUserList);
		for (int indexOfCurrentUser = 0; indexOfCurrentUser < totalExpenses; indexOfCurrentUser++)
			for (int indexOfOtherUser = 0; indexOfOtherUser < totalExpenses; indexOfOtherUser++) 
		totalAmountPerUserList[indexOfCurrentUser] = utilityService.roundOfAmountToTwoDecimal((totalAmountPerUserList[indexOfCurrentUser] + utilityService.roundOfAmountToTwoDecimal(balanceSheetMatrix[indexOfOtherUser][indexOfCurrentUser]
						- balanceSheetMatrix[indexOfCurrentUser][indexOfOtherUser])));
		
		settleAmountAmongUsers(totalAmountPerUserList);
	}

	private void printOutput(String whoPays, String whomToPay, Double howMuchToPay) {
		System.out.println(whoPays + " pays " + howMuchToPay + " to Person " + whomToPay);
	}

	
	

}
