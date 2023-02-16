package com.youcon.bp;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {"com.youcon.bp"},

    exclude= {
//    DataSourceAutoConfiguration.class,
    RedisAutoConfiguration.class,
//    MybatisAutoConfiguration.class
}
)
@EnableJpaRepositories(basePackages = {
    "com.youcon.bp.cg.go.*.*"
})
@MapperScan("com.youcon.bp.cg")
@EntityScan(basePackages = {
    "com.youcon.bp.cg.go.*.*"
})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
