package com.kjq.langchain4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kjq.langchain4j.mapper")
public class Langchain4jApplication {

    public static void main(String[] args) {
        SpringApplication.run(Langchain4jApplication.class, args);
    }

}
