package com.semih.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = "com.semih")
@EntityScan(basePackages = "com.semih")
@EnableJpaRepositories(basePackages = "com.semih")
@SpringBootApplication
public class YardimKuruluslariErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(YardimKuruluslariErpApplication.class, args);
    }

}
