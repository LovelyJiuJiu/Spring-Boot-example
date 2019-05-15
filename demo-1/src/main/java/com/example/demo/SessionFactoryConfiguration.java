package com.example.demo;

import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class SessionFactoryConfiguration {
	
	@Value("${hibernate.show-sql}")
	private String SHOW_SQL;

	@Value("${hibernate.dialect}")
	private String DIALECT;
	
	@Value("${hibernate.current_session_context_class}")
	private String CURRENT_SESSION_CONTEXT_CLASS;
	
	@Value("${hibernate.hbm2ddl.auto}")
	private String HBM2DDL;
	
	@Autowired
	private DataSource dataSource;

//	@Bean
//	public JpaVendorAdapter jpaVendorAdapter() {
//        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
//        jpaVendorAdapter.setGenerateDdl(true);
//        jpaVendorAdapter.setShowSql(true);
//        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
//
//        return jpaVendorAdapter;
//    } 
	
	@Bean
	public EntityManagerFactory configureEntityManagerFactory() {
	    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
	    entityManagerFactoryBean.setDataSource(dataSource);
	    entityManagerFactoryBean.setPackagesToScan("com.example.demo");
	    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

	    Properties jpaProperties = new Properties();
	    jpaProperties.setProperty("hibernate.show_sql", SHOW_SQL);
	    jpaProperties.setProperty("hibernate.jdbc.fetch_size", "100");
	    jpaProperties.setProperty("hibernate.hibernate.dialect", DIALECT);
	    jpaProperties.setProperty("hibernate.current_session_context_class", CURRENT_SESSION_CONTEXT_CLASS);
	    jpaProperties.setProperty("hibernate.hbm2ddl.auto", HBM2DDL);
	    entityManagerFactoryBean.setJpaProperties(jpaProperties);

	    entityManagerFactoryBean.afterPropertiesSet();
	    return (EntityManagerFactory) entityManagerFactoryBean.getObject();
	}
	
	@Bean
	@Primary
	public SessionFactory getSessionFactory() {
	    return configureEntityManagerFactory()
	            .unwrap(SessionFactory.class);
	}
	
}
