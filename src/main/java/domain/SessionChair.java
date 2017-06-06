package domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "session_chairs")
public class SessionChair implements Serializable {

    @EmbeddedId
    private UserEdition userEdition;

    public SessionChair() {}

    public SessionChair(UserEdition userEdition) {
        this.userEdition = userEdition;
    }

    public UserEdition getUserEdition() {
        return userEdition;
    }

    public void setUserEdition(UserEdition userEdition) {
        this.userEdition = userEdition;
    }
}
