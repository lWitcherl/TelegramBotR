package com.sikoraton.telegrambotr.util;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Setter @Getter
@ConfigurationProperties("menu")
public class BotMenuText {
    private HashMap<String, String> create;
    private HashMap<String, String> join;
    private HashMap<String, String> added;
    private HashMap<String, String> startgame;
    private HashMap<String, String> nextrow;
    private HashMap<String, String> finishgame;
    private HashMap<String, String> tostart;
}
