package com.gdscpknu.gdscpknu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// DB 연결 없이 실행하기 위해 DataSourceAutoConfiguration 제외
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class GdscpknuApplication {

    public static void main(String[] args) {
        SpringApplication.run(GdscpknuApplication.class, args);
    }

}