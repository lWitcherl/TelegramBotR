package com.sikoraton.telegrambotr.util;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Setter @Getter
@ConfigurationProperties("text")
public class BotMessageText {
    private HashMap<String, String> start;
    private HashMap<String, String> createroom;
    private HashMap<String, String> joinroom;
    private HashMap<String, String> unknowncommand;
    private HashMap<String, String> notatext;
    private HashMap<String, String> rowadded;
    private HashMap<String, String> createrow;
    private HashMap<String, String> joincomplicte;
    private HashMap<String, String> roomnotfound;
    private HashMap<String, String> main;
    private HashMap<String, String> startmenu;
    private HashMap<String, String> norow;
}
