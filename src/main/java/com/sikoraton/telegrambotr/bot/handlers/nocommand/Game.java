package com.sikoraton.telegrambotr.bot.handlers.nocommand;

import com.sikoraton.telegrambotr.bot.handlers.keyboards.GameKeyboard;
import com.sikoraton.telegrambotr.bot.handlers.keyboards.MainMenuKeyboard;
import com.sikoraton.telegrambotr.service.GameRoomService;
import com.sikoraton.telegrambotr.util.BotMenuText;
import com.sikoraton.telegrambotr.util.BotMessageText;
import com.sikoraton.telegrambotr.util.I10nHandler;
import com.sikoraton.telegrambotr.util.SessionCache;
import com.sikoraton.telegrambotr.util.UserCache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
@Getter @Setter
public class Game implements NoCommandHandler {
    private BotMenuText menuText;
    private BotMessageText messageText;
    private GameKeyboard gameKeyboard;
    private MainMenuKeyboard mainMenuKeyboard;
    private GameRoomService gameRoomService;
    private SessionCache sessionCache;

    @Autowired
    public Game(BotMenuText menuText,
                BotMessageText messageText,
                GameKeyboard gameKeyboard,
                MainMenuKeyboard mainMenuKeyboard,
                GameRoomService gameRoomService,
                SessionCache sessionCache) {
        this.menuText = menuText;
        this.messageText = messageText;
        this.gameKeyboard = gameKeyboard;
        this.mainMenuKeyboard = mainMenuKeyboard;
        this.gameRoomService = gameRoomService;
        this.sessionCache = sessionCache;
    }

    @Override
    public boolean isGoal(String text) {
        return menuText.getStartgame().containsValue(text) || menuText.getNextrow().containsValue(text);
    }

    @Override
    public BotApiMethodMessage processing(Message message) {
        UserCache userCache = sessionCache.getUserCache(message.getFrom().getId());
        ReplyKeyboardMarkup keyboard = gameKeyboard.buildKeyboard(userCache.getLanguageCode());
        String text = gameRoomService.getRandomRecordByRoomId(userCache.getRoomId());
        if (text == null){
            text = I10nHandler.getTextByLanguage(messageText.getNorow(), userCache.getLanguageCode());
            keyboard = mainMenuKeyboard.buildKeyboard(userCache.getLanguageCode());
        }
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }
}
