package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.Expense;
import inc.sanvic.model.Friend;
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
		final Friend testUser1 =Friend.createFriendInstance("testUser1");
		final Friend testUser2 = Friend.createFriendInstance("testUser2");
		final Map<Friend, Integer> userMappingToIndex = new HashMap<Friend, Integer>();
		final List<Expense> expenses = new ArrayList<>();

		userMappingToIndex.put(testUser1, 0);
		userMappingToIndex.put(testUser2, 1);
		expenses.add(Expense.createExpense(BigDecimal.valueOf(100.0), testUser1));
		expenses.add(Expense.createExpense(BigDecimal.valueOf(500.0), testUser2));

		when(expenseRepository.getExpenses()).thenReturn(expenses);
		when(indexRepository.getFriendMappingToIndex()).thenReturn(userMappingToIndex);

		indexingService.setIndexes();

		assertEquals(expectedSize, indexRepository.getFriendMappingToIndex().size());
	}

}
