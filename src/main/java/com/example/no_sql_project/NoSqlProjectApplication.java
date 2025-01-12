package com.example.no_sql_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "repository", "model"})
@EnableMongoRepositories(basePackages = "repository")
public class NoSqlProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoSqlProjectApplication.class, args);
    }
}
