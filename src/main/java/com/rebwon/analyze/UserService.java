package com.rebwon.analyze;

import java.util.List;

public final class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradleLevels() {
        List<User> users = userDao.findAll();

        for(User u : users) {
            if(u.getId() == 3) {
                throw new IllegalStateException();
            }
            upgradleLevel(u);
        }
    }

    private void upgradleLevel(User user) {
        user.upgradleLevel();
        userDao.update(user);
    }
}
