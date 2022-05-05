package inc.sanvic.service;

import org.springframework.stereotype.Service;

import inc.sanvic.model.User;
import inc.sanvic.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void addUser(User user) {
		userRepository.addUser(user);
	}
}
