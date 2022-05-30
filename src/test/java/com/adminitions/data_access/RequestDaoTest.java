package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.request.Request;
import com.adminitions.entities.request.RequestStatus;
import com.mysql.cj.jdbc.result.ResultSetImpl;
import com.oracle.wls.shaded.org.apache.xalan.lib.sql.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sun.management.VMOptionCompositeData;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

class RequestDaoTest {

    RequestDao requestDao;
    BasicConnectionPool poll;
    Connection connection;
    Statement statement;
    PreparedStatement preparedStatement;
    Request request;
    @BeforeEach
    void setUp() throws SQLException {
        poll = mock(BasicConnectionPool.class);
        connection = mock(Connection.class);
        when(poll.getConnection()).thenReturn(connection);
        requestDao = new RequestDao(poll);

        statement = mock(Statement.class);
        preparedStatement = mock(PreparedStatement.class);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(false);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        request = new Request();
        request.setStatus(RequestStatus.NOT_PROCESSED);
        when(resultSet.getInt(1)).thenReturn(request.getId());
        when(resultSet.getString(2)).thenReturn(RequestStatus.getStatusString(request.getStatus()));
        when(resultSet.getInt(3)).thenReturn(request.getFacultiesId());
        when(resultSet.getInt(4)).thenReturn(request.getApplicantId());
        when(resultSet.getInt(5)).thenReturn(request.getMainSubject());
        when(resultSet.getInt(6)).thenReturn(request.getSecondSubject());
        when(resultSet.getInt(7)).thenReturn(request.getSubSubject());
        when(resultSet.getInt(8)).thenReturn(request.getRatingScore());
        when(resultSet.getFloat(9)).thenReturn(request.getAverageAttestationScore());
        when(resultSet.getTime(10)).thenReturn(request.getPublishTime());
    }

    @Test
    void findAll() throws DaoException, SQLException {
        requestDao.findAll();
        verify(statement).executeQuery(RequestDao.SQL_SELECT_ALL);
    }

    @Test
    void findAllWithFaculty() throws DaoException, SQLException {
        requestDao.findAllWithFaculty(1);
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void findAllWitStatus() throws DaoException, SQLException {
        requestDao.findAllWitStatus(1, RequestStatus.ALLOWED);
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setString(2, RequestStatus.getStatusString(RequestStatus.ALLOWED));
        verify(preparedStatement).executeQuery();
    }

    @Test
    void findAllByApplicantId() throws DaoException, SQLException {
        requestDao.findAllByApplicantId(1, RequestStatus.ALLOWED);
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setString(2, RequestStatus.getStatusString(RequestStatus.ALLOWED));
        verify(preparedStatement).executeQuery();
    }

    @Test
    void findEntityById() throws DaoException, SQLException {
        requestDao.findEntityById(1);
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void requestExist() throws SQLException {
        requestDao.requestExist(1,1);
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void findRequestByIds() throws DaoException, SQLException {
        requestDao.findRequestByIds(1,1);
        verify(preparedStatement).setInt(1,1);
        verify(preparedStatement).setInt(2,1);
        verify(preparedStatement).executeQuery();
    }

    @Test
    void delete() throws DaoException, SQLException {
        requestDao.delete(new Request());
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void testDelete() throws SQLException, DaoException {
        requestDao.delete(1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void create() throws SQLException, DaoException {
        requestDao.create(request);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    void update() throws DaoException, SQLException {
        requestDao.update(request);
        verify(preparedStatement).executeUpdate();
    }
}