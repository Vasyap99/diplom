package kok.spring21.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.core.env.Environment;

import org.springframework.orm.hibernate5.*;
import org.springframework.transaction.*;
import org.springframework.jdbc.*;

import org.springframework.jdbc.datasource.*;

import javax.sql.DataSource;

import java.util.Properties;

//настройка Hibernate
@Configuration
@ComponentScan("kok.spring21")           //skanirovanie proishodit takzhe vo vlozhennyh papkah rekursivno
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
public class PersistenceConfig{

	private final Environment env;
	private final ApplicationContext ac;
	
	@Autowired
	public PersistenceConfig(ApplicationContext c,Environment env){ac=c;this.env=env;}

    private Properties hibernateProperties(){
		Properties properties=new Properties();
		properties.put("hibernate.dialect",env.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.show_sql",env.getRequiredProperty("hibernate.show_sql"));
		return properties;
    }

	@Bean
    LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan("kok.spring21.models");
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
    }

	@Bean
	public PlatformTransactionManager hibernateTransactionManager(){
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory().getObject());
		return transactionManager;
	}

	@Bean
    DataSource dataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getRequiredProperty("hibernate.driver_class"));
		dataSource.setUrl(env.getRequiredProperty("hibernate.connection.url"));
		dataSource.setUsername(env.getRequiredProperty("hibernate.connection.username"));

		dataSource.setPassword(env.getRequiredProperty("hibernate.connection.password"));
		return dataSource;
    }

}
