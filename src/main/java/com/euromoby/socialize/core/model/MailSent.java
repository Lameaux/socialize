package com.euromoby.socialize.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mail_sent")
public class MailSent {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "recipient")
	private String recipient;

	@Column(name = "subject")
	private String subject;

	@Column(name = "content")
	private String content;

	@Column(name = "sent")
	private long sent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
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

	public long getSent() {
		return sent;
	}

	public void setSent(long sent) {
		this.sent = sent;
	}

}
