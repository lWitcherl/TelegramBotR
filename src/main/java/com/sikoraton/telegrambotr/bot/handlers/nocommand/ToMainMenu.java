package com.sikoraton.telegrambotr.bot.handlers.nocommand;

import com.sikoraton.tbt.bot.handlers.keyboards.MainMenuKeyboard;
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
public class ToMainMenu implements NoCommandHandler {
    private BotMenuText menuText;
    private BotMessageText messageText;
    private MainMenuKeyboard mainMenuKeyboard;
    private SessionCache sessionCache;

    @Autowired
    public ToMainMenu(BotMenuText menuText,
                      BotMessageText messageText,
                      MainMenuKeyboard mainMenuKeyboard,
                      SessionCache sessionCache) {
        this.menuText = menuText;
        this.mainMenuKeyboard = mainMenuKeyboard;
        this.sessionCache = sessionCache;
        this.messageText = messageText;
    }

    @Override
    public boolean isGoal(String text) {
        return menuText.getFinishgame().containsValue(text);
    }

    @Override
    public BotApiMethodMessage processing(Message message) {
        UserCache userCache = sessionCache.getUserCache(message.getFrom().getId());
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(I10nHandler.getTextByLanguage(messageText.getMain(), userCache.getLanguageCode()))
                .replyMarkup(mainMenuKeyboard.buildKeyboard(userCache.getLanguageCode()))
                .build();
    }
}
