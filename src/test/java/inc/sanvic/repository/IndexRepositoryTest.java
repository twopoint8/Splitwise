package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;
import inc.sanvic.service.IndexingService;

class IndexRepositoryTest {

	private IndexRepository indexRepository;
	private ExpenseRepository expenseRepository;
	private IndexingService indexingService;

	@BeforeEach
	void init() {
		indexRepository = new IndexRepository();
		expenseRepository = new ExpenseRepository();
		indexingService = new IndexingService(indexRepository, expenseRepository);
	}

	@Test
	void shouldReturnValidIndexForAGivenUser() {
		final User testUser = new User("testUser");
		final User dummyUser = new User("dummyUser");

		final Expense travelExpense = new Expense(100.0, testUser);
		final Expense foodExpense = new Expense(2305.0, dummyUser);
		final int expectedValue = 1;

		expenseRepository.addExpense(travelExpense);
		expenseRepository.addExpense(foodExpense);
		indexingService.setIndexes();

		assertEquals(expectedValue, indexRepository.getIndexByUser(dummyUser));
	}

	@Test
	void shouldReturnValidUserForAGivenIndex() {
		final User testUser = new User("testUser");
		final User dummyUser = new User("dummyUser");

		final Expense travelExpense = new Expense(100.0, testUser);
		final Expense foodExpense = new Expense(2305.0, dummyUser);
		final String expectedValue = "testUser";

		expenseRepository.addExpense(travelExpense);
		expenseRepository.addExpense(foodExpense);
		indexingService.setIndexes();

		assertEquals(expectedValue, indexRepository.getUserByIndex(0).getName());
	}

	@Test
	void shouldAddValuesToUserMappingToIndex() {
		final User testUser = new User("testUser");
		final User dummyUser = new User("dummyUser");
		final Integer testUserIndex = 0;
		final Integer dummyUserIndex = 1;
		final int expectedValue = 2;

		indexRepository.setUserMappingToIndex(testUser, testUserIndex);
		indexRepository.setUserMappingToIndex(dummyUser, dummyUserIndex);

		assertEquals(expectedValue, indexRepository.getUserMappingToIndex().size());
	}

	@Test
	void shouldAddValuesToIndexMappingToUser() {
		final User testUser = new User("testUser");
		final User dummyUser = new User("dummyUser");
		final Integer testUserIndex = 0;
		final Integer dummyUserIndex = 1;
		int expectedValue = 2;

		indexRepository.setIndexMappingToUser(testUserIndex, testUser);
		indexRepository.setIndexMappingToUser(dummyUserIndex, dummyUser);

		assertEquals(expectedValue, indexRepository.getIndexMappingToUser().size());
	}
}
