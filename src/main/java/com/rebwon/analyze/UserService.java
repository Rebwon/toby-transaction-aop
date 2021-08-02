package com.rebwon.analyze;

import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public final class UserService {

    private DataSource dataSource;
    private UserDao userDao;

    public UserService(DataSource dataSource, UserDao userDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
    }

    public void upgradleLevels() throws Exception {
        TransactionSynchronizationManager.initSynchronization();
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.setAutoCommit(false);

        try {
            List<User> users = userDao.findAll();

            for(User u : users) {
                if(u.getId() == 3) {
                    throw new IllegalStateException();
                }
                upgradleLevel(u);
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw new RuntimeException(e);
        } finally {
            DataSourceUtils.releaseConnection(connection, dataSource);
            TransactionSynchronizationManager.unbindResource(this.dataSource);
            TransactionSynchronizationManager.clearSynchronization();
        }
    }

    private void upgradleLevel(User user) {
        user.upgradleLevel();
        userDao.update(user);
    }
}
