package inc.sanvic.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;

public class InputServiceTest {
	
	InputService inputService;
	
	@BeforeEach
	void init() {
		inputService = mock(InputService.class);
	}
	@Test
	public void shouldTakeUserInput() {
		
		doNothing().when(inputService).takeUserInput();;
		inputService.takeUserInput();
		
		verify(inputService,times(1)).takeUserInput();
		
	}
}