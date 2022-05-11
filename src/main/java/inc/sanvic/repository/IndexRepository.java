package inc.sanvic.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import inc.sanvic.model.User;
import lombok.Getter;

@Getter
@Repository
public class IndexRepository {
	private Map<User, Integer> userMappingToIndex;
	private Map<Integer, User> indexMappingToUser;

	
	public IndexRepository() {
		userMappingToIndex = new HashMap<>();
		indexMappingToUser = new HashMap<>();
	}

	public Integer getIndexByUser(User user) {
		return userMappingToIndex.get(user);
	}

	public void setUserMappingToIndex(User user, Integer index) {
		userMappingToIndex.put(user, index);
	}

	public User getUserByIndex(Integer index) {
		return indexMappingToUser.get(index);
	}

	public void setIndexMappingToUser(Integer index, User user) {
		indexMappingToUser.put(index, user);
	}
	
}
