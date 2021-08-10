package com.rebwon.analyze;

import javax.sql.DataSource;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanPostProcessor;
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
    public TransactionAdvice transactionAdvice() {
        return new TransactionAdvice(platformTransactionManager());
    }

    @Bean
    public Pointcut transactionPointcut() {
        NameMatchClassMethodPointcut pointcut = new NameMatchClassMethodPointcut();
        pointcut.setMappedName("upgrade*");
        pointcut.setMappedClassName("*ServiceImpl");
        return pointcut;
    }

    @Bean
    public Advisor transactionAdvisor() {
        return new DefaultPointcutAdvisor(transactionPointcut(), transactionAdvice());
    }

    @Bean
    public BeanPostProcessor defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserServiceImpl(userDao);
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
