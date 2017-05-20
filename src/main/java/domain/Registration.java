package domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class Registration implements Serializable {
    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name="edition_id",referencedColumnName = "id")
    private Edition edition;

    @Column
    private boolean acquitted;

    public Registration() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }

    public boolean isAcquitted() {
        return acquitted;
    }

    public void setAcquitted(boolean acquitted) {
        this.acquitted = acquitted;
    }
}
