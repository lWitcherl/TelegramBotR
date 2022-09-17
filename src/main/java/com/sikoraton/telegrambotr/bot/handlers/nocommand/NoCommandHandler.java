package com.sikoraton.telegrambotr.bot.handlers.nocommand;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface NoCommandHandler {
    boolean isGoal(String text);
    BotApiMethodMessage processing(Message message);
}
