package com.sikoraton.telegrambotr.service;

import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface NoCommandProcessing {
    BotApiMethodMessage processing(Message message);
}
