package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.exception.InvalidAmountException;
import inc.sanvic.helper.Utility;
import inc.sanvic.model.Expense;
import inc.sanvic.model.Friend;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;
@SpringBootTest
class ExpenseManagerServiceTest {

	@InjectMocks
	private ExpenseManagerService expenseManagerService;
	@Mock
	private Utility utility;
	@Mock
	private IndexRepository indexRepository;
	@Mock
	private SettleUpExpenseService settleUpExpenseService;
	@Mock
	private ExpenseRepository expenseRepository;
	@Mock
	private IndexingService indexService;
	
	@Test
	void shouldCallSplitExpenseFunction() {
		final Expense expense = Expense.createExpense(BigDecimal.valueOf(100.0),Friend.createFriendInstance("testUser"));
		final List<Expense> expenses = new ArrayList<Expense>();
		
		expenses.add(expense);
		
		doNothing().when(settleUpExpenseService).settleUpAmount(Mockito.any());
		doNothing().when(indexService).setIndexes();
		when(expenseRepository.getExpenses()).thenReturn(expenses);
		
		expenseManagerService.splitExpenses();

		verify(settleUpExpenseService, times(1)).settleUpAmount(Mockito.any());
		verify(expenseRepository,times(1)).getExpenses();
	}
	@Test
	void shouldReturnValidAmountAfterDistributionOfExpenses() throws InvalidAmountException {
		final Friend testUser = Friend.createFriendInstance("testUser");
		final Friend dummyUser = Friend.createFriendInstance("dummyUser");
		final Expense travelExpense = Expense.createExpense(BigDecimal.valueOf(100.0), testUser);
		final Expense foodExpense = Expense.createExpense(BigDecimal.valueOf(250.0), dummyUser);
		final List<Expense> expenses = new ArrayList<Expense>();
		final BigDecimal expectedValue = BigDecimal.valueOf(125.0);
		expenses.add(travelExpense);
		expenses.add(foodExpense);
		
		when(indexRepository.getIndexByFriend(testUser)).thenReturn(0);
		when(indexRepository.getIndexByFriend(dummyUser)).thenReturn(1);
		when(utility.initialize2DArrayWithZeros(Mockito.any())).thenCallRealMethod();

		
		final BigDecimal[][] filledBalanceSheet = expenseManagerService.initialiseAndFillBalanceSheet(expenses);
		assertEquals(expectedValue, filledBalanceSheet[0][1]);
	}
}
