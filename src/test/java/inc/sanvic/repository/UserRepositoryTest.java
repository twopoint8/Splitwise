package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inc.sanvic.model.User;

class UserRepositoryTest {

	private UserRepository userRepository;

	@BeforeEach
	void init() {
		userRepository = new UserRepository();
	}

	@Test
	void shouldBeAbleToAddUser() {
		final User testUser = new User("testUser");
		final int expectedValue = 1;
		userRepository.addUser(testUser);

		assertEquals(expectedValue, userRepository.getUsers().size());
	}

	@Test
	void shouldReturnTrueWhenUserExists() {
		final User testUser = new User("testUser");
		final User dummyUser = new User("dummyUser");

		userRepository.addUser(testUser);
		userRepository.addUser(dummyUser);

		assertTrue(userRepository.isUserExists("testUser"));
	}

	@Test
	void shouldReturnFalseWhenUserExists() {
		final User testUser = new User("testUser");

		userRepository.addUser(testUser);

		assertFalse(userRepository.isUserExists("dummyUser"));
	}

}
