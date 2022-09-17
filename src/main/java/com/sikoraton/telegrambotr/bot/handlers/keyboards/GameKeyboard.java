package com.sikoraton.telegrambotr.bot.handlers.keyboards;

import java.util.Collections;
import java.util.List;

import com.sikoraton.tbt.util.BotMenuText;
import com.sikoraton.tbt.util.I10nHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Service
@Getter @Setter
public class GameKeyboard implements Keyboard{
    private BotMenuText menuText;

    @Autowired
    public GameKeyboard(BotMenuText menuText) {
        this.menuText = menuText;
    }

    @Override
    public List<KeyboardRow> generateKeyboardRow(String languageCode) {
        String createButton = I10nHandler.getTextByLanguage(menuText.getNextrow(), languageCode);
        String joinButton = I10nHandler.getTextByLanguage(menuText.getFinishgame(), languageCode);
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton(createButton));
        keyboardRow.add(new KeyboardButton(joinButton));
        return Collections.singletonList(keyboardRow);
    }
}
