package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;

@SpringBootTest
public class ExpenseServiceTest {

	@Mock
	private ExpenseRepository expenseRepository;
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private ExpenseService expenseService;

	@Test
	public void shouldCreateAnExpenseForNewUser() {
		final String paidBy = "testUser";
		final Double amount = 100D;
		final List<User> users = new ArrayList<>();
		
		users.add(new User(paidBy));
		
		when(userRepository.getUsers()).thenReturn(users);
		
		expenseService.createExpense(paidBy, amount);
		
		assertEquals(1, userRepository.getUsers().size());
	}

	@Test
	public void shouldAddAmountInExpenseForExistingUser() {
		final String paidBy = "testUser";
		final Double firstExpenseamount = 100D;
		final Double secondExpenseamount = 123D;
		final Double expectedAmount = 223D;
		final List<Expense> expenses = new ArrayList<>();
		
		expenses.add(new Expense(0.0, new User(paidBy)));

		when(expenseRepository.getExpenses()).thenReturn(expenses);
		when(userRepository.isUserExists(paidBy)).thenReturn(true);

		expenseService.createExpense(paidBy, firstExpenseamount);
		expenseService.createExpense(paidBy, secondExpenseamount);

		final Double actualAmount = expenseRepository.getExpenses().stream()
				.filter(expense -> expense.getPaidBy().getName().equals(paidBy)).findFirst().get().getAmount();

		assertEquals(expectedAmount, actualAmount);
	}
}
