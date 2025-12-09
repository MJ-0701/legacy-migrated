package com.example.legacymigrated.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["com.example.legacymigrated.config"],
    entityManagerFactoryRef = "mysqlEntityManagerFactory",
    transactionManagerRef = "mysqlTransactionManager"
)
class MysqlConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.mysql")
    fun mysqlDataSource(): DataSource =
        DataSourceBuilder.create().build()

    @Bean
    fun mysqlEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("mysqlDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean =
        builder
            .dataSource(dataSource)
            .packages("com.example.legacymigrated.rdbms.mysql.entity") // MySQL용 엔티티 패키지
            .persistenceUnit("mysql")
            .build()

    @Bean
    fun mysqlTransactionManager(
        @Qualifier("mysqlEntityManagerFactory")
        emf: EntityManagerFactory
    ): PlatformTransactionManager =
        JpaTransactionManager(emf)
}