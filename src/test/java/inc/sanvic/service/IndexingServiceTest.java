package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

@SpringBootTest
class IndexingServiceTest {

	@InjectMocks
	IndexingService indexingService;
	@Mock
	IndexRepository indexRepository;
	@Mock
	ExpenseRepository expenseRepository;

	@Test
	void shouldSetIndexesForGivenExpenses() {

		final int expectedSize = 2;
		final User testUser1 = new User("testUser1");
		final User testUser2 = new User("testUser2");
		final Map<User, Integer> userMappingToIndex = new HashMap<User, Integer>();
		final List<Expense> expenses = new ArrayList<>();

		userMappingToIndex.put(testUser1, 0);
		userMappingToIndex.put(testUser2, 1);
		expenses.add(new Expense(100.0, testUser1));
		expenses.add(new Expense(500.0, testUser2));

		when(expenseRepository.getExpenses()).thenReturn(expenses);
		when(indexRepository.getUserMappingToIndex()).thenReturn(userMappingToIndex);

		indexingService.setIndexes();

		assertEquals(expectedSize, indexRepository.getUserMappingToIndex().size());
	}

}
