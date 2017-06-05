package server;

import common.IServerController;
import domain.User;
import repository.*;
import server.service.*;
import server.validator.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer {
    public static void main(String[] args) throws Exception{
        // TODO Repositories param -> connection
        UserValidator userValidator = new UserValidator();
        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userValidator, userRepository);

        ConferenceValidator conferenceValidator = new ConferenceValidator();
        ConferenceRepository conferenceRepository = new ConferenceRepository();
        ConferenceService conferenceService = new ConferenceService(conferenceValidator, conferenceRepository);

        EditionValidator editionValidator = new EditionValidator();
        EditionRepository editionRepository = new EditionRepository();
        EditionService editionService = new EditionService(editionValidator, editionRepository);

        PaperValidator paperValidator = new PaperValidator();
        PaperRepository paperRepository = new PaperRepository();
        PaperService paperService = new PaperService(paperValidator, paperRepository);

        ReviewValidator reviewValidator = new ReviewValidator();
        ReviewRepository reviewRepository = new ReviewRepository();
        ReviewService reviewService = new ReviewService(reviewValidator, reviewRepository);

        RegistrationValidator registrationValidator = new RegistrationValidator();
        RegistrationRepository registrationRepository = new RegistrationRepository();
        RegistrationService registrationService = new RegistrationService(registrationValidator, registrationRepository);

        SessionChairValidator sessionChairValidator = new SessionChairValidator();
        SessionChairRepository sessionChairRepository = new SessionChairRepository();
        SessionChairService sessionChairService = new SessionChairService(sessionChairValidator, sessionChairRepository);

        IServerController serverImpl = new ServerImpl(userService, conferenceService, editionService, paperService,
                                                      reviewService, registrationService, sessionChairService);

        try {
            String name = "Triathlon";
            LocateRegistry.createRegistry(1099);
            IServerController stub = (IServerController) UnicastRemoteObject.exportObject(serverImpl, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Server bound!");
        } catch (Exception e) {
            System.err.println("Server exception: ");
        }
    }
}
