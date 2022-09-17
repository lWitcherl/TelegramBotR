package com.sikoraton.telegrambotr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.sikoraton.tbt.entity",
                                            "com.sikoraton.tbt.service",
                                            "com.sikoraton.tbt.util",
                                            "com.sikoraton.tbt.bot"})
@EnableJpaRepositories("com.sikoraton.tbt.repository")
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
