package repository;

import domain.Session;
import domain.User;
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
public class UserRepository implements IRepository<Integer, User> {
    private JdbcUtils dbutils;

    @Override
    public int size() {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from users")) {
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
    public void save(User entity) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into users(id,email, firstname, lastname, password, tag, username)" +
                " values (?,?,?, ?, ?, ?, ?)")){

            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getEmail());
            preStmt.setString(3,entity.getFirstname());
            preStmt.setString(4,entity.getLastname());
            preStmt.setString(5,entity.getPassword());
            preStmt.setString(6,entity.getTag());
            preStmt.setString(7,entity.getUsername());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from users where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, User entity) {

    }

    @Override
    public User findOne(Integer integer) {
        Connection con=dbutils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from users where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String email = result.getString("email");
                    String firstname = result.getString("firstname");
                    String lastname = result.getString("lastname");
                    String password  = result.getString("password");
                    String tag = result.getString("tag");
                    String username = result.getString("username");

                    User u = new User(id, email, firstname,lastname, password, tag, username);
                    return u;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        Connection con=dbutils.getConnection();
        List<User> users=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from users")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String email = result.getString("email");
                    String firstname = result.getString("firstname");
                    String lastname = result.getString("lastname");
                    String password  = result.getString("password");
                    String tag = result.getString("tag");
                    String username = result.getString("username");

                    User u = new User(id, email, firstname,lastname, password, tag, username);

                    users.add(u);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return users;
    }
}
