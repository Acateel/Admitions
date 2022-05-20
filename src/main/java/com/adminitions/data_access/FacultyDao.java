package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacultyDao extends BaseDao<Faculty> {

    public static void main(String[] args) throws SQLException, DaoException {
        BasicConnectionPool pool = BasicConnectionPool.create(
                "jdbc:mysql://localhost:3306/admissions",
                "root",
                "pass");
        FacultyDao facultyDao = new FacultyDao(pool);
        Faculty faculty = new Faculty();
        faculty.setName("URFAC");
        faculty.setBudgetSeats(15);
        faculty.setTotalSeats(25);
        boolean status = facultyDao.create(faculty);
        System.out.println(status);
    }
    private static final String SQL_SELECT_ALL =
            "select * from faculties";
    private static final String SQL_SELECT_BY_ID =
            "select * from faculties where id=?";
    private static final String SQL_INSERT =
            "INSERT INTO `Faculties` (faculty_name, budget_seats, total_seats) VALUES (?, ?, ?);";

    public FacultyDao(BasicConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public List<Faculty> findAll() throws DaoException {
        List<Faculty> faculties = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                faculties.add(parseResultSet(resultSet));
            }
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return faculties;
    }

    @Override
    Faculty findEntityById(int id) throws DaoException {
        Faculty faculty;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            faculty = parseResultSet(resultSet);
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return faculty;
    }

    @Override
    boolean delete(Faculty entity) throws DaoException {
        return false;
    }

    @Override
    boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    boolean create(Faculty entity) throws DaoException {
        boolean createComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getBudgetSeats());
            statement.setInt(3, entity.getTotalSeats());
            int changeCount = statement.executeUpdate();
            createComplete = changeCount > 0;
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return createComplete;
    }

    private Faculty parseResultSet(ResultSet resultSet) {
        Faculty faculty = new Faculty();
        try {
            faculty.setId(resultSet.getInt(1));
            faculty.setName(resultSet.getString(2));
            faculty.setBudgetSeats(resultSet.getInt(3));
            faculty.setTotalSeats(resultSet.getInt(4));
        } catch (SQLException throwable) {
            // log
        }
        return faculty;
    }
}
