package inc.sanvic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import inc.sanvic.model.User;

@SpringBootTest
class UserRepositoryTest {
	@InjectMocks
	UserRepository userRepository;

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
