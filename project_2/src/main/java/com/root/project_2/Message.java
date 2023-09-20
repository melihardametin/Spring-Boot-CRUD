package com.root.project_2;


import jakarta.persistence.*; 
import java.text.SimpleDateFormat;  
import java.util.Date; 

@Entity
@Table(name = "messages")
public class Message {


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "sender")
	private String sender;

    @Column(name = "receiver")
	private String receiver;

    @Column(name = "title")
	private String title;

    @Column(name = "message")
	private String message;

    @Column(name = "date")
	private String date;

	@Column(name = "deleted")
	private boolean deleted;


	public Message() {

	}

	public Message(String sender, String receiver,  String title, String message) {
        this.sender = sender;
		this.receiver = receiver;
		this.title = title;
        this.message = message;
        this.deleted = false;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");  
        Date dt = new Date(); 
        this.date = formatter.format(dt);
	}

    public long getId() {return id;}
	public String getSender() {return sender;}
	public String getReceiver() {return receiver;}
    public String getTitle() {return title;}
    public String getMessage() {return message;}
    public String getDate() {return date;}
    public boolean isDeleted() {return deleted;}

	public void setSender(String sender) {this.sender = sender;}
    public void setReceiver(String receiver) {this.receiver = receiver;}
    public void setTitle(String title) {this.title = title;}
    public void setDeleted() {this.deleted = true;}


	@Override
	public String toString() {
		return "Message [id=" + id + ", sender =" + sender + ", receiver=" + receiver + ", title=" + title + ", message=" + message + "]";
	}
}