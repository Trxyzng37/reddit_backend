//package com.trxyzng.trung.authentication.shared.beanConfigs;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class SqlConfig {
//    @Bean
//    @Primary
//    @ConfigurationProperties("spring.datasource.sql")
//    public DataSourceProperties sqlDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @Primary
//    public DataSource sqlDataSource() {
//        return sqlDataSourceProperties()
//                .initializeDataSourceBuilder()
//                .build();
//    }
//
////    @Bean
////    public JdbcTemplate sqlTemplate(@Qualifier("sqlDataSource") DataSource dataSource) {
////        return new JdbcTemplate(dataSource);
////    }
//}
