package com.sikoraton.telegrambotr.bot.handlers.keyboards;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public interface Keyboard {
    List<KeyboardRow> generateKeyboardRow(String languageCode);

    default ReplyKeyboardMarkup buildKeyboard(String languageCode){
        return ReplyKeyboardMarkup.builder()
                                    .keyboard(generateKeyboardRow(languageCode))
                                    .selective(true)
                                    .resizeKeyboard(true)
                                    .oneTimeKeyboard(false)
                                    .build();
    }
}
