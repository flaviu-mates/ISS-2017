package domain;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
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
