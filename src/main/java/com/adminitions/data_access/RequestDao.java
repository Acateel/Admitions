package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDao extends BaseDao<Request> {

    private static final String SQL_SELECT_ALL =
            "select * from request";
    private static final String SQL_SELECT_BY_ID =
            "select * from request where id=?";
    private static final String SQL_SELECT_BY_FACULTY_ID =
            "select * from request where faculties_id=? order by rating_score desc;";
    private static final String SQL_SELECT_BY_FACULTY_AND_APPLICANT_ID =
            "SELECT * FROM request WHERE faculties_id=? AND applicant_id=?;";

    private static final String SQL_SELECT_STATUS_ORDER =
            "select * from request where faculties_id=? and `status`=? order by rating_score desc;";
    private static final String SQL_SELECT_BY_APPLICANT_ID_AND_STATUS =
            "select * from request where applicant_id=? and `status`=?";

    private static final String SQL_INSERT =
            "INSERT INTO request (`status`, faculties_id, applicant_id, main_subject, second_subject, sub_subject, rating_score, average_attestation_score, publish_time) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_DELETE_BY_ID =
            "delete from request where id=?;";

    private static final String SQL_DELETE_BY_NAME =
            "DELETE FROM request WHERE faculties_id=? AND applicant_id=?;";

    private static final String SQL_UPDATE =
            "UPDATE request SET `status`=?, faculties_id=?, applicant_id=?, main_subject=?, second_subject=?, sub_subject=?, rating_score=?, average_attestation_score=?, publish_time=? WHERE id=?;";

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

    public List<Request> findAllWithFaculty(int facultyId) throws DaoException {
        List<Request> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_FACULTY_ID);
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
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

    public List<Request> findAllWitStatus(int facultyId, RequestStatus status) throws DaoException {
        List<Request> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_STATUS_ORDER);
            statement.setInt(1, facultyId);
            statement.setString(2, RequestStatus.getStatusString(status));
            ResultSet resultSet = statement.executeQuery();
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

    public List<Request> findAllByApplicantId(int applicantId, RequestStatus status) throws DaoException {
        List<Request> requests = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_APPLICANT_ID_AND_STATUS);
            statement.setInt(1, applicantId);
            statement.setString(2, RequestStatus.getStatusString(status));
            ResultSet resultSet = statement.executeQuery();
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

    public boolean requestExist(int facultyId, int applicantId){
        boolean exist = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_FACULTY_AND_APPLICANT_ID);
            statement.setInt(1, facultyId);
            statement.setInt(2, applicantId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Request request = parseResultSet(resultSet);
            if (request != null || request.getId() != 0) {
                exist = true;
            }
        } catch (SQLException throwable) {
            // add log
        } finally {
            close(statement);
            close(connection);
        }
        return exist;
    }

    public Request findRequestByIds(int facultyId, int applicantId) throws DaoException {
        Request request = new Request();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_FACULTY_AND_APPLICANT_ID);
            statement.setInt(1, facultyId);
            statement.setInt(2, applicantId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            request = parseResultSet(resultSet);
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
            // add log
        } finally {
            close(statement);
            close(connection);
        }
        return request;
    }

    @Override
    public boolean delete(Request entity) throws DaoException {
        boolean deleteComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_BY_NAME);
            statement.setInt(1, entity.getFacultiesId());
            statement.setInt(2, entity.getApplicantId());
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
    public boolean create(Request entity) throws DaoException {
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

    public boolean update(Request entity) throws DaoException {
        boolean createComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);
            prepareStatement(entity, statement);
            statement.setInt(10, entity.getId());
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
        statement.setString(1, RequestStatus.getStatusString(entity.getStatus()));
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
