package com.rebwon.analyze;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceInheritanceTest {

    @Autowired UserServiceInheritance userServiceInheritance;
    @Autowired UserDao userDao;

    @Test
    void updateAllOrNothing() {
        try {
            userServiceInheritance.upgradeLevels();
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
}