package inc.sanvic.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpenseManagerServiceTest {

	private ExpenseManagerService managerService;

	@BeforeEach
	void init() {
		managerService = mock(ExpenseManagerService.class);
	}

	@Test
	void shouldCallSplitExpenseFunction() {
		doNothing().when(managerService).splitExpenses();

		managerService.splitExpenses();

		verify(managerService, times(1)).splitExpenses();

	}

}
