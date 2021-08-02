package com.rebwon.analyze;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
public class AppConfig {

    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate) {
        return new UserDao(jdbcTemplate);
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserService(dataSource(), userDao);
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("classpath:jdbc/schema.sql")
            .addScript("classpath:jdbc/test-data.sql")
            .build();
    }
}
