package inc.sanvic.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sanvic.helper.Utility;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

@Service
public class SettleUpExpenseService {

	private Utility utility;
	private ExpenseRepository expenseRepository;
	private IndexRepository indexRepository;
	private Integer totalExpenses;

	@Autowired
	public SettleUpExpenseService(ExpenseRepository expenseRepository, IndexRepository indexRepository,
			Utility utility) {
		this.expenseRepository = expenseRepository;
		this.indexRepository = indexRepository;
		this.utility = utility;
	}

	public void settleUpAmount(BigDecimal[][] balanceSheetMatrix) {
		BigDecimal[] totalAmountPerFriend = calculateEachFriendTotalAmountToPayOrGet(balanceSheetMatrix);
		settleAmountAmongFriends(totalAmountPerFriend);
	}

	public BigDecimal[] calculateEachFriendTotalAmountToPayOrGet(BigDecimal[][] balanceSheetMatrix) {
		totalExpenses = expenseRepository.getExpenses().size();
		BigDecimal[] totalAmountPerFriend = new BigDecimal[totalExpenses];
		utility.initializeArrayWithZeros(totalAmountPerFriend);
		for (int indexOfCurrentFriend = 0; indexOfCurrentFriend < totalExpenses; indexOfCurrentFriend++)
			for (int indexOfOtherFriend = 0; indexOfOtherFriend < totalExpenses; indexOfOtherFriend++)
				totalAmountPerFriend[indexOfCurrentFriend] = totalAmountPerFriend[indexOfCurrentFriend]
						.add(balanceSheetMatrix[indexOfOtherFriend][indexOfCurrentFriend]
								.subtract(balanceSheetMatrix[indexOfCurrentFriend][indexOfOtherFriend]));
		return totalAmountPerFriend;
	}

	public void settleAmountAmongFriends(BigDecimal[] totalAmountPerFriend) {
		int indexOfMaximumAmount = utility.getIndexOfMaximumValue(totalAmountPerFriend);
		int indexOfMinimumAmount = utility.getIndexOfMinimumValue(totalAmountPerFriend);
		BigDecimal minimumAmountToPay = utility.findMinimumOfTwoValues(
				totalAmountPerFriend[indexOfMinimumAmount].negate(), totalAmountPerFriend[indexOfMaximumAmount]);
		
		if (totalAmountPerFriend[indexOfMaximumAmount].compareTo(BigDecimal.ZERO) == 0
				&& totalAmountPerFriend[indexOfMinimumAmount].compareTo(BigDecimal.ZERO) == 0)
			return;

		
		totalAmountPerFriend[indexOfMaximumAmount] = totalAmountPerFriend[indexOfMaximumAmount]
				.subtract(minimumAmountToPay);
		totalAmountPerFriend[indexOfMinimumAmount] = totalAmountPerFriend[indexOfMinimumAmount]
				.add(minimumAmountToPay);
		
		utility.printOutput(indexRepository.getFriendByIndex(indexOfMinimumAmount).getName(),
				indexRepository.getFriendByIndex(indexOfMaximumAmount).getName(), minimumAmountToPay);

		settleAmountAmongFriends(totalAmountPerFriend);
	}

}
