package common;

import domain.*;

import java.util.Date;
import java.util.List;

public interface IServerController {

    List<User> getAllUser();

    List<Edition> getAllEditions();

    List<Review> getAllReviews();

    List<SessionChair> getAllSessionChairs();

    List<Paper> getPapersToBeReviewed(User user, ReviewStatus reviewStatus);

    User login(User user, IClientController client) throws Exception;

    void logout(String username) throws Exception;

    void addUser(User user) throws Exception;

    void addPaper(Paper paper) throws Exception;

    void updateUser(User newUser) throws Exception;

    void addReview(Review review) throws Exception;

    void addSessionChair(SessionChair sessionChair) throws Exception;

    void updatePaper(Paper newPaper) throws Exception;

    List<Conference> getAllConferences();

    List<Paper> getAllPapers();

    User getUserById(int userId);

    Edition getEditionById(int editionId);
    Paper getPaperById(int paperId);
    List<Paper> getPapersFromAuthor(User author);
    void addConference(Conference conference) throws Exception;

    void addEdition(Edition edition) throws Exception;
    List<Review> getReviewByReviewerAndStatus(User user,ReviewStatus status);

    List<Review> getReviewsByReviewer(User user);

    Conference getConferenceById(int userId);
    List<Edition> getEditionAfterDate(Date date);
    List<Paper> getPapersReviewer(User user);

    void updateReview(Review review) throws Exception;

    Review getReviewByReviewerAndPaper(User user, Paper paper);

    List<Paper> getPapersNotReviewed(User user);

    void deleteReview(Review review);

    void addRegistration(Registration registration) throws Exception;
    
}
