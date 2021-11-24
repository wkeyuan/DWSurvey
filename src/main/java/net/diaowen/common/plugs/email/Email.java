package net.diaowen.common.plugs.email;

public class Email {
	private String to;// 收件人
	private String subject;// 主题
	private String username;// 用户称呼
	private String content;// 内容
	private String date;// 日期
	private String sendEmailId;

	public Email() {
		super();
	}

	public Email(String to, String subject, String username, String content,
			String date) {
		super();
		this.to = to;
		this.subject = subject;
		this.username = username;
		this.content = content;
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSendEmailId() {
		return sendEmailId;
	}

	public void setSendEmailId(String sendEmailId) {
		this.sendEmailId = sendEmailId;
	}

	@Override
	public String toString() {
		return "Email [content=" + content + ", date=" + date + ", subject="
				+ subject + ", to=" + to + ", username=" + username + "]";
	}

}
