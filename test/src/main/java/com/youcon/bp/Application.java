package com.youcon.bp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(
    scanBasePackages = {"com.youcon"},

    exclude = {
        RedisAutoConfiguration.class,
    }
)
@ComponentScan(
    basePackages = {"com.youcon"}
)
@EnableJpaRepositories(basePackages = {
    "com.youcon.bp.cg.go.*.*"
})
@MapperScan("com.youcon.bp.cg.go")
@EntityScan(basePackages = {
    "com.youcon.bp.cg.go.*.*"
})
@EnableTransactionManagement
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
