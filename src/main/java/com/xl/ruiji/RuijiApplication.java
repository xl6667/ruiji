package com.xl.ruiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RuijiApplication {
    public static void main(String[] args) {
        SpringApplication.run(RuijiApplication.class, args);
    }
}
