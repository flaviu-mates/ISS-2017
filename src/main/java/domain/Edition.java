package domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "editions")
public class Edition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conference_id", referencedColumnName = "id")
    private Conference conference;

    @Column
    private String name;

    @Column
    private Date paperDeadline;

    @Column
    private Date deadline;

    @Column
    private Date begin;

    @Column
    private Date end;

    public Edition() {}

    public Edition(Integer id, String name, Date begin, Date end, Conference conference, Date deadline, Date paperDeadline) {
        this.conference = conference;
        this.name = name;
        this.paperDeadline = paperDeadline;
        this.deadline = deadline;
        this.begin = begin;
        this.end = end;
    }

    public Edition(String name, Date begin, Date end, Conference conference, Date deadline, Date paperDeadline) {
        this.conference = conference;
        this.name = name;
        this.paperDeadline = paperDeadline;
        this.deadline = deadline;
        this.begin = begin;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPaperDeadline() {
        return paperDeadline;
    }

    public void setPaperDeadline(Date paperDeadline) {
        this.paperDeadline = paperDeadline;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
