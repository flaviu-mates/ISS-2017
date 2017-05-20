package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "sessions")
public class Session implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "edition_id", referencedColumnName = "id")
    private Edition edition;

    @Column
    private Date date;

    @Column
    private String location;

    public Session() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
