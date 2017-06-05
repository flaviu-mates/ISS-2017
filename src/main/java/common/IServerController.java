package common;

import domain.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface IServerController extends Remote
{
    List<User> getAllUser() throws RemoteException;

    List<Edition> getAllEditions() throws RemoteException;

    List<Review> getAllReviews() throws RemoteException;

    List<SessionChair> getAllSessionChairs() throws RemoteException;

    List<Paper> getPapersToBeReviewed(User user, ReviewStatus reviewStatus) throws RemoteException;

    User login(User user, IClientController client) throws RemoteException;

    void logout(String username) throws RemoteException;

    void addUser(User user) throws Exception;

    void addPaper(Paper paper) throws Exception;

    void updateUser(User newUser) throws Exception;

    void addReview(Review review) throws Exception;

    void addSessionChair(SessionChair sessionChair) throws Exception;

    void updatePaper(Paper newPaper) throws Exception;

    List<Conference> getAllConferences() throws RemoteException;

    List<Paper> getAllPapers() throws RemoteException;

    User getUserById(int userId) throws RemoteException;

    Edition getEditionById(int editionId) throws RemoteException;

    Paper getPaperById(int paperId) throws RemoteException;

    List<Paper> getPapersFromAuthor(User author) throws RemoteException;

    void addConference(Conference conference) throws Exception;

    void addEdition(Edition edition) throws Exception;

    List<Review> getReviewByReviewerAndStatus(User user, ReviewStatus status) throws RemoteException;

    List<Review> getReviewsByReviewer(User user) throws RemoteException;

    Conference getConferenceById(int userId) throws RemoteException;

    List<Edition> getEditionAfterDate(Date date) throws RemoteException;

    List<Paper> getPapersReviewer(User user) throws RemoteException;

    void updateReview(Review review) throws Exception;

    Review getReviewByReviewerAndPaper(User user, Paper paper) throws RemoteException;

    List<Paper> getPapersNotReviewed(User user) throws RemoteException;

    void deleteReview(Review review) throws RemoteException;

    void addRegistration(Registration registration) throws Exception;
}