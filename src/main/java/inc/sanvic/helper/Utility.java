package inc.sanvic.helper;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.stereotype.Component;

import inc.sanvic.exception.NullOrEmptyStringException;

@Component
public class Utility {

	public BigDecimal[] initializeArrayWithZeros(BigDecimal[] totalAmountPerUserList) {
		Arrays.fill(totalAmountPerUserList, BigDecimal.ZERO);
		return totalAmountPerUserList;
	}

	public BigDecimal[][] initialize2DArrayWithZeros(BigDecimal balanceSheetMatrix[][]) {
		Arrays.stream(balanceSheetMatrix).forEach(a -> Arrays.fill(a, BigDecimal.ZERO));
		return balanceSheetMatrix;
	}

	public Integer getIndexOfMinimumValue(BigDecimal[] amount) {
		int indexOfMinimumValue = 0;
		for (int currentIndex = 1; currentIndex < amount.length; currentIndex++)
			if (amount[currentIndex].compareTo(amount[indexOfMinimumValue]) < 0)
				indexOfMinimumValue = currentIndex;
		return indexOfMinimumValue;
	}

	public Integer getIndexOfMaximumValue(BigDecimal[] amount) {
		int indexOfMaximumValue = 0;
		for (int currentIndex = 1; currentIndex < amount.length; currentIndex++)
			if (amount[currentIndex].compareTo(amount[indexOfMaximumValue]) > 0)
				indexOfMaximumValue = currentIndex;
		return indexOfMaximumValue;
	}

	public BigDecimal findMinimumOfTwoValues(BigDecimal first, BigDecimal second) {
		return (first.compareTo(second) < 0) ? first : second;
	}

	public void checkForNullOrEmpty(String name) throws NullOrEmptyStringException {
		if(name == null || name.isEmpty())
			throw new NullOrEmptyStringException("Name should not be empty");
	}
}
