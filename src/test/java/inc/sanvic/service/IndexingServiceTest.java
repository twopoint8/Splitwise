package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

class IndexingServiceTest {

	private IndexingService indexingService;
	private IndexRepository indexRepository;
	private ExpenseRepository expenseRepository;

	@BeforeEach
	void init() {
		expenseRepository = new ExpenseRepository();
		indexRepository = new IndexRepository();
		indexingService = new IndexingService(indexRepository, expenseRepository);
	}

	@Test
	void shouldSetIndexesForGivenExpenses() {
		expenseRepository.addExpense(new Expense(100.0, new User("testUser1")));
		expenseRepository.addExpense(new Expense(500.0, new User("testUser2")));
		final int expectedSize = 2;

		indexingService.setIndexes();

		assertEquals(expectedSize, indexRepository.getUserMappingToIndex().size());
	}

}
