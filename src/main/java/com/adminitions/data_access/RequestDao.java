package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Entity;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDao extends BaseDao<Request> {

    public static void main(String[] args) throws SQLException, DaoException {
        BasicConnectionPool pool = BasicConnectionPool.create(
                "jdbc:mysql://localhost:3306/admissions",
                "root",
                "pass"
        );
        RequestDao requestDao = new RequestDao(pool);

        System.out.println(requestDao.findEntityById(1));
    }
    private static final String SQL_SELECT_ALL =
            "select * from request";
    private static final String SQL_SELECT_BY_ID =
            "select * from request where id=?";

    public RequestDao(BasicConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public List<Request> findAll() throws DaoException {
        List<Request> requests = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                requests.add(parseResultSet(resultSet));
            }
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return requests;
    }

    @Override
    Request findEntityById(int id) throws DaoException {
        Request request;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            request = parseResultSet(resultSet);
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return request;
    }

    @Override
    boolean delete(Request entity) throws DaoException {
        return false;
    }

    @Override
    boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    boolean create(Request entity) throws DaoException {
        return false;
    }

    private Request parseResultSet(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setId(resultSet.getInt(1));
        request.setStatus(RequestStatus.getStatus(resultSet.getString(2)));
        request.setFacultiesId(resultSet.getInt(3));
        request.setApplicantId(resultSet.getInt(4));
        request.setMainSubject(resultSet.getInt(5));
        request.setSecondSubject(resultSet.getInt(6));
        request.setSubSubject(resultSet.getInt(7));
        request.setRatingScore(resultSet.getInt(8));
        request.setAverageAttestationScore(resultSet.getFloat(9));
        request.setPublishTime(resultSet.getTime(10));
        return request;
    }
}
