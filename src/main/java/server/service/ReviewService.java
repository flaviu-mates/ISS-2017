package server.service;

import domain.*;
import repository.IRepository;
import repository.ReviewRepository;
import server.validator.IValidator;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewService extends AbstractService<Integer, Review> {

    private ReviewRepository repository;
    private IValidator<Review> validator;

    public ReviewService(ReviewRepository repository, IValidator<Review> validator) {
        super(validator);
        this.validator = validator;
        this.repository = repository;
    }

    IRepository<Integer, Review> getRepository() {
        return repository;
    }

    public List<Review> getReviewByReviewerAndStatus(User user, ReviewStatus status){
        return repository.getReviewByReviewerAndStatus(user, status);
    }

    public List<Paper> getPaperToBeReviewed(User user , ReviewStatus status){
        return getReviewByReviewerAndStatus(user, status).stream()
                .map(review -> review.getUserPaper().getPaper())
                .collect(Collectors.toList());
    }

    public List<Review> getReviewsByReviewer(User user) {
        return repository.getReviewsByReviewer(user);
    }

    public Review getReviewByReviewerAndPaper(User user, Paper paper) {
        return repository.getReviewByReviewerAndPaper(user, paper);
    }
}
