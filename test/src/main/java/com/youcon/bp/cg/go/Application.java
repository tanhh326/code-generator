package com.youcon.bp.cg.go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(

    exclude = {
        RedisAutoConfiguration.class,
    }
)
@ComponentScan(
    basePackages = {
        "com.youcon.bp.cg.go.controller",
        "com.youcon.bp.cg.go.repository",
        "com.youcon.bp.cg.go.service"
    }
)
@EnableJpaRepositories(basePackages = {
    "com.youcon.bp.cg.go.repository"
})
@EntityScan(basePackages = {
    "com.youcon.bp.cg.go.engity"
})
@EnableTransactionManagement
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
