package ru.job4j.accidents.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Конфигурация средства миграции
 * @author Alexander Emelyanov
 * @version 1.0
 */
@Configuration
public class LiquibaseConfig {

    /**
     * Создание объекта миграции приложения,
     * параметры считываются из файла /resources/liquibase-changeLog.xml.
     *
     * @param ds источник данных
     * @return объект источника данных
     */
    @Bean
    public SpringLiquibase liquibase(DataSource ds) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
        liquibase.setDataSource(ds);
        return liquibase;
    }
}
