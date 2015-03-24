package limeng32.testSpring.converter;

import limeng32.testSpring.pojo.User;

import org.springframework.core.convert.converter.Converter;

public class StringToUserConverter implements Converter<String, User> {

	public User convert(String source) {
		User user = new User();
		if (source != null) {
			String[] items = source.split(":");
			user.setName(items[0]);
		}
		return user;
	}

}
