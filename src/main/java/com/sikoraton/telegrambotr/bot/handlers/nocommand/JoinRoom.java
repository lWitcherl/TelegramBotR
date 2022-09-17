package com.sikoraton.telegrambotr.bot.handlers.nocommand;

import com.sikoraton.tbt.bot.handlers.keyboards.MainMenuKeyboard;
import com.sikoraton.tbt.enums.StatusEnums;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

@Component
@Setter @Getter
public class JoinRoom implements NoCommandHandler {
    private BotMenuText menuText;
    private BotMessageText messageText;
    private MainMenuKeyboard mainMenuKeyboard;
    private SessionCache sessionCache;

    @Override
    public boolean isGoal(String text) {
        return menuText.getJoin().containsValue(text);
    }

    @Autowired
    public JoinRoom(BotMenuText menuText,
                    BotMessageText messageText,
                    MainMenuKeyboard mainMenuKeyboard,
                    SessionCache sessionCache) {
        this.menuText = menuText;
        this.messageText = messageText;
        this.mainMenuKeyboard = mainMenuKeyboard;
        this.sessionCache = sessionCache;
    }

    @Override
    public BotApiMethodMessage processing(Message message) {
        UserCache userCache = sessionCache.getUserCache(message.getFrom().getId());
        userCache.setStatus(StatusEnums.JOIN_ROOM);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(I10nHandler.getTextByLanguage(messageText.getJoinroom(), userCache.getLanguageCode()))
                .replyMarkup(ReplyKeyboardRemove.builder().removeKeyboard(true).build())
                .build();
    }
}
