package repository;

import domain.Conference;
import domain.Edition;
import jdbc.JdbcUtils;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andrei on 5/31/2017.
 */
public class EditionRepository implements IRepository<Integer, Edition> {
    private JdbcUtils dbutils;

    public EditionRepository(JdbcUtils dbutils) {
        this.dbutils = dbutils;
    }

    @Override
    public int size() {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from editions")) {
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
    public void save(Edition entity) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into editions(id, begin, deadline, end, name, paperDeadline,conference_id)" +
                " values (?,?,?,?,?,?,?)")){

            preStmt.setInt(1,entity.getId());

            java.util.Date date = entity.getBegin();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            preStmt.setDate(2,sqlDate);

            date = entity.getDeadline();
            sqlDate = new java.sql.Date(date.getTime());

            preStmt.setDate(3,sqlDate);

            date = entity.getEnd();
            sqlDate = new java.sql.Date(date.getTime());

            preStmt.setDate(4,sqlDate);
            preStmt.setString(5,entity.getName());

            date = entity.getPaperDeadline();
            sqlDate = new java.sql.Date(date.getTime());

            preStmt.setDate(6,sqlDate);
            preStmt.setInt(7,entity.getConference().getId());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void delete(Integer integer) {
        Connection con=dbutils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from editions where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
    }

    @Override
    public void update(Integer integer, Edition entity) {

    }

    @Override
    public Edition findOne(Integer integer) {
        Connection con=dbutils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from editions where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    java.util.Date begin= result.getDate("begin");
                    java.util.Date deadline= result.getDate("deadline");
                    java.util.Date end= result.getDate("end");
                    java.util.Date paperdeadline= result.getDate("paperDeadline");
                    String name = result.getString("name");
                    ConferenceRepository r = new ConferenceRepository(dbutils);
                    Conference c = (r.findOne(result.getInt("conference_id")));
                    Edition m = new Edition(id, name, begin, end, c, deadline, paperdeadline);
                    return m;
                }
            }
        }catch (SQLException ex){
            System.out.println("Error DB "+ex);
        }
        return null;
    }

    @Override
    public List<Edition> findAll() {
        Connection con=dbutils.getConnection();
        List<Edition> editions=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from editions")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    java.util.Date begin= result.getDate("begin");
                    java.util.Date deadline= result.getDate("deadline");
                    java.util.Date end= result.getDate("end");
                    java.util.Date paperdeadline= result.getDate("paperDeadline");
                    String name = result.getString("name");
                    ConferenceRepository r = new ConferenceRepository(dbutils);
                    Conference c = (r.findOne(result.getInt("conference_id")));
                    Edition m = new Edition(id, name, begin, end, c, deadline, paperdeadline);
                    editions.add(m);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return editions;
    }

    public List<Edition> getEditionAfterDate(java.util.Date date) {
        return findAll().stream()
                .filter(edition -> date.compareTo(edition.getPaperDeadline()) < 0)
                .collect(Collectors.toList());
    }
}
