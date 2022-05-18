package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.Friend;
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
		final Friend testUser = Friend.createFriendInstance("testUser");
		final Friend dummyUser = Friend.createFriendInstance("dummyUser");
		final int expectedValue = 1;

		indexRepository.setFriendMappingToIndex(dummyUser, 0);
		indexRepository.setFriendMappingToIndex(testUser, 1);

		assertEquals(expectedValue, indexRepository.getIndexByFriend(testUser));
	}

	@Test
	void shouldReturnValidUserForAGivenIndex() {
		final Friend testUser = Friend.createFriendInstance("testUser");
		final Friend dummyUser = Friend.createFriendInstance("dummyUser");
		final String expectedValue = "testUser";

		indexRepository.setIndexMappingToFriend(0, testUser);
		indexRepository.setIndexMappingToFriend(1, dummyUser);

		assertEquals(expectedValue, indexRepository.getFriendByIndex(0).getName());
	}

	@Test
	void shouldAddValuesToUserMappingToIndex() {
		final Friend testUser = Friend.createFriendInstance("testUser");
		final Friend dummyUser = Friend.createFriendInstance("dummyUser");
		final Integer testUserIndex = 0;
		final Integer dummyUserIndex = 1;
		final int expectedValue = 2;

		indexRepository.setFriendMappingToIndex(testUser, testUserIndex);
		indexRepository.setFriendMappingToIndex(dummyUser, dummyUserIndex);

		assertEquals(expectedValue, indexRepository.getFriendMappingToIndex().size());
	}

	@Test
	void shouldAddValuesToIndexMappingToUser() {
		final Friend testUser = Friend.createFriendInstance("testUser");
		final Friend dummyUser = Friend.createFriendInstance("dummyUser");
		final Integer testUserIndex = 0;
		final Integer dummyUserIndex = 1;
		final int expectedValue = 2;

		indexRepository.setIndexMappingToFriend(testUserIndex, testUser);
		indexRepository.setIndexMappingToFriend(dummyUserIndex, dummyUser);

		assertEquals(expectedValue, indexRepository.getIndexMappingToFriend().size());
	}
}
