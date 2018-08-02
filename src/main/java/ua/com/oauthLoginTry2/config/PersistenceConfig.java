package ua.com.oauthLoginTry2.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages= {"ua.com.oauthLoginTry2.dao", "ua.com.oauthLoginTry2.service"})
@PropertySource("classpath:persistence/database.properties")
public class PersistenceConfig {
	
	private Database database = Database.MYSQL;
	private static final String DIALECT = "hibernate.dialect";
	private static final String HBM2_DDL = "hibernate.hbm2ddl.auto";
	private static final String SHOW_SQL = "hibernate.show_sql";
	
	@Autowired
	private Environment env;
	
	@Bean("dataSource")
	public DataSource getDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("driverClassName"));
		hikariConfig.setJdbcUrl(env.getProperty("jdbcUrl"));
		hikariConfig.setUsername(env.getProperty("db.username"));
		hikariConfig.setPassword(env.getProperty("db.password"));
		hikariConfig.setIdleTimeout(Long.parseLong(env.getProperty("idleTimeout")));
		hikariConfig.setMaximumPoolSize(Integer.parseInt(env.getProperty("maximumPoolSize")));
		hikariConfig.setMinimumIdle(Integer.parseInt(env.getProperty("minimumIdle")));
		hikariConfig.setConnectionTimeout(Long.parseLong(env.getProperty("connectionTimeout")));
		
		return new HikariDataSource(hikariConfig);
	}
	
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean geEntityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setDataSource(getDataSource());
		factoryBean.setJpaVendorAdapter(getVendorAdapter());
		factoryBean.setPackagesToScan(env.getProperty("package.to.scan"));
		
		Properties jpaProperties = new Properties();
		jpaProperties.put(DIALECT, env.getProperty("db.dialect"));
		jpaProperties.put(HBM2_DDL, env.getProperty("db.hbm.value"));
		jpaProperties.put(SHOW_SQL, env.getProperty("db.showSQL.value") );
		
		factoryBean.setJpaProperties(jpaProperties);
		
		return factoryBean;
	}
	
	@Bean(name="transactionManager")
	public JpaTransactionManager getJpaTransactionManager(EntityManagerFactory factory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(factory);
		
		return transactionManager;
	}
	
	@Bean
	public HibernateJpaVendorAdapter getVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(database);
		
		return hibernateJpaVendorAdapter;
	}
}
