package inc.sanvic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import inc.sanvic.service.InputService;

@SpringBootApplication
public class SplitwiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SplitwiseApplication.class, args);

		InputService inputService = new InputService();
		inputService.takeInput();
	}
}
