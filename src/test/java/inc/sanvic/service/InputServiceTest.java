package inc.sanvic.service;

import org.junit.jupiter.api.BeforeEach;

import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;

public class InputServiceTest {

	InputService inputService;
	ExpenseRepository expenseRepository;
	UserRepository userRepository;
	@BeforeEach
	void setup() {
		expenseRepository = new ExpenseRepository();
		userRepository = new UserRepository();
		inputService = new InputService(expenseRepository, userRepository);
	}
}