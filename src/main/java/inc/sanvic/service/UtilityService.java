package inc.sanvic.service;

import java.util.Arrays;

public class UtilityService {

	public Double roundOfValueUptoTwoDecimal(Double amount) {
		return (double) Math.round(amount * 100) / 100;
	}

	public Double[] initializeArrayWithZeros(Double totalAmountPerUserList[]) {
		Arrays.fill(totalAmountPerUserList, 0.0);
		return totalAmountPerUserList;
	}

	public Double[][] initializeArrayWithZeros(Double balanceSheetMatrix[][]) {
		Arrays.stream(balanceSheetMatrix).forEach(a -> Arrays.fill(a, 0.0));
		return balanceSheetMatrix;
	}
	
	public Integer getInexOfMinimumValue(Double amount[]) {
		int indexOfMinimumValue = 0;
		for (int currentIndex = 1; currentIndex < amount.length; currentIndex++)
			if (amount[currentIndex] < amount[indexOfMinimumValue])
				indexOfMinimumValue = currentIndex;
		return indexOfMinimumValue;
	}

	public Integer getInexOfMaximumValue(Double amount[]) {
		int indexOfMaximumValue = 0;
		for (int currentIndex = 1; currentIndex < amount.length; currentIndex++)
			if (amount[currentIndex] > amount[indexOfMaximumValue])
				indexOfMaximumValue = currentIndex;
		return indexOfMaximumValue;
	}

	public Double findMinimumOfTwoValues(Double first, Double second) {
		return (first < second) ? first : second;
	}

}
