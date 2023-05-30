package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @BelongsProject: ggblog
 * @BelongsPackage: org.example
 * @Author: dingquan
 * @CreateTime: 2023-05-22  17:08
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootApplication
@MapperScan("org.example.dao")
@EnableScheduling
@EnableSwagger2
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class,args);
    }
}
