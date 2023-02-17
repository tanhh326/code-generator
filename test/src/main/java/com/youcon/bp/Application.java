package com.youcon.bp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {"com.youcon"},

    exclude = {
        RedisAutoConfiguration.class,
    }
)
@EnableJpaRepositories(basePackages = {
    "com.youcon.bp.cg.go.repository.*.*", "com.youcon.bp.cg.go.link"
})
@MapperScan("com.youcon.bp.cg.go.mapper")
@EntityScan(basePackages = {
    "com.youcon.bp.cg.go.module.db.*.*","com.youcon.bp.cg.go.link"
})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
