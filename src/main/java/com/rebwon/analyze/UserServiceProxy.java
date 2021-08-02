package com.rebwon.analyze;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public final class UserServiceProxy implements UserService {

    private PlatformTransactionManager transactionManager;
    private UserService userService;

    public UserServiceProxy(
        PlatformTransactionManager transactionManager,
        UserService userService) {
        this.transactionManager = transactionManager;
        this.userService = userService;
    }

    @Override
    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(
            new DefaultTransactionDefinition());

        try {
            userService.upgradeLevels();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new RuntimeException(e);
        }
    }
}
