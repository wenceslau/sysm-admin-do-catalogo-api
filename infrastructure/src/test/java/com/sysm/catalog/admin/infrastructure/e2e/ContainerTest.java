package com.sysm.catalog.admin.infrastructure.e2e;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.MySQLContainer;

public class ContainerTest {

    private static MySQLContainer mySQLContainer;

    protected static MySQLContainer getMySQLContainer() {
        if (mySQLContainer != null) {
           return mySQLContainer;
        }

        //The docker up this database using these credentials
        mySQLContainer = new MySQLContainer("mysql:8.0")
                .withPassword("123456")
                .withUsername("root")
                .withDatabaseName("adm_videos_test_e2e");
        return mySQLContainer;
    }

    protected static void registryPort(final DynamicPropertyRegistry registry) {

        //The application.properties file is using these properties to connect to the database
        registry.add("mysql.port", () -> mySQLContainer.getMappedPort(3306));
    }
}
