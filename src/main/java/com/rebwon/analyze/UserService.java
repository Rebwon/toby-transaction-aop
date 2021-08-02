package com.rebwon.analyze;

import java.sql.Connection;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public final class UserService {

    private DataSource dataSource;
    private UserDao userDao;

    public UserService(DataSource dataSource, UserDao userDao) {
        this.dataSource = dataSource;
        this.userDao = userDao;
    }

    public void upgradleLevels() throws Exception {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        TransactionStatus status = transactionManager.getTransaction(
            new DefaultTransactionDefinition());

        try {
            List<User> users = userDao.findAll();
            for(User u : users) {
                if(u.getId() == 3) {
                    throw new IllegalStateException();
                }
                upgradleLevel(u);
            }

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    private void upgradleLevel(User user) {
        user.upgradleLevel();
        userDao.update(user);
    }
}
