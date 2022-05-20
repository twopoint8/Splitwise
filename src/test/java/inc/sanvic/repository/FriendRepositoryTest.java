package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.Friend;

@SpringBootTest
class FriendRepositoryTest {
	@InjectMocks
	FriendRepository friendRepository;

	@Test
	void shouldBeAbleToAddUser() {
		final Friend testUser = Friend.createFriendInstance("testUser");
		final int expectedValue = 1;
		friendRepository.addFriend(testUser);

		assertEquals(expectedValue, friendRepository.getFriends().size());
	}

	@Test
	void shouldReturnTrueWhenUserExists() {
		final Friend testUser = Friend.createFriendInstance("testUser");
		final Friend dummyUser = Friend.createFriendInstance("dummyUser");

		friendRepository.addFriend(testUser);
		friendRepository.addFriend(dummyUser);

		assertTrue(friendRepository.isFriendExistsWithName("testUser"));
	}

	@Test
	void shouldReturnFalseWhenUserDoesNotExists() {
		final Friend testUser = Friend.createFriendInstance("testUser");

		friendRepository.addFriend(testUser);

		assertFalse(friendRepository.isFriendExistsWithName("dummyUser"));
	}
	
}
