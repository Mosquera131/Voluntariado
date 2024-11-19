package entity;

import java.time.LocalDate;

public class Inscription {
    private int id;
    private int user_id;
    private int project_id;
    private LocalDate date;

    public Inscription()
    {}

    public Inscription(int id, int user_id, int project_id, LocalDate date)
    {
        this.id= id;
        this.user_id= user_id;
        this.project_id = project_id;
        this.date = date;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public int getUser_id() {return user_id;}

    public void setUser_id(int user_id) {this.user_id = user_id;}

    public int getProject_id() {return project_id;}

    public void setProject_id(int project_id) {this.project_id = project_id;}

    public LocalDate getDate() {return date;}

    public void setDate(LocalDate date) {this.date = date;}

    @Override
    public String toString() {
        return "Inscription {" +
                "id=" + id +
                ", user_id = " + user_id +
                ", project_id = " + project_id +
                ", date = " + date +

        '}';
    }
}
