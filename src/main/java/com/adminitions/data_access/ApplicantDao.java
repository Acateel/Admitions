package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.Entity;
import com.adminitions.entities.Faculty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantDao extends BaseDao<Applicant>{

    public ApplicantDao(BasicConnectionPool connectionPool) {
        super(connectionPool);
    }

    private static final String SQL_SELECT_ALL =
            "select * from applicant";
    private static final String SQL_SELECT_BY_ID =
            "select * from applicant where id=?";

    @Override
    public List<Applicant> findAll() throws DaoException {
        List<Applicant> applicants = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                applicants.add(parseResultSet(resultSet));
            }
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return applicants;
    }

    @Override
    public Applicant findEntityById(int id) throws DaoException {
        Applicant applicant;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            applicant = parseResultSet(resultSet);
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return applicant;
    }

    @Override
    boolean delete(Applicant entity) throws DaoException {
        return false;
    }

    @Override
    boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    boolean create(Applicant entity) throws DaoException {
        return false;
    }

    private Applicant parseResultSet(ResultSet resultSet) {
        Applicant applicant = new Applicant();
        try {
            applicant.setId(resultSet.getInt(1));
            applicant.setLastName(resultSet.getString(2));
            applicant.setName(resultSet.getString(3));
            applicant.setSurname(resultSet.getString(4));
            applicant.setEmail(resultSet.getString(5));
            applicant.setCity(resultSet.getString(6));
            applicant.setRegion(resultSet.getString(7));
            applicant.setNameEducationalInstitution(resultSet.getString(8));
            applicant.setAttestation(resultSet.getBlob(9));
            applicant.setBlock(resultSet.getBoolean(10));
        } catch (SQLException throwable) {
            // log
        }
        return applicant;
    }
}
