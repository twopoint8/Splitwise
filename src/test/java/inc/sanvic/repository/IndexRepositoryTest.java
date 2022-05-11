package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.User;
import inc.sanvic.service.IndexingService;

@SpringBootTest
class IndexRepositoryTest {
	@InjectMocks
	IndexRepository indexRepository;
	@InjectMocks
	ExpenseRepository expenseRepository;
	@InjectMocks
	IndexingService indexingService;

	@Test
	void shouldReturnValidIndexForAGivenUser() {
		final User testUser = new User("testUser");
		final User dummyUser = new User("dummyUser");
		final int expectedValue = 1;

		indexRepository.setUserMappingToIndex(dummyUser, 0);
		indexRepository.setUserMappingToIndex(testUser, 1);

		assertEquals(expectedValue, indexRepository.getIndexByUser(testUser));
	}

	@Test
	void shouldReturnValidUserForAGivenIndex() {
		final User testUser = new User("testUser");
		final User dummyUser = new User("dummyUser");
		final String expectedValue = "testUser";

		indexRepository.setIndexMappingToUser(0, testUser);
		indexRepository.setIndexMappingToUser(1, dummyUser);

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
		final int expectedValue = 2;

		indexRepository.setIndexMappingToUser(testUserIndex, testUser);
		indexRepository.setIndexMappingToUser(dummyUserIndex, dummyUser);

		assertEquals(expectedValue, indexRepository.getIndexMappingToUser().size());
	}
}
