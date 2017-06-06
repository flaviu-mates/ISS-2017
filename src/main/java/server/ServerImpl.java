package server;

import common.IClientController;
import common.IServerController;
import domain.*;
import server.service.*;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ServerImpl implements IServerController {

    private final UserService userService;
    private final ConferenceService conferenceService;
    private final EditionService editionService;
    private final PaperService paperService;
    private final ReviewService reviewService;
    private final RegistrationService registrationService;
    private final SessionChairService sessionChairService;
    private final SessionService sessionService;
    private static final int THREADS = Runtime.getRuntime().availableProcessors();

    private Map<String, IClientController> clientsMap;

    public ServerImpl(UserService userService,
                      ConferenceService conferenceService,
                      EditionService editionService,
                      PaperService paperService,
                      ReviewService reviewService,
                      RegistrationService registrationService,
                      SessionChairService sessionChairService,
                      SessionService sessionService) {
        this.userService = userService;
        this.conferenceService = conferenceService;
        this.editionService = editionService;
        this.paperService = paperService;
        this.reviewService= reviewService;
        this.registrationService = registrationService;
        this.sessionChairService = sessionChairService;
        this.sessionService = sessionService;

        this.clientsMap = new ConcurrentHashMap<>();
    }

    @Override
    public List<User> getAllUser() {
        return userService.getAll();
    }

    @Override
    public List<Edition> getAllEditions() {
        return editionService.getAll();
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewService.getAll();
    }

    @Override
    public List<SessionChair> getAllSessionChairs() {
        return sessionChairService.getAll();
    }

    @Override
    public List<Paper> getPapersToBeReviewed(User user, ReviewStatus reviewStatus) {
        return reviewService.getPaperToBeReviewed(user, reviewStatus);
    }

    public User login(User user, IClientController client) throws RemoteException {
        User loggedUser = userService.findUser(user);

        //invalid user
        if (loggedUser == null) {
            throw new RemoteException("Invalid username/password");
        }

        //already logged in
        if (clientsMap.containsKey(loggedUser.getUsername())) {
            throw new RemoteException("User is already logged in");
        }

        //we save the user in loggedClients HashMap
        clientsMap.put(loggedUser.getUsername(), client);
        return loggedUser;
    }

    @Override
    public void logout(String username) throws RemoteException {
        clientsMap.remove(username);
    }

    @Override
    public void addUser(User user) throws Exception {
        userService.add(user);
    }

    @Override
    public void addPaper(Paper paper) throws Exception {
        paperService.add(paper);
    }

    @Override
    public void updateUser(User newUser) throws Exception {
        userService.update(newUser.getId(), newUser);
        this.notifyAllViewers();
    }

    @Override
    public void addReview(Review review) throws Exception {
        reviewService.add(review);
    }

    @Override
    public void addSessionChair(SessionChair sessionChair) throws Exception {
        sessionChairService.add(sessionChair);
    }

    @Override
    public void updatePaper(Paper newPaper) throws RemoteException {
        paperService.update(newPaper.getId(), newPaper);
    }

    @Override
    public List<Conference> getAllConferences() {
        return conferenceService.getAll();
    }

    @Override
    public List<Paper> getAllPapers() {
        return paperService.getAll();
    }

    @Override
    public User getUserById(int userId) {
        return userService.findById(userId);
    }

    @Override
    public Edition getEditionById(int editionId) {
        return editionService.findById(editionId);
    }

    @Override
    public Paper getPaperById(int paperId) {
        return paperService.findById(paperId);
    }

    @Override
    public List<Paper> getPapersFromAuthor(User author) {
        return paperService.getPapersFromAuthor(author);
    }

    @Override
    public void addConference(Conference conference) throws Exception {
        conferenceService.add(conference);
    }

    @Override
    public void addEdition(Edition edition) throws Exception {
        editionService.add(edition);
    }

    @Override
    public List<Review> getReviewByReviewerAndStatus(User user, ReviewStatus status) {
        return reviewService.getReviewByReviewerAndStatus(user, status);
    }

    @Override
    public List<Review> getReviewsByReviewer(User user) {
        return reviewService.getReviewsByReviewer(user);
    }

    @Override
    public Conference getConferenceById(int conferenceId) {
        return conferenceService.findById(conferenceId);
    }

    @Override
    public List<Edition> getEditionAfterDate(Date date) {
        return editionService.getEditionAfterDate(date);
    }

    @Override
    public List<Paper> getPapersReviewer(User user) {
        return getAllReviews().stream()
                .filter(review -> review.getUserPaper().getUser().getId() == user.getId())
                .map(review -> review.getUserPaper().getPaper())
                .collect(Collectors.toList());
    }

    @Override
    public void updateReview(Review review) throws Exception {
        //TODO: Update review key missing
    }

    @Override
    public Review getReviewByReviewerAndPaper(User user, Paper paper) {
        return reviewService.getReviewByReviewerAndPaper(user, paper);
    }

    @Override
    public List<Paper> getPapersNotReviewed(User user) {
        return paperService.getAll().stream()
                .filter(paper -> isPaperReviewer(user, paper))
                .collect(Collectors.toList());
    }

    public boolean isPaperReviewer(User user, Paper paper) {
        return getPapersReviewer(user).stream().filter(paper1 -> paper1.getId() == paper.getId()).collect(Collectors.toList()).size() == 1;
    }

    @Override
    public void deleteReview(Review review) {
        //TODO: Review key for deletion
    }

    @Override
    public void addRegistration(Registration registration) throws Exception {
        registrationService.add(registration);
    }

    @Override
    public List<Session> getAllSessions() throws RemoteException {
        return sessionService.getAll();
    }

    private void notifyAllViewers() {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        for (String username : clientsMap.keySet()) {
            IClientController client = clientsMap.get(username);
            {
                executor.execute(() -> {
                    try {
                        client.showUpdated();
                    } catch (Exception e) {
                        //TODO: Something
                        e.getMessage();
                    }
                });
            }

        }

        executor.shutdown();
    }
}
