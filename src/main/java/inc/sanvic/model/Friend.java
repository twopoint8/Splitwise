package inc.sanvic.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Friend {
	private String name;
	 
	private Friend(String name) {
		this.name = name;
	}

	public static Friend createFriendInstance(String name) {
		return new Friend(name);
	}
}