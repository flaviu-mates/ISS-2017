package repository;

import domain.*;
import jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Andrei on 5/31/2017.
 */
public class ReviewRepository implements IRepository<Integer, Review> {
    private JdbcUtils dbutils;

    public ReviewRepository(JdbcUtils dbutils) {
        this.dbutils = dbutils;
    }

    @Override
    public int size() {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from reviews")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Review entity) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into reviews(comment, reviewStatus, user_id, paper_id)" +
                " values (?,?,?,?)")){

            preStmt.setString(1,entity.getComment());
            preStmt.setInt(2, entity.getReviewStatus().ordinal());
            preStmt.setInt(3, entity.getUserPaper().getUser().getId());
            preStmt.setInt(4, entity.getUserPaper().getPaper().getId());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from reviews where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Review entity) {

    }

    @Override
    public Review findOne(Integer integer) {
        Connection con=dbutils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from reviews where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String comment = result.getString("comment");
                    int reviewStatus_index = result.getInt("reviewStatus");
                    UserRepository userRepository = new UserRepository(dbutils);
                    User user = userRepository.findOne(result.getInt("user_id"));

                    PaperRepository paperRepository = new PaperRepository(dbutils);
                    Paper paper= paperRepository.findOne(result.getInt("paper_id"));


                    Review r = new Review(comment, ReviewStatus.values()[reviewStatus_index], user, paper);
                    return r;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<Review> findAll() {
        Connection con=dbutils.getConnection();
        List<Review> reviews =new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from reviews")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {

                    String comment = result.getString("comment");
                    int reviewStatus_index = result.getInt("reviewStatus");
                    UserRepository userRepository = new UserRepository(dbutils);
                    User user = userRepository.findOne(result.getInt("user_id"));

                    PaperRepository paperRepository = new PaperRepository(dbutils);
                    Paper paper= paperRepository.findOne(result.getInt("paper_id"));


                    Review r = new Review(comment, ReviewStatus.values()[reviewStatus_index], user, paper);
                    reviews.add(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return reviews;
    }

    public List<Review> getReviewByReviewerAndStatus(User user, ReviewStatus status) {
        List<Review> reviews = findAll();

        return reviews.stream().filter(review ->
                review.getReviewStatus().equals(status)
                        && review.getUserPaper().getUser().getId() == user.getId())
                .collect(Collectors.toList());
    }

    public List<Review> getReviewsByReviewer(User user) {
        return findAll().stream()
                .filter(review -> review.getUserPaper().getUser().getId() == user.getId())
                .collect(Collectors.toList());
    }

    public Review getReviewByReviewerAndPaper(User user,Paper paper) {
        List<Review> reviewer = findAll().stream()
                .filter(review -> review.getUserPaper().getUser().getId() == user.getId() &&
                review.getUserPaper().getPaper().getId() == paper.getId())
                .collect(Collectors.toList());

        return reviewer.size() == 1 ? reviewer.get(0) : null;
    }
}
