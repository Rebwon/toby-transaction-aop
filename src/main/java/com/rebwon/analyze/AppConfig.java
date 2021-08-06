package com.rebwon.analyze;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AppConfig {

    @Bean
    public UserDao userDao(JdbcTemplate jdbcTemplate) {
        return new UserDao(jdbcTemplate);
    }

    @Bean
    public UserService userService(UserDao userDao) throws Exception {
        JdkDynamicProxyBeanFactory beanFactory = new JdkDynamicProxyBeanFactory(
            new UserServiceImpl(userDao),
            platformTransactionManager(),
            "",
            UserService.class
        );
        return (UserService) beanFactory.getObject();
    }

//    @Bean
//    public UserService decoratorUserService(UserDao userDao) {
//        return new UserServiceProxy(platformTransactionManager(), new UserLogginService(new UserServiceImpl(userDao)));
//    }

    @Bean
    public UserServiceInheritance userServiceInheritance(UserDao userDao) {
        return new UserExtendsService(userDao, platformTransactionManager());
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
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
