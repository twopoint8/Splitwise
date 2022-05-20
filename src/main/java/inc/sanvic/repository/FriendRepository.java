package inc.sanvic.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Repository;

import inc.sanvic.model.Friend;
import lombok.Getter;

@Getter
@Repository
public class FriendRepository {

	private List<Friend> friends;

	public FriendRepository() {
		friends = Collections.synchronizedList(new ArrayList<>());
	}

	public void addFriend(Friend user) {
		friends.add(user);
	}

	public Boolean isFriendExistsWithName(String name) {
		return friends.contains(Friend.createFriendInstance(name));
	}

}