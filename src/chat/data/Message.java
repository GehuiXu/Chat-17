package chat.data;

import java.util.Date;

public class Message {
	private User user;
	private String text;
	private Date date;
	public Message (User user, String text) {
		this.user=user;
		this.text=text;
		date = new Date();
	}
	public User getUser() {
		return user;
	}
	public String getText() {
		return text;
	}
	
	public String getDate() {
		return date.toString();
	}
}
