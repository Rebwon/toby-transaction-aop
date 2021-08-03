package com.rebwon.analyze;

import java.util.List;

public class UserServiceInheritance {

    private UserDao userDao;

    public UserServiceInheritance(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
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
