package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.helper.Utility;
import inc.sanvic.model.Expense;
import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;
@SpringBootTest
class ExpenseManagerServiceTest {

	@InjectMocks
	 ExpenseManagerService expenseManagerService;
	@Mock
	Utility utility;
	@Mock
	IndexRepository indexRepository;
	@Mock
	SettleUpExpenseService settleUpExpenseService;
	@Mock
	ExpenseRepository expenseRepository;
	
	@Mock
	IndexingService indexService;
	@Test
	void shouldCallSplitExpenseFunction() {
		final Expense expense = new Expense(100.0, new User("testUser"));
		final List<Expense> expenses = new ArrayList<Expense>();
		
		expenses.add(expense);
		
		doNothing().when(settleUpExpenseService).calculateEachUserTotalAmountToPayOrGet(Mockito.any());
		doNothing().when(indexService).setIndexes();
		when(expenseRepository.getExpenses()).thenReturn(expenses);
		
		expenseManagerService.splitExpenses();

		verify(settleUpExpenseService, times(1)).calculateEachUserTotalAmountToPayOrGet(Mockito.any());
		verify(expenseRepository,times(1)).getExpenses();
	}
	@Test
	void shouldReturnValidAmountAfterDistributionOfExpenses() {
		final User testUser = new User("testUser");
		final User dummyUser = new User("dummyUser");
		final Expense travelExpense = new Expense(100.0, testUser);
		final Expense foodExpense = new Expense(250.0, dummyUser);
		final List<Expense> expenses = new ArrayList<Expense>();
		final Double expectedValue = 125.0;
		expenses.add(travelExpense);
		expenses.add(foodExpense);
		
		when(indexRepository.getIndexByUser(testUser)).thenReturn(0);
		when(indexRepository.getIndexByUser(dummyUser)).thenReturn(1);
		when(utility.initialize2DArrayWithZeros(Mockito.any())).thenCallRealMethod();
		when(utility.roundOfValueUptoTwoDecimal(Mockito.anyDouble())).thenCallRealMethod();
		
		
		final Double[][] filledBalanceSheet = expenseManagerService.distributeAmountAmongUsers(expenses);
		assertEquals(expectedValue, filledBalanceSheet[0][1]);
	}
}
