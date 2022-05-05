package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.Expense;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;

@SpringBootTest
public class ExpenseServiceTest {

	ExpenseRepository expenseRepository;
	UserRepository userRepository;
	ExpenseService expenseService;

	@BeforeEach
	void init() {
		expenseRepository = new ExpenseRepository();
		userRepository = new UserRepository();
		expenseService = new ExpenseService(expenseRepository, userRepository);
	}

	@Test
	public void shouldCreateAnExpenseForNewUser() {
		String paidBy = "testUser";
		Double amount = 100D;

		expenseService.createExpense(paidBy, amount);

		assertEquals(1, userRepository.getUsers().size());
	}

	@Test
	public void shouldAddAmountInExpenseForExistingUser() {
		String paidBy = "testUser";
		Double firstExpenseamount = 100D;
		Double secondExpenseamount = 123D;
		Double expectedAmount = 223D;

		expenseService.createExpense(paidBy, firstExpenseamount);
		expenseService.createExpense(paidBy, secondExpenseamount);
		Double actualAmount = expenseRepository.getExpenses().stream()
				.filter(expense -> expense.getPaidBy().getName().equals(paidBy)).findFirst().get().getAmount();

		assertEquals(expectedAmount, actualAmount);
	}
}
