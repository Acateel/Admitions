package com.adminitions.data_access;

import com.adminitions.data_access.connection_pool.BasicConnectionPool;
import com.adminitions.entities.users.User;

import java.util.List;

public class UserDao extends BaseDao<User> {
    public UserDao(BasicConnectionPool connectionPool) {
        super(connectionPool);
    }

    @Override
    List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    User findEntityById(int id) throws DaoException {
        return null;
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
        return false;
    }
}
