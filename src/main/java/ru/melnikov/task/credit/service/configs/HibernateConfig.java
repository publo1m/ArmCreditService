package ru.melnikov.task.credit.service.configs;

import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:hibernate.properties")
@RequiredArgsConstructor
public class HibernateConfig {

    private final Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("DRIVER_CLASS"));
        dataSource.setUrl(env.getRequiredProperty("CONNECTION_URL"));
        dataSource.setUsername(env.getRequiredProperty("CONNECTION_USERNAME"));
        dataSource.setPassword(env.getRequiredProperty("CONNECTION_PASSWORD"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("ru.melnikov.task.credit.service.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", env.getRequiredProperty("HIBERNATE.SHOW_SQL"));
        properties.put("hibernate.ddl_auto", env.getRequiredProperty("HIBERNATE.DDL_AUTO"));
        properties.put("hibernate.format_sql", env.getRequiredProperty("HIBERNATE.FORMAT_SQL"));
        return properties;
    }
}