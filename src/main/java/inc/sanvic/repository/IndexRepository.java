package inc.sanvic.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import inc.sanvic.model.Friend;
import lombok.Getter;

@Getter
@Repository
public class IndexRepository {
	private Map<Friend, Integer> friendMappingToIndex;
	private Map<Integer, Friend> indexMappingToFriend;

	
	public IndexRepository() {
		friendMappingToIndex = Collections.synchronizedMap(new HashMap<>());
		indexMappingToFriend = Collections.synchronizedMap(new HashMap<>());
	}

	public Integer getIndexByFriend(Friend friend) {
		return friendMappingToIndex.get(friend);
	}

	public void setFriendMappingToIndex(Friend friend, Integer index) {
		friendMappingToIndex.put(friend, index);
	}

	public Friend getFriendByIndex(Integer index) {
		return indexMappingToFriend.get(index);
	}

	public void setIndexMappingToFriend(Integer index, Friend friend) {
		indexMappingToFriend.put(index, friend);
	}
	
}
