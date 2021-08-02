package com.rebwon.analyze;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AnalyzeTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyzeTransactionApplication.class, args);
    }

    //@Component
    static class ApplicationExecute implements ApplicationRunner {

        @Autowired
        UserService userService;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            userService.upgradleLevels();
        }
    }

}
