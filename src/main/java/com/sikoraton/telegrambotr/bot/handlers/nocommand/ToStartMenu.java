package com.sikoraton.telegrambotr.bot.handlers.nocommand;

import com.sikoraton.tbt.bot.handlers.keyboards.StartKeyboard;
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
@Getter @Setter
public class ToStartMenu implements NoCommandHandler {
    private BotMenuText menuText;
    private BotMessageText messageText;
    private StartKeyboard startKeyboard;
    private SessionCache sessionCache;

    @Autowired
    public ToStartMenu(BotMenuText menuText,
                       BotMessageText messageText,
                       StartKeyboard startKeyboard,
                       SessionCache sessionCache) {
        this.menuText = menuText;
        this.messageText = messageText;
        this.startKeyboard = startKeyboard;
        this.sessionCache = sessionCache;
    }

    @Override
    public boolean isGoal(String text) {
        return menuText.getTostart().containsValue(text);
    }

    @Override
    public BotApiMethodMessage processing(Message message) {
        UserCache userCache = sessionCache.getUserCache(message.getFrom().getId());
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(I10nHandler.getTextByLanguage(messageText.getStartmenu(), userCache.getLanguageCode()))
                .replyMarkup(startKeyboard.buildKeyboard(userCache.getLanguageCode()))
                .build();
    }
}
