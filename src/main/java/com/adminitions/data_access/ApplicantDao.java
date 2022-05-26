package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Applicant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantDao extends BaseDao<Applicant> {

    public ApplicantDao(BasicConnectionPool connectionPool) {
        super(connectionPool);
    }

    private static final String SQL_SELECT_ALL =
            "select * from applicant";
    private static final String SQL_SELECT_BY_ID =
            "select * from applicant where id=?";
    private static final String SQL_SELECT_ID =
            "select id from applicant where last_name=? and `name`=? and surname=? and email=?;";
    private static final String SQL_INSERT =
            "INSERT INTO applicant (last_name, `name`, surname, email, city, region, " +
                    "name_educational_institution, attestation, `block`) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_DELETE_BY_ID =
            "delete from applicant where id=?;";

    private static final String SQL_DELETE_BY_NAME =
            "delete from applicant where last_name=? and `name`=? and surname=?;";

    private static final String SQL_UPDATE =
            "UPDATE applicant SET last_name=?, `name`=?, surname=?, email=?, city=?, region=?, name_educational_institution=?, attestation=?, `block`=? WHERE id=?;";

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

    public int findEntityId(Applicant applicant) throws DaoException {
        int id;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_ID);
            statement.setString(1, applicant.getLastName());
            statement.setString(2, applicant.getName());
            statement.setString(3, applicant.getSurname());
            statement.setString(4, applicant.getEmail());

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return id;
    }

    @Override
    public boolean delete(Applicant entity) throws DaoException {
        boolean deleteComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_DELETE_BY_NAME);
            statement.setString(1, entity.getLastName());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getSurname());
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
    public boolean create(Applicant entity) throws DaoException {
        boolean createComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);

            setBasicPrepareStatement(entity, statement);

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

    public boolean update(Applicant entity) throws DaoException {
        boolean createComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_UPDATE);

            setBasicPrepareStatement(entity, statement);
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

    private void setBasicPrepareStatement(Applicant entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getLastName());
        statement.setString(2, entity.getName());
        statement.setString(3, entity.getSurname());
        statement.setString(4, entity.getEmail());
        statement.setString(5, entity.getCity());
        statement.setString(6, entity.getRegion());
        statement.setString(7, entity.getNameEducationalInstitution());
        statement.setBlob(8, entity.getAttestation());
        statement.setBoolean(9, entity.isBlock());
    }
}
