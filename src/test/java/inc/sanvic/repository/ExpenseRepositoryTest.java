package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.Expense;
import inc.sanvic.model.Friend;

@SpringBootTest
class ExpenseRepositoryTest {
	@InjectMocks
	private ExpenseRepository expenseRepository;
	
	@Mock
	private FriendRepository userRepository;
	@Test
	void shouldBeAbleToAddExpenses() {
		
		when(userRepository.isFriendExistsWithName(Mockito.anyString())).thenReturn(false);
		expenseRepository.addExpense(BigDecimal.valueOf(100.0),"testUser");
		expenseRepository.addExpense(BigDecimal.valueOf(200.0),"dummyUser");

		assertEquals(2, expenseRepository.getExpenses().size());
	}

	@Test
	public void shouldAbleToAddExpenseForExistingUser() {
		final String paidBy = "testUser";
		final BigDecimal firstExpenseamount = BigDecimal.valueOf(100.0);
		final BigDecimal secondExpenseamount = BigDecimal.valueOf(123.0);
		final BigDecimal expectedAmount = BigDecimal.valueOf(223.0);
		
		when(userRepository.isFriendExistsWithName(Mockito.anyString())).thenReturn(false, true);

		expenseRepository.addExpense(firstExpenseamount, paidBy);
		expenseRepository.addExpense(secondExpenseamount, paidBy);

		final BigDecimal actualAmount = expenseRepository.getExpenses().stream()
				.filter(expense -> expense.getPaidBy().getName().equals(paidBy)).findFirst().get().getAmount();

		assertEquals(expectedAmount, actualAmount);
	}
	
	@Test
	void shouldBeAbleToAddExpensesIfUserIsNull() {
		
		when(userRepository.isFriendExistsWithName(Mockito.anyString())).thenReturn(false);
		expenseRepository.addExpense(BigDecimal.valueOf(100.0),"testUser");
		expenseRepository.addExpense(BigDecimal.valueOf(200.0),null);

		assertEquals(2, expenseRepository.getExpenses().size());
	}
}
