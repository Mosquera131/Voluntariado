package entity;

import java.time.LocalDate;

public class Project extends User {
    private int id;
    private String title;
    private String description;
    private LocalDate start_date;
    private LocalDate end_date;
    private int created_by;

    public Project()
    {

    }

    public Project(int id, String title, String description, LocalDate start_date,LocalDate end_date,int created_by)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created_by = created_by;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public LocalDate getStart_date() {return start_date;}

    public void setStart_date(LocalDate start_date) {this.start_date = start_date;}

    public LocalDate getEnd_date() {return end_date;}

    public void setEnd_date(LocalDate end_date) {this.end_date = end_date;}

    public int getCreated_by() {return created_by;}

    public void setCreated_by(int created_by) {this.created_by = created_by;}


    public String toString()
    {
        return  "Projects{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description=' "  + description + '\'' +
                ", start_date= ' " + start_date + '\'' +
                ", end_date= ' " + end_date + '\'' +
                ", created_by ' " + created_by + '\'' +
                ", ";

    }
    public String ShowProject()
    {
        return "Projects{" +
                "id=" + id +
                ", title= '" + title + '\'' +
                ", description=' "  + description + '\'' +
                ", start_date= ' " + start_date + '\'' +
                ", end_date= ' " + end_date + '\'' +

        '}';
    }

    public String toStringCreated(){
        return  "Projects{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description=' "  + description + '\'' +
                ", start_date= ' " + start_date + '\'' +
                ", end_date= ' " + end_date + '\'' +
                ", created_by ' " + super.getName()+
                ", ";

    }
}
