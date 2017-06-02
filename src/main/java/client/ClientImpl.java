package client;

import common.IClientController;
import common.IServerController;
import domain.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements IClientController {

    private IServerController server;

    public ClientImpl(IServerController server) throws RemoteException {
        this.server = server;
    }

    public void addUser(User user) throws Exception {
        server.addUser(user);
    }


    public List<User> getAllUsers() {
        return server.getAllUser();
    }

    public User getUserById(int userId) {
        return server.getUserById(userId);
    }

    public Conference getConferenceById(int userId) {
        return server.getConferenceById(userId);
    }


    public Edition getEditionById(int editionId) {
        return server.getEditionById(editionId);
    }

    public void addPaper(Paper paper) throws Exception {
        server.addPaper(paper);
    }


    public void addRegistration(Registration registration) throws Exception {
        server.addRegistration(registration);
    }


    public List<Conference> getAllConferences() {
        return server.getAllConferences();
    }

    public List<Edition> getAllEdition() {
        return server.getAllEditions();
    }


    public List<Paper> getAllPapers() {
        return server.getAllPapers();
    }

    public List<Paper> getPapersFromAuthor(User author) {
        return server.getPapersFromAuthor(author);
    }

    public void updateUser(User newUser) throws Exception {
        server.updateUser(newUser);
    }

    public List<Edition> getEditionAfterDate(Date date) {
        return server.getEditionAfterDate(date);
    }

    public User login(String username, String password) throws Exception {
        User user = new User(username, password);
        return server.login(user, this);
    }

    public void logout(String username) throws Exception {
        try {
            server.logout(username);
        } catch (Exception ex) {
            throw new Exception("Error at logout!");
        }
    }

    public List<Review> getReviewByReviewerAndStatus(User user, ReviewStatus status) {
        return server.getReviewByReviewerAndStatus(user, status);
    }

    public void addConference(String conferenceName) throws Exception {
        server.addConference(new Conference(conferenceName));
    }

    public List<Paper> getPapersToBeReviewed(User user, ReviewStatus status) {
        return server.getPapersToBeReviewed(user, status);
    }

    public List<Paper> getPapersNotReviewed(User user) {
        return server.getPapersNotReviewed(user);
    }

    public void addEdition(Conference conference,
                              LocalDate beginningDate,
                              LocalDate endingDate,
                              String name,
                              LocalDate paperSubmissionDeadline,
                              LocalDate finalDeadline) throws Exception {
        Edition edition = new Edition(
                name,
                convertToDate(beginningDate),
                convertToDate(endingDate),
                conference,
                convertToDate(paperSubmissionDeadline),
                convertToDate(finalDeadline)
        );

        server.addEdition(edition);
    }

    public List<Review> getAllReviews() {
        return server.getAllReviews();
    }


    public void addReview(User user, Paper paper, ReviewStatus status, String comment) throws Exception {
        Review review = new Review(new UserPaper(user, paper), status, comment);
        server.addReview(review);

    }

    public Paper getPaperById(int id) throws Exception {
        return server.getPaperById(id);

    }

    public void deleteReview(Review r) {
        server.deleteReview(r);
    }

    public Review getReviewByReviewerAndPaper(User u, Paper p) {
        return server.getReviewByReviewerAndPaper(u, p);
    }

    public void updateReview(User user, Paper paper, ReviewStatus status, String comment) throws Exception {
        Review review = new Review(new UserPaper(user, paper), status, comment);
        server.updateReview(review);
    }


    public void updateReview(Review review) throws Exception {
        server.updateReview(review);
    }


    public void updatePaper(Paper newPaper) throws Exception {
        server.updatePaper(newPaper);
    }


    public synchronized List<SessionChair> getAllSessionChairs() {
        return server.getAllSessionChairs();
    }


    public void addSessionChair(SessionChair sessionChair) throws Exception {
        server.addSessionChair(sessionChair);
    }

    public static Date convertToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }

        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void showUpdated() throws RemoteException {
        //TODO: Observers
    }
}
