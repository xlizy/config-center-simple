package com.xlizy.middleware.cc.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author xlizy
 * @date 2019-01-09
 */
@SpringBootApplication
@EnableCaching
@MapperScan("com.xlizy.middleware.cc.server.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.run(args);
    }
}
