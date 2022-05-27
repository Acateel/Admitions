package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FacultyDao extends BaseDao<Faculty> {

    private static final String SQL_SELECT_ALL =
            "select * from faculties";
    private static final String SQL_SELECT_BY_ID =
            "select * from faculties where id=?";
    private static final String SQL_INSERT =
            "INSERT INTO `Faculties` (faculty_name, budget_seats, total_seats) VALUES (?, ?, ?);";
    private static final String SQL_DELETE_BY_ID =
            "delete from faculties where id=?;";
    private static final String SQL_DELETE_BY_NAME =
            "delete from faculties where faculty_name=?;";
    private static final String SQL_UPDATE =
            "UPDATE faculties SET faculty_name=?, budget_seats=?, total_seats=? WHERE id=?;";

    private static final String SQL_SELECT_ALL_BY_NAME =
            "select * from faculties order by faculty_name;";
    private static final String SQL_SELECT_ALL_BY_BUDGET =
            "select * from faculties order by budget_seats;";
    private static final String SQL_SELECT_ALL_BY_TOTAL =
            "select * from faculties order by total_seats;";

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

    public List<Faculty> findAllByName() throws DaoException {
        List<Faculty> faculties = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_BY_NAME);
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

    public List<Faculty> findAllByNameRevers() throws DaoException {
        List<Faculty> faculties = findAllByName();
        Collections.reverse(faculties);
        return faculties;
    }

    public List<Faculty> findAllByBudget() throws DaoException {
        List<Faculty> faculties = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_BY_BUDGET);
            while (resultSet.next()) {
                faculties.add(parseResultSet(resultSet));
            }
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        Collections.reverse(faculties);
        return faculties;
    }

    public List<Faculty> findAllByTotal() throws DaoException {
        List<Faculty> faculties = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_BY_TOTAL);
            while (resultSet.next()) {
                faculties.add(parseResultSet(resultSet));
            }
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        Collections.reverse(faculties);
        return faculties;
    }

    @Override
    public Faculty findEntityById(int id) throws DaoException {
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
    public boolean delete(Faculty entity) throws DaoException {
        boolean deleteComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_BY_NAME);
            statement.setString(1, entity.getName());
            int changeCount = statement.executeUpdate();
            deleteComplete = changeCount > 0;
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return deleteComplete;
    }

    @Override
    public boolean delete(int id) throws DaoException {
        boolean deleteComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setInt(1, id);
            int changeCount = statement.executeUpdate();
            deleteComplete = changeCount > 0;
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return deleteComplete;
    }

    @Override
    public boolean create(Faculty entity) throws DaoException {
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

    public boolean update(Faculty entity) throws DaoException {
        boolean createComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getBudgetSeats());
            statement.setInt(3, entity.getTotalSeats());
            statement.setInt(4, entity.getId());
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
