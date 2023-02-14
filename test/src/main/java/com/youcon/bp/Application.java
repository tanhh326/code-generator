package com.youcon.bp;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {"com.youcon.bp"},

    exclude= {
    DataSourceAutoConfiguration.class,
    RedisAutoConfiguration.class,
    MybatisAutoConfiguration.class
}
)
@EnableJpaRepositories
//@MapperScan("com.youcon.bp")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
