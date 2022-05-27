package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Entity;
import com.adminitions.entities.Faculty;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class RequestDao extends BaseDao<Request> {

    public static void main(String[] args) throws SQLException, DaoException {
        BasicConnectionPool pool = BasicConnectionPool.create(
                "jdbc:mysql://localhost:3306/admissions",
                "root",
                "pass"
        );
        RequestDao requestDao = new RequestDao(pool);

        Request request = new Request();
        request.setStatus(RequestStatus.NOT_PROCESSED);
        request.setFacultiesId(2);
        request.setApplicantId(2);
        request.setMainSubject(188);
        request.setSecondSubject(182);
        request.setSubSubject(179);
        request.setRatingScore(0);
        request.setAverageAttestationScore(10.3f);
        request.setPublishTime(new Time(new Date().getTime()));

        requestDao.create(request);
    }

    private static final String SQL_SELECT_ALL =
            "select * from request";
    private static final String SQL_SELECT_BY_ID =
            "select * from request where id=?";
    private static final String SQL_INSERT =
            "INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";

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
    public Request findEntityById(int id) throws DaoException {
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
        boolean createComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);
            prepareStatement(entity, statement);
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

    private void prepareStatement(Request entity, PreparedStatement statement) throws SQLException {
        statement.setString(1,RequestStatus.getStatusString(entity.getStatus()));
        statement.setInt(2, entity.getFacultiesId());
        statement.setInt(3, entity.getApplicantId());
        statement.setInt(4, entity.getMainSubject());
        statement.setInt(5, entity.getSecondSubject());
        statement.setInt(6, entity.getSubSubject());
        statement.setInt(7, entity.getRatingScore());
        statement.setFloat(8, entity.getAverageAttestationScore());
        statement.setTime(9, entity.getPublishTime());
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
