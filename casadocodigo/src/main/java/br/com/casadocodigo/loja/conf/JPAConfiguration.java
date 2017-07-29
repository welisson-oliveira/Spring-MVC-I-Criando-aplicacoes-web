package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.activation.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * 
 * @author focusnetworks
 * A Classe {@link JPAConfiguration} sera utilizada pelo Spring para
 * gerar o {@link EntityManager} pois esse precisa de uma adapter e estamos
 * passando um que o Hibernate disponibiliza.\n
 * Possui tambem um {@link DataSource} que contem as configurações de banco de dados.
 * 
 *
 */
@EnableTransactionManagement //utilizado para que a classe JPAConfiguration possa gerenciar as transações
public class JPAConfiguration {

	
	@Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        factoryBean.setJpaVendorAdapter(vendorAdapter);

        // configurações do banco de dados a ser utilizado
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        factoryBean.setDataSource(dataSource);
        
        // configurações para o banco de dados
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.hbm2ddl.auto", "update");

        factoryBean.setJpaProperties(props);
    	
        // informa onde o EntityManager encontrara os models
        factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");

        return factoryBean;
    }
	
	/**
	 * 
	 * @param entityManager
	 * @return
	 * Assim que o Spring ativar o gerenciamento de transações ele passará a reconhecer o TransactionManager
	 */
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
		return new JpaTransactionManager(entityManagerFactory);
	}
}
