package com.rebwon.analyze;

import java.util.List;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public final class UserService {

    private PlatformTransactionManager transactionManager;
    private UserDao userDao;

    public UserService(
        PlatformTransactionManager transactionManager,
        UserDao userDao) {
        this.transactionManager = transactionManager;
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(
            new DefaultTransactionDefinition());

        try {
            upgradeLevelsInternal();

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }

    private void upgradeLevelsInternal() {
        List<User> users = userDao.findAll();
        for(User u : users) {
            if(u.getId() == 3) {
                throw new IllegalStateException();
            }
            upgradeLevel(u);
        }
    }

    private void upgradeLevel(User user) {
        user.upgradleLevel();
        userDao.update(user);
    }
}
