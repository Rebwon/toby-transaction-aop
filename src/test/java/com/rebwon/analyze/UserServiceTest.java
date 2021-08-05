package com.rebwon.analyze;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Proxy;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserDao userDao;
    @Autowired PlatformTransactionManager transactionManager;

    @Test
    void updateAllOrNothing() {
        try {
            userService.upgradeLevels();
            fail("Fail upgradeLevels");
        } catch (RuntimeException e) {

        }

        List<User> users = userDao.findAll();

        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);

        assertThat(user1.getLevel()).isEqualTo(1);
        assertThat(user2.getLevel()).isEqualTo(1);
        assertThat(user3.getLevel()).isEqualTo(1);
    }

    // 이 테스트는 UserServiceImpl에 if(id==3) 절을 주석처리하고 테스트 해볼 것.
    // 트랜잭션 처리가 정상적으로 커밋되고 로깅 작업이 잘 처리되는지 확인하기 위한 테스트.
    @Test
    void updateAllOrNothingUsingLogging() {
        userService.upgradeLevels();

        List<User> users = userDao.findAll();

        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);

        assertThat(user1.getLevel()).isEqualTo(2);
        assertThat(user2.getLevel()).isEqualTo(2);
        assertThat(user3.getLevel()).isEqualTo(2);
    }

    // JDK Dynamic Proxy를 사용한 테스트
    @Test
    void updateAllOrNothingUsingJDKDynamicProxy() {
        UserService txUserService = (UserService) Proxy.newProxyInstance(getClass().getClassLoader(),
            new Class[]{UserService.class},
            new TransactionHandler(userService, transactionManager, ""));
        txUserService.upgradeLevels();

        List<User> users = userDao.findAll();

        User user1 = users.get(0);
        User user2 = users.get(1);
        User user3 = users.get(2);

        assertThat(user1.getLevel()).isEqualTo(2);
        assertThat(user2.getLevel()).isEqualTo(2);
        assertThat(user3.getLevel()).isEqualTo(2);
    }
}