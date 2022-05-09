package inc.sanvic.service;

import java.util.Arrays;

public class UtilityService {

	public Double roundOfAmountToTwoDecimal(Double amount) {
		return (double)Math.round(amount * 100) / 100;
	}
	
	public Double[] initializeArrayWithZeros(Double totalAmountPerUserList[]) {
		Arrays.fill(totalAmountPerUserList, 0.0);
		return totalAmountPerUserList;
	}
	
	public Double[][] initializeArrayWithZeros(Double balanceSheetMatrix[][]) {
		Arrays.stream(balanceSheetMatrix).forEach(a -> Arrays.fill(a, 0.0));
		return balanceSheetMatrix;
	}
}
