package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "papers")
public class Paper implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "session_id", referencedColumnName = "id")
    private Session session;

    @Column
    private PaperStatus paperStatus;

    @Column
    private String title;

    @Column
    private String topic;

    public Paper() {}

    public Paper(Integer id, PaperStatus paperStatus, String title, String topic, Session session, User user) {
        this.id = id;
        this.user = user;
        this.session = session;
        this.paperStatus = paperStatus;
        this.title = title;
        this.topic = topic;
    }

    public Paper(PaperStatus paperStatus, String title, String topic, Session session, User user) {
        this.user = user;
        this.session = session;
        this.paperStatus = paperStatus;
        this.title = title;
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public PaperStatus getPaperStatus() {
        return paperStatus;
    }

    public void setPaperStatus(PaperStatus paperStatus) {
        this.paperStatus = paperStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
