package domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity(name = "reviews")
public class Review implements Serializable {

    @EmbeddedId
    private UserPaper userPaper;

    @Column
    private ReviewStatus reviewStatus;

    @Column
    private String comment;

    public Review() {}

    public UserPaper getUserPaper() {
        return userPaper;
    }

    public void setUserPaper(UserPaper userPaper) {
        this.userPaper = userPaper;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
