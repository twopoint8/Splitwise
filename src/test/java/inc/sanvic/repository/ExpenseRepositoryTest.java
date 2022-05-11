package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;

@SpringBootTest
class ExpenseRepositoryTest {
	@InjectMocks
	private ExpenseRepository expenseRepository;

	@Test
	void shouldBeAbleToAddExpenses() {
		final Expense travelExpense = new Expense(100.0, new User("testUser"));
		final Expense foodExpense = new Expense(2305.0, new User("dummyUser"));

		expenseRepository.addExpense(travelExpense);
		expenseRepository.addExpense(foodExpense);

		assertEquals(2, expenseRepository.getExpenses().size());
	}

}
