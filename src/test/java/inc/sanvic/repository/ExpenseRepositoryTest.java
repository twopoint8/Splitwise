package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;

class ExpenseRepositoryTest {

	private ExpenseRepository expenseRepository;
	
	@BeforeEach
	void init() {
		expenseRepository = new ExpenseRepository();
	}
	@Test
	void shouldBeAbleToAddExpenses() {
		final Expense travelExpense = new Expense(100.0, new User("testUser"));
final 		Expense foodExpense = new Expense(2305.0, new User("dummyUser"));
		
		expenseRepository.addExpense(travelExpense);
		expenseRepository.addExpense(foodExpense);
		
		assertEquals(2, expenseRepository.getExpenses().size());
	}

}
