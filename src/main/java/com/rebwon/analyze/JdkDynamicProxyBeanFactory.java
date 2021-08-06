package com.rebwon.analyze;

import java.lang.reflect.Proxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public final class JdkDynamicProxyBeanFactory implements FactoryBean<Object> {
    private Object target;
    private PlatformTransactionManager transactionManager;
    private String pattern;
    private Class<?> serviceInterface;  // UserService 외에 인터페이스를 생성할 때 필요함.

    public JdkDynamicProxyBeanFactory(Object target,
        PlatformTransactionManager transactionManager, String pattern,
        Class<?> serviceInterface) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object getObject() throws Exception {
        TransactionHandler transactionHandler = new TransactionHandler(
            target, transactionManager, pattern);
        return Proxy.newProxyInstance(getClass().getClassLoader(),
            new Class[]{ serviceInterface }, transactionHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }
}
