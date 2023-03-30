package com.onionit.ebank.configurations;

import com.onionit.ebank.EBankApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(basePackageClasses = EBankApplication.class)
@EnableTransactionManagement
public class JpaConfiguration {
    @Bean
    public DataSource getDataSource() {
        String host = Objects.requireNonNull(System.getenv("MYSQL_HOST"));
        String database = Objects.requireNonNull(System.getenv("MYSQL_DATABASE"));
        String user = System.getenv("MYSQL_USER");
        String password = System.getenv("MYSQL_PASSWORD");

        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.url(String.format("jdbc:mysql://%s/%s", host, database));

        if (!Objects.isNull(user)) {
            builder.username(user).password(password);
        }

        return builder.build();
    }
}
