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
    entityManagerFactoryRef = "oracleEntityManagerFactory",
    transactionManagerRef = "oracleTransactionManager"
)
class OracleConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.oracle")
    fun oracleDataSource(): DataSource =
        DataSourceBuilder.create().build()

    @Bean
    fun oracleEntityManagerFactory(
        builder: EntityManagerFactoryBuilder,
        @Qualifier("oracleDataSource") dataSource: DataSource
    ): LocalContainerEntityManagerFactoryBean =
        builder
            .dataSource(dataSource)
            .packages("com.example.legacymigrated.rdbms.oracle.entity") // 오라클용 엔티티 패키지
            .persistenceUnit("oracle")
            .build()

    @Bean
    fun oracleTransactionManager(
        @Qualifier("oracleEntityManagerFactory")
        emf: EntityManagerFactory
    ): PlatformTransactionManager =
        JpaTransactionManager(emf)
}