package mx.unam.dgtic.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("mx.unam.dgtic")
public class AppConfig {
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mariadb://localhost:3306/modulo6?useSSL=false&serverTimezone=UTC");
        config.setUsername("appuser");
        config.setPassword("MiPasswordSegura123!");
        config.setDriverClassName("org.mariadb.jdbc.Driver");
        return new HikariDataSource(config);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource){
        LocalContainerEntityManagerFactoryBean emfb =
                new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emfb.setPackagesToScan("mx.unam.dgtic");

        Properties jpaProperties = new Properties();
        //org.hibernate.dialect.MariaDB103Dialect
        jpaProperties.put("spring.sql.init.mode","always");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto","create");

        emfb.setJpaProperties(jpaProperties);

        return emfb;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);

        // Populador con los scripts
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        // Cargamos schema.sql (creación de tablas)
        // populator.addScript(new ClassPathResource("schema.sql"));
        // Cargamos data.sql (inserción de datos)
        //populator.addScript(new ClassPathResource("data.sql"));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
