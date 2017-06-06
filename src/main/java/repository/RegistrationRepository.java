package repository;

import domain.Registration;
import jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RegistrationRepository implements IRepository<Integer, Registration> {
    private JdbcUtils dbutils;

    public RegistrationRepository(JdbcUtils dbutils) {
        this.dbutils = dbutils;
    }

    @Override
    public int size() {
        Connection con = dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from registration")) {
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
    public void save(Registration entity) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into registration(acquitted, user_id, edition_id)" +
                " values (?, ?, ?)")){

            preStmt.setBoolean(1, entity.isAcquitted());
            preStmt.setInt(2, entity.getUserEdition().getUser().getId());
            preStmt.setInt(3, entity.getUserEdition().getEdition().getId());

            int result = preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Registration entity) {

    }

    @Override
    public Registration findOne(Integer integer) {
        return null;
    }

    @Override
    public List<Registration> findAll() {
        return null;
    }
}
