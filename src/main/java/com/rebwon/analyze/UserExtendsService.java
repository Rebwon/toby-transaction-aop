package com.rebwon.analyze;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public final class UserExtendsService extends UserServiceInheritance {
    private PlatformTransactionManager transactionManager;

    public UserExtendsService(UserDao userDao,
        PlatformTransactionManager transactionManager) {
        super(userDao);
        this.transactionManager = transactionManager;
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(
            new DefaultTransactionDefinition());
        try {
            super.upgradeLevels();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }
}
