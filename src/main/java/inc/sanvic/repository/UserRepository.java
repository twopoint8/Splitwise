package inc.sanvic.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import inc.sanvic.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Repository
public class UserRepository {

	private List<User> users;

	public UserRepository() {
		users = new ArrayList<>();
	}

	public void addUser(User user) {
		users.add(user);
	}

	public Boolean isUserExists(String userName) {
		return users.contains(new User(userName));
	}

}