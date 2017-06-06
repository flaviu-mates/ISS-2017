package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "registration")
public class Registration implements Serializable {

    @EmbeddedId
    private UserEdition userEdition;

    @Column
    private boolean acquitted;

    public Registration() {}

    public UserEdition getUserEdition() {
        return userEdition;
    }

    public void setUserEdition(UserEdition userEdition) {
        this.userEdition = userEdition;
    }

    public boolean isAcquitted() {
        return acquitted;
    }

    public void setAcquitted(boolean acquitted) {
        this.acquitted = acquitted;
    }
}
