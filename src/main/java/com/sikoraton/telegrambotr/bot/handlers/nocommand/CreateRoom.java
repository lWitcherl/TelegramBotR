package com.sikoraton.telegrambotr.bot.handlers.nocommand;

import com.sikoraton.tbt.bot.handlers.keyboards.MainMenuKeyboard;
import com.sikoraton.tbt.service.GameRoomService;
import com.sikoraton.tbt.util.BotMenuText;
import com.sikoraton.tbt.util.BotMessageText;
import com.sikoraton.tbt.util.I10nHandler;
import com.sikoraton.tbt.util.SessionCache;
import com.sikoraton.tbt.util.UserCache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@Setter @Getter
public class CreateRoom implements NoCommandHandler {
    private BotMenuText menuText;
    private BotMessageText messageText;
    private GameRoomService gameRoomService;
    private MainMenuKeyboard mainMenuKeyboard;
    private SessionCache sessionCache;

    @Autowired
    public CreateRoom(GameRoomService gameRoomService,
                      BotMenuText menuText,
                      BotMessageText messageText,
                      MainMenuKeyboard mainMenuKeyboard,
                      SessionCache sessionCache){
        this.menuText = menuText;
        this.gameRoomService = gameRoomService;
        this.messageText= messageText;
        this.mainMenuKeyboard = mainMenuKeyboard;
        this.sessionCache = sessionCache;
    }

    @Override
    public boolean isGoal(String text) {
        return menuText.getCreate().containsValue(text);
    }

    @Override
    public BotApiMethodMessage processing(Message message) {
        Long roomId = gameRoomService.createGameRoom(message.getFrom().getId());
        UserCache userCache = sessionCache.getUserCache(message.getFrom().getId());
        userCache.setRoomId(roomId);
        String text = String.format(I10nHandler.getTextByLanguage(messageText.getCreateroom(),
                                                                    userCache.getLanguageCode()),
                                                                    roomId);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(text)
                .replyMarkup(mainMenuKeyboard.buildKeyboard(userCache.getLanguageCode()))
                .build();
    }
}
