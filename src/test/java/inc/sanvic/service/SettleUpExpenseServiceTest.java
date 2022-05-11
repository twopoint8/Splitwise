package inc.sanvic.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SettleUpExpenseServiceTest {

	@Mock
	SettleUpExpenseService settleUpService;

	@Test
	void shouldCallCalculateEachUserTotalAmountToPayOrGetMethod() {
		final Double[][] dummyBalanceSheetMatrix = new Double[2][2];

		doNothing().when(settleUpService).calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);
		settleUpService.calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);
		settleUpService.calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);

		verify(settleUpService, times(2)).calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);
	}

}
