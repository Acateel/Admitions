package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.Applicant;
import com.adminitions.entities.users.Role;
import com.adminitions.entities.users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao<User> {

    public static void main(String[] args) throws SQLException, DaoException {
        BasicConnectionPool pool = BasicConnectionPool.create(
                "jdbc:mysql://localhost:3306/admissions",
                "root",
                "pass"
        );

        UserDao dao = new UserDao(pool);

        User user = new User();
        user.setLogin("admin");
        user.setPassword("admin");
        user.setRole(Role.ADMIN);

        System.out.println(dao.create(user));
    }

    private static final String SQL_SELECT_ALL =
            "select * from `user`";
    private static final String SQL_SELECT_BY_ID =
            "select * from user where id=?";
    private static final String SQL_INSERT =
            "INSERT INTO `user` (login, `password`, `role`, applicant_id) VALUES (?, ?, ?, ?);";

    public UserDao(BasicConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
            while (resultSet.next()) {
                users.add(parseResultSet(resultSet));
            }
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return users;
    }

    @Override
    User findEntityById(int id) throws DaoException {
        User user;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = parseResultSet(resultSet);
        } catch (SQLException throwable) {
            throw new DaoException(throwable.getMessage());
        } finally {
            close(statement);
            close(connection);
        }
        return user;
    }

    @Override
    boolean delete(User entity) throws DaoException {
        return false;
    }

    @Override
    boolean delete(int id) throws DaoException {
        return false;
    }

    @Override
    boolean create(User entity) throws DaoException {
        boolean createComplete;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            statement = connection.prepareStatement(SQL_INSERT);

            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getPassword());
            statement.setString(3, parseRoleToString(entity.getRole()));
            statement.setInt(4, entity.getApplicantId());

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

    private User parseResultSet(ResultSet resultSet) {
        User user = new User();
        try {
            user.setId(resultSet.getInt(1));
            user.setLogin(resultSet.getString(2));
            user.setPassword(resultSet.getString(3));
            user.setRole(parseRole(resultSet.getString(4)));
            user.setApplicantId(resultSet.getInt(5));
        } catch (SQLException throwable) {
            // log
        }
        return user;
    }

    private Role parseRole(String strRole) {
        switch (strRole) {
            case "admin":
                return Role.ADMIN;
            case "applicant":
                return Role.APPLICANT;
            default:
                return Role.UNKNOWN;
        }
    }

    private String parseRoleToString(Role role) throws DaoException {
        switch (role) {
            case ADMIN:
                return "admin";
            case APPLICANT:
                return "applicant";
            default:
                throw new DaoException("Role not found for DB");
        }
    }
}
