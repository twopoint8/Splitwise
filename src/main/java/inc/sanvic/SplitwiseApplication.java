package inc.sanvic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;
import inc.sanvic.service.ExpenseManagerService;
import inc.sanvic.service.InputService;

@SpringBootApplication
public class SplitwiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);

		ExpenseRepository expenseRepository = new ExpenseRepository();
		UserRepository userRepository = new UserRepository();
		InputService inputService = new InputService(expenseRepository, userRepository);
		inputService.takeUserInput();
		ExpenseManagerService expenseManagerService = new ExpenseManagerService(expenseRepository);
		expenseManagerService.splitExpenses();
	}
}