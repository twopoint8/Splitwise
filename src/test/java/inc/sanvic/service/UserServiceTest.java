package inc.sanvic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inc.sanvic.model.User;
import inc.sanvic.repository.UserRepository;

class UserServiceTest {

	private UserRepository userRepository;
	private UserService userService;

	@BeforeEach
	void init() {
		userRepository = new UserRepository();
		userService = new UserService(userRepository);
	}

	@Test
	void shouldSucessfullyAddUser() {
		final String userName = "testUser";

		userService.addUser(new User(userName));

		assertEquals(1, userRepository.getUsers().size());
	}
}
