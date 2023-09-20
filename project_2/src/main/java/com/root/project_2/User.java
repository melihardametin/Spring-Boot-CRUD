package com.root.project_2;


import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {




	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "username")
	private String username;

    @Column(name = "pwd")
	private String pwd;

    @Column(name = "name")
	private String name;

    @Column(name = "surname")
	private String surname;

    @Column(name = "date")
	private String date;

    @Column(name = "gender")
	private String gender;

    @Column(name = "mail")
	private String mail;

    @Column(name = "city")
	private String city;

	@Column(name = "admin")
	private boolean admin;

    @Column(name = "token")
    private String token;




	public User() {

	}

	public User(String username, String pwd,  String name, String surname, String date, String gender, String mail, String city, boolean admin) {
        this.username = username;
		this.pwd = pwd;
		this.name = name;
        this.surname = surname;
        this.date = date;
        this.gender = gender;
        this.mail = mail;
        this.city = city;
        this.admin = admin;
	}


    public String getToken() {return token;}
	public long getId() {return id;}
	public String getUsername() {return username;}
    public String getPassword() {return pwd;}
    public String getName() {return name;}
    public String getSurname() {return surname;}
    public String getDate() {return date;}
    public String getGender() {return gender;}
    public String getMail() {return mail;}
    public String getCity() {return city;}
    public boolean getAdmin() {return admin;}




    public void setToken(String token) {this.token = token;}
	public void setUsername(String username) {this.username = username;}
    public void setPassword(String pwd) {this.pwd = pwd;}
    public void setName(String name) {this.name = name;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setDate(String date) {this.date = date;}
    public void setGender(String gender) {this.gender = gender;}
    public void setMail(String mail) {this.mail = mail;}
    public void setCity(String city) {this.city = city;}
    public void makeAdmin() {this.admin = true;}
    public void unMakeAdmin() {this.admin = false;}


	@Override
	public String toString() {
		return "User [id=" + id + ", usernname =" + username + ", name=" + name + ", pwd=" + pwd + ", name=" + name + ", surname=" + surname + ", date=" + date + ", gender=" + gender + ", mail=" + mail + ", city=" + city + ", admin=" + admin + "]";
	}
}



