package repository;

import domain.Edition;
import domain.Session;
import jdbc.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrei on 5/31/2017.
 */
public class SessionRepository implements IRepository<Integer, Session> {
    private JdbcUtils dbutils;


    @Override
    public int size() {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from sessions")) {
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
    public void save(Session entity) {

        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into sessions(id,date, location, edition_id)" +
                " values (?,?, ?, ?)")){

            preStmt.setInt(1,entity.getId());

            Date date = entity.getDate();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            preStmt.setDate(2,sqlDate);

            preStmt.setString(3,entity.getLocation());


            preStmt.setInt(4, entity.getEdition().getId());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }

    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from sessions where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Session entity) {

    }

    @Override
    public Session findOne(Integer integer) {
        Connection con=dbutils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from sessions where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    java.util.Date date= result.getDate("date");
                    String location = result.getString("location");
                    int edition_id = result.getInt("edition_id");
                    EditionRepository r = new EditionRepository();
                    Edition e = r.findOne(edition_id);

                    Session s = new Session(id, date, location, e);
                    return s;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<Session> findAll() {
        Connection con=dbutils.getConnection();
        List<Session> sessions=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from sessions")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    java.util.Date date= result.getDate("date");
                    String location = result.getString("location");
                    int edition_id = result.getInt("edition_id");
                    EditionRepository r = new EditionRepository();
                    Edition e = r.findOne(edition_id);


                    Session m = new Session(id, date, location, e);
                    sessions.add(m);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return sessions;
    }
}
