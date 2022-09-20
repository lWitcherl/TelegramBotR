package com.sikoraton.telegrambotr.service.imp;

import java.util.List;

import com.sikoraton.telegrambotr.bot.handlers.nocommand.NoCommandHandler;
import com.sikoraton.telegrambotr.service.DataFromUserService;
import com.sikoraton.telegrambotr.service.NoCommandProcessing;
import com.sikoraton.telegrambotr.util.BotMessageText;
import com.sikoraton.telegrambotr.util.I10nHandler;
import com.sikoraton.telegrambotr.util.SessionCache;
import com.sikoraton.telegrambotr.util.UserCache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
@Getter @Setter
public class NoCommandProcessingImpl implements NoCommandProcessing {
    private List<NoCommandHandler> noCommandList;
    private BotMessageText botMessageText;
    private SessionCache sessionCache;
    private DataFromUserService dataFromUserService;

    @Autowired
    public NoCommandProcessingImpl(List<NoCommandHandler> noCommandList,
                                   BotMessageText botMessageText,
                                   SessionCache sessionCache,
                                   DataFromUserService dataFromUserService) {
        this.noCommandList = noCommandList;
        this.botMessageText = botMessageText;
        this.sessionCache = sessionCache;
        this.dataFromUserService = dataFromUserService;
    }

    @Override
    public BotApiMethodMessage processing(Message message) {
        UserCache userCache = sessionCache.getUserCache(message.getFrom().getId());
        if(userCache.getStatus() != null)
            return dataFromUserService.processingData(message);
        for(NoCommandHandler command : noCommandList)
            if (command.isGoal(message.getText()))
                return command.processing(message);
        return SendMessage.builder()
                .chatId(message.getChatId())
                .text(I10nHandler.getTextByLanguage(botMessageText.getUnknowncommand(), userCache.getLanguageCode()))
                .build();
    }
}


