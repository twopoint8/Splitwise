package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.helper.Utility;
import inc.sanvic.model.Expense;
import inc.sanvic.model.Friend;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

@SpringBootTest
class SettleUpExpenseServiceTest {

	@InjectMocks
	@Spy
	private SettleUpExpenseService settleUpService;
	@Mock
	private ExpenseRepository expenseRepository;
	@Mock
	private Utility utility;
	@Mock
	private IndexRepository indexRepository;

	@Test
	void shouldCallCalculateEachUserTotalAmountToPayOrGetMethod() {
		final BigDecimal[][] dummyBalanceSheetMatrix = { { BigDecimal.ZERO, BigDecimal.valueOf(50.0) }, { BigDecimal.TEN, BigDecimal.ZERO } };
		final Expense travelExpense = Expense.createExpense(BigDecimal.valueOf(100.0),Friend.createFriendInstance("testUser"));
		final Expense foodExpense = Expense.createExpense(BigDecimal.valueOf(20.0), Friend.createFriendInstance("dummyUser"));
		final List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(travelExpense);
		expenses.add(foodExpense);

		doNothing().when(settleUpService).settleAmountAmongFriends(Mockito.any());
		when(expenseRepository.getExpenses()).thenReturn(expenses);
		when(utility.initializeArrayWithZeros(Mockito.any())).thenCallRealMethod();

		settleUpService.calculateEachFriendTotalAmountToPayOrGet(dummyBalanceSheetMatrix);

		verify(expenseRepository, times(1)).getExpenses();
		verify(utility, times(1)).initializeArrayWithZeros(Mockito.any());
		
	}

	@Test
	void shouldCallsettleAmountAmongUsersMethod() {
		final BigDecimal[] totalAmountPerUserList = { BigDecimal.TEN, BigDecimal.TEN.negate() };

		doNothing().when(utility).printOutput(Mockito.any(), Mockito.any(), Mockito.any());

		when(utility.getIndexOfMaximumValue(Mockito.any())).thenCallRealMethod();
		when(utility.getIndexOfMinimumValue(Mockito.any())).thenCallRealMethod();
		when(utility.findMinimumOfTwoValues(Mockito.any(), Mockito.any())).thenCallRealMethod();
		when(indexRepository.getFriendByIndex(Mockito.anyInt())).thenReturn(Friend.createFriendInstance("testUser"), Friend.createFriendInstance("dummyUser"));

		settleUpService.settleAmountAmongFriends(totalAmountPerUserList);

		verify(utility, times(2)).getIndexOfMaximumValue(Mockito.any());
		verify(utility, times(2)).getIndexOfMinimumValue(Mockito.any());
		verify(utility, times(2)).findMinimumOfTwoValues(Mockito.any(), Mockito.any());
		verify(indexRepository, times(2)).getFriendByIndex(Mockito.anyInt());
	}

	
	@Test
	void shouldCallSettleUpAmount(){
		final BigDecimal[][] dummyBalanceSheetMatrix = { { BigDecimal.ZERO, BigDecimal.valueOf(50.0) }, { BigDecimal.TEN, BigDecimal.ZERO } };
		final BigDecimal[] totalAmountPerFriend = {BigDecimal.valueOf(-40.0), BigDecimal.valueOf(40.0)};
		
		doReturn(totalAmountPerFriend).when(settleUpService).calculateEachFriendTotalAmountToPayOrGet(Mockito.any());
		doNothing().when(settleUpService).settleAmountAmongFriends(Mockito.any());
		
		settleUpService.settleUpAmount(dummyBalanceSheetMatrix);
		
		verify(settleUpService, times(1)).calculateEachFriendTotalAmountToPayOrGet(Mockito.any());
		verify(settleUpService, times(1)).settleAmountAmongFriends(Mockito.any());
	}
	
}
