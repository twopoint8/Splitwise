package inc.sanvic.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InputServiceTest {

	private InputService inputService;

	@BeforeEach
	void init() {
		inputService = mock(InputService.class);
	}

	@Test
	public void shouldTakeUserInput() {

		doNothing().when(inputService).takeUserInput();

		inputService.takeUserInput();

		verify(inputService, times(1)).takeUserInput();

	}
}