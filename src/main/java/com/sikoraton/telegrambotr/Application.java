package com.sikoraton.telegrambotr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.sikoraton.telegrambotr.entity",
                                            "com.sikoraton.telegrambotr.service",
                                            "com.sikoraton.telegrambotr.util",
                                            "com.sikoraton.telegrambotr.bot"})
@EnableJpaRepositories("com.sikoraton.telegrambotr.repository")
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
