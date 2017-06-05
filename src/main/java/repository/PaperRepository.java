package repository;

import domain.Paper;
import domain.PaperStatus;
import domain.Session;
import domain.User;
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
public class PaperRepository implements IRepository<Integer, Paper> {
    private JdbcUtils dbutils;

    public PaperRepository(JdbcUtils dbutils) {
        this.dbutils = dbutils;
    }

    @Override
    public int size() {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from papers")) {
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
    public void save(Paper entity) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into papers(paperStatus, title, topic, session_id, user_id)" +
                " values (?,?,?,?,?)")){

            preStmt.setInt(1, entity.getPaperStatus().ordinal());
            preStmt.setString(2, entity.getTitle());
            preStmt.setString(3, entity.getTopic());
            preStmt.setInt(4, entity.getSession().getId());
            preStmt.setInt(5, entity.getUser().getId());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from papers where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Paper entity) {

    }

    @Override
    public Paper findOne(Integer integer) {
        Connection con=dbutils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from papers where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int paperStatus_index = result.getInt("paperStatus");
                    String title = result.getString("title");
                    String topic = result.getString("topic");

                    SessionRepository sr = new SessionRepository(dbutils);
                    Session session  = new Session();
                    session = sr.findOne(result.getInt("session_id"));

                    UserRepository ur = new UserRepository(dbutils);
                    User user = new User();
                    user = ur.findOne(result.getInt("user_id"));

                    Paper p = new Paper(id, PaperStatus.values()[paperStatus_index], title, topic, session, user);
                    return p;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<Paper> findAll() {
        Connection con=dbutils.getConnection();
        List<Paper> papers=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from papers")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int paperStatus_index = result.getInt("paperStatus");
                    String title = result.getString("title");
                    String topic = result.getString("topic");

                    SessionRepository sr = new SessionRepository(dbutils);
                    Session session  = new Session();
                    session = sr.findOne(result.getInt("session_id"));

                    UserRepository ur = new UserRepository(dbutils);
                    User user = new User();
                    user = ur.findOne(result.getInt("user_id"));



                    Paper p = new Paper(id, PaperStatus.values()[paperStatus_index], title, topic, session, user);
                    papers.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return papers;
    }

    public List<Paper> getPapersFromAuthor(User author) {
        return findAll().stream()
                .filter(paper -> paper.getUser().getId() == author.getId())
                .collect(Collectors.toList());
    }
}
