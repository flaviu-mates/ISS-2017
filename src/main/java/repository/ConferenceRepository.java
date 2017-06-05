package repository;

import domain.Conference;
import jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrei on 5/31/2017.
 */

public class ConferenceRepository implements IRepository<Integer, Conference> {
    private JdbcUtils dbutils;

    public ConferenceRepository(JdbcUtils dbutils) {
        this.dbutils = dbutils;
    }

    @Override
    public int size() {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from conferences")) {
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
    public void save(Conference entity) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into conferences(name)" +
                " values (?)")){

            preStmt.setString(1,entity.getName());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer integer) {

        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from conferences where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Conference entity) {

    }

    @Override
    public Conference findOne(Integer integer) {
        Connection con=dbutils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from conferences where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");

                    Conference m = new Conference(id, name);
                    return m;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<Conference> findAll() {
        Connection con=dbutils.getConnection();
        List<Conference> conferences=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from conferences")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String name = result.getString("name");

                    Conference m = new Conference(id, name);
                    conferences.add(m);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return conferences;
    }
}
