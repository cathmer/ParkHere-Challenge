package com.parkhere.reservation_service.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.TimeZone;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostgresConfig {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String dbUser;

  @Value("${spring.datasource.password}")
  private String dbPassword;

  @Bean
  public DataSource dataSource() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setJdbcUrl(dbUrl);
    dataSource.setUsername(dbUser);
    dataSource.setPassword(dbPassword);
    dataSource.setConnectionInitSql("SET TIME ZONE 'UTC'");
    dataSource.setAutoCommit(true);
    return dataSource;
  }
}
