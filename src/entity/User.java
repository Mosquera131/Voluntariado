package entity;

import helper.Role;

public class User {

    private int id;
    private String  name;
    private String email;
    private String password;
    private Role role;

    public User()
    {

    }

    public User(int id,String  name , String email,String password,Role role)
    {
        this.id=id;
        this.name=name;
        this.email=email;
        this.password=password;
    }

    // all my getters and setters

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public Role getRole() {return role;}

    public void setRole(Role role) {this.role = role;}

    //to Tostring
    @Override
    public String toString()
    {
        return "User{"+
                "id="+ id+
                ", name= '"+ name + '\'' +
                ", email="+ email +
                ", password="+ password +
                ", role=" + role + '\''+

                '}';

    }

    public String theVouleenteer()
    {
        return "id" + id +
                ", name= '" + name + '\'' +
                ", email= ' " + email + '\'' +
                ',';
    }
}
