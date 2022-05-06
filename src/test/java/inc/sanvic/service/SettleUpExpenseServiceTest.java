package inc.sanvic.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

class SettleUpExpenseServiceTest {

	private ExpenseRepository expenseRepository;
	private IndexRepository indexRepository;
	private SettleUpExpenseService settleUpService;

	@BeforeEach
	void init() {
		expenseRepository = new ExpenseRepository();
		indexRepository = new IndexRepository();
		settleUpService = mock(SettleUpExpenseService.class);
	}

	@Test
	void shouldCallCalculateEachUserTotalAmountToPayOrGetMethod() {
		final Double[][] dummyBalanceSheetMatrix = new Double[2][2];

		doNothing().when(settleUpService).calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);
		settleUpService.calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);
		settleUpService.calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);

		verify(settleUpService, times(2)).calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);
	}

}
