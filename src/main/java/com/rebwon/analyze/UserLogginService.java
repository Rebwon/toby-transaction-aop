package com.rebwon.analyze;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UserLogginService implements UserService {
    private UserService userService;

    public UserLogginService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void upgradeLevels() {
        log.info("Start Logging");
        userService.upgradeLevels();
        log.info("End Logging");
    }
}
