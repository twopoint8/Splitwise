package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inc.sanvic.repository.ExpenseRepository;

class ExpenseManagerServiceTest {

	ExpenseManagerService managerService;
	ExpenseRepository expenseRepository;
	@BeforeEach
	void init() {
		expenseRepository = new ExpenseRepository();
		managerService = mock(ExpenseManagerService.class);
	}
	@Test
	void shouldCallSplitExpenseFunction() {
		doNothing().when(managerService).splitExpenses();
		managerService.splitExpenses();
		
		verify(managerService, times(1)).splitExpenses();
		
	}

}
