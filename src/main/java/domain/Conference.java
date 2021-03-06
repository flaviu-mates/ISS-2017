package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "conferences")
public class Conference implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name")
    private String name;

    public Conference() {}

    public Conference(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Conference(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
