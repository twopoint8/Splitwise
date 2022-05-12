package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

@SpringBootTest
class SettleUpExpenseServiceTest {

	@InjectMocks
	@Spy
	SettleUpExpenseService settleUpService;
	@Mock
	ExpenseRepository expenseRepository;
	@Mock
	Utility utility;
	@Mock
	IndexRepository indexRepository;

	@Test
	void shouldCallCalculateEachUserTotalAmountToPayOrGetMethod() {
		final Double[][] dummyBalanceSheetMatrix = { { 0.0, 50.0 }, { 10.0, 0.0 } };
		final Expense travelExpense = new Expense(100.0, new User("testUser"));
		final Expense foodExpense = new Expense(20.0, new User("dummyUser"));
		final List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(travelExpense);
		expenses.add(foodExpense);

		doNothing().when(settleUpService).settleAmountAmongUsers(Mockito.any());
		when(expenseRepository.getExpenses()).thenReturn(expenses);
		when(utility.initializeArrayWithZeros(Mockito.any())).thenCallRealMethod();
		when(utility.roundOfValueUptoTwoDecimal(Mockito.anyDouble())).thenCallRealMethod();

		settleUpService.calculateEachUserTotalAmountToPayOrGet(dummyBalanceSheetMatrix);

		verify(expenseRepository, times(1)).getExpenses();
		verify(utility, times(1)).initializeArrayWithZeros(Mockito.any());
		verify(utility, times(8)).roundOfValueUptoTwoDecimal(Mockito.anyDouble());
	}

	@Test
	void shouldCallsettleAmountAmongUsersMethod() {
		final Double[] totalAmountPerUserList = { 40.0, -40.0 };

		doNothing().when(settleUpService).printOutput(Mockito.any(), Mockito.any(), Mockito.any());

		when(utility.getIndexOfMaximumValue(Mockito.any())).thenCallRealMethod();
		when(utility.getIndexOfMinimumValue(Mockito.any())).thenCallRealMethod();
		when(utility.findMinimumOfTwoValues(Mockito.anyDouble(), Mockito.anyDouble())).thenCallRealMethod();
		when(indexRepository.getUserByIndex(Mockito.anyInt())).thenReturn(new User("testUser"), new User("dummyUser"));

		settleUpService.settleAmountAmongUsers(totalAmountPerUserList);

		verify(utility, times(2)).getIndexOfMaximumValue(Mockito.any());
		verify(utility, times(2)).getIndexOfMinimumValue(Mockito.any());
		verify(utility, times(1)).findMinimumOfTwoValues(Mockito.anyDouble(), Mockito.anyDouble());
		verify(indexRepository, times(2)).getUserByIndex(Mockito.anyInt());
	}

	@Test
	void shouldPrintMessage() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		final PrintStream originalOut = System.out;
		final String expectedValue = "testUser pays 50.0 to dummyUser\r\n";

		System.setOut(new PrintStream(outContent));

		settleUpService.printOutput("testUser", "dummyUser", 50.0);

		assertEquals(expectedValue, outContent.toString());

		System.setOut(originalOut);
	}
}
