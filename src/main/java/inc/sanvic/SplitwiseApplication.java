package inc.sanvic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import inc.sanvic.service.ExpenseManagerService;
import inc.sanvic.service.InputService;

@SpringBootApplication
public class SplitwiseApplication {

	@Autowired
	InputService inputService;
	@Autowired
	ExpenseManagerService expenseManagerService;
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SplitwiseApplication.class, args);
		 SplitwiseApplication splitwiseApplication = context.getBean(SplitwiseApplication.class);
		System.out.println("Once finished with input write done...");
		splitwiseApplication.inputService.takeUserInput();
		splitwiseApplication.expenseManagerService.splitExpenses();
	}
}