package com.sikoraton.telegrambotr.bot;

import javax.annotation.PostConstruct;
import java.util.Set;

import com.sikoraton.tbt.service.NoCommandProcessing;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service @Setter
@ConfigurationProperties("bot")
public class BasicBot extends TelegramLongPollingCommandBot {
    private Logger logger = LogManager.getLogger(BasicBot.class);
    private static final Marker BASIC_BOT_MARKER = MarkerManager.getMarker("BASIC_BOT");
    private static final Marker INIT_MARKER = MarkerManager.getMarker("BASIC_BOT_INIT")
                                                            .addParents(BASIC_BOT_MARKER);
    private static final Marker SEND_MESSAGE = MarkerManager.getMarker("BASIC_BOT_SEND_MESSAGE")
                                                            .addParents(BASIC_BOT_MARKER);

    private String name;
    private String token;

    private NoCommandProcessing noCommandProcessing;

    @Autowired
    public BasicBot(Set<BotCommand> commands, NoCommandProcessing noCommandProcessing){
        for (BotCommand command : commands)
            register(command);
        this.noCommandProcessing = noCommandProcessing;
    }

    @PostConstruct
    void init(){
        try {
            new TelegramBotsApi(DefaultBotSession.class).registerBot(this);
        } catch (Throwable e) {
            logger.error(INIT_MARKER, e.getMessage(), e);
        }
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        BotApiMethodMessage message;
        if (update.hasMessage() && update.getMessage().hasText())
            message = noCommandProcessing.processing(update.getMessage());
        else
            message = SendMessage.builder()
                                    .chatId(update.getMessage().getChatId())
                                    .text("bot supportet only text message.")
                                    .build();
        sendMsg(message);
    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void sendMsg(BotApiMethodMessage message){
        try {
            this.sendApiMethod(message);
        } catch (TelegramApiException e) {
            logger.error(SEND_MESSAGE, e.getMessage(), e);
        }
    }
}
